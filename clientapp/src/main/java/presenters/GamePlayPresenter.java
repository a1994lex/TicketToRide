package presenters;

import android.app.Activity;
import android.provider.DocumentsContract;
import android.widget.Toast;

import com.example.clientapp.GameActivity;
import com.example.clientapp.IClaimRouteView;
import com.example.clientapp.IGameView;
import com.groupryan.client.UIGameFacade;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.ClaimRouteData;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

import async.DiscardDestCardAsyncTask;
import async.DiscardTrainCardAsyncTask;
import async.Poller;
import states.GameState;
import states.game.ActiveState;
import states.game.InactiveState;


public class GamePlayPresenter implements Observer, IGamePlayPresenter {

    private RootClientModel root = RootClientModel.getInstance();
    int totalClaimedRoutes = root.getCurrentGame().getClaimedRoutesList().size();
    private UIGameFacade uiGameFacade = UIGameFacade.getInstance();
    private IGameView gameView;
    private Activity discardActivity;
    private IClaimRouteView claimRouteView;
    private ClientGame game=RootClientModel.getCurrentGame();
    private GameState state;
    private ClaimRouteData claimRouteData;


    private static GamePlayPresenter instance;

    private GamePlayPresenter() {
        this.state = new InactiveState();
    }

    public static GamePlayPresenter getInstance() {
        if (instance == null) {
            instance = new GamePlayPresenter();
        }
        return instance;
    }

    public void setGameView(IGameView gameView) {
        this.gameView = gameView;
        root.addObserver(this);
        game.addObserver(this);
    }
    public IGameView getGameView(){
        return this.gameView;
    }
    public BankPresenter getBankPresenter(){
        return BankPresenter.getInstance();
    }

    @Override
    public void setUpIfFirst() {
       // if (this.game!=null && this.game.getCurrentTurn()<0){
            if(this.game.getOriginal()){
            stopLobbyPolling();
            callDrawDestCards();
        }
    }

    public void setDiscardActivity(Activity discardActivity) {
        this.discardActivity = discardActivity;
    }

    public void setClaimRouteView(IClaimRouteView claimRouteView) {
        this.claimRouteView = claimRouteView;
    }

    @Override
    public void redrawRoutes() {
        root.setShowRoutes();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == game) { //I changed this so now it check if observable is game, not root.
            int secondSize = root.getCurrentGame().getClaimedRoutesList().size();
            if (o.getClass().equals(Route.class)) {
                if (secondSize > totalClaimedRoutes) {
                    totalClaimedRoutes = secondSize;
                    Route r = (Route) o;
                    HashSet<RouteSegment> routeSegments = root.getRouteSegmentSet(r.getId());
                    gameView.drawRoute(r.getColor(), routeSegments);
                    drawRoutes();
                }
            } else if (o.equals(utils.DISCARD_DESTCARD)) {
                // it's trying to call a method on gameView when gameView is null
                gameView.cardsDiscarded();
            } else if (o.equals(utils.REDRAW_ROUTES)) {
                drawRoutes();
            } else if(o.equals(utils.GAME_OVER)){
                gameView.endGame();
            }
            else if(observable==game){
                if (o.equals(utils.DISCARD_DESTCARD)) {
                    gameView.cardsDiscarded();
                   // this.gameActivity.finish();
                }
                else if (o.equals(utils.NEW_TURN)){
                    if (game.isMyTurn()){
                        setState(new ActiveState());
                    }
                }
            }
        }
    }

    public void drawRoutes() {
        List<Route> claimedRoutes = root.getCurrentGame().getClaimedRoutesList();
        List<Integer> routeIds = new ArrayList<>();
        List<String> routeColors = new ArrayList<>();
        for (Route route : claimedRoutes) {
            routeIds.add(route.getId());
            routeColors.add(route.getColor());
        }
        HashSet<RouteSegment> routeSegments;
        for (int i = 0; i < routeIds.size(); i++) {
            routeSegments = root.getRouteSegmentSet(routeIds.get(i));
            gameView.drawRoute(routeColors.get(i), routeSegments);
        }
    }
    /// STATE FUNCTIONS //////////
    @Override
    public void clickClaimRoute() {
        state.clickClaimRoute(this);
        //gameView.showClaimRouteModal();
        // depending on the state, the state will call gameView.showClaimRouteModal();
    }

    @Override
    public void clickDrawCard() {
        state.clickDrawCard(this);
    }

    public void setState(GameState state){
        this.state = state;
    }

    @Override
    public void cancel() {
        this.state.cancel(this);
    }

    @Override
    public void submit() {
        this.state.submit(this);
    }
    ////END OF STATE FUNCTIONS//////



    @Override // DO WE WANT TO MOVE THIS TO A CLAIM ROUTE DIALOG ACTIVITY??
    public void claimRoute(int routeId) {
        List<TrainCard> trainCards = verifyRoute(routeId);
        boolean goToHand = false;
        if (trainCards != null) {
            int trains = RootClientModel.getCurrentGame().getMyPlayer().getTrainPieces();
            boolean enoughTrains = verifyEnoughTrainPieces(trains, routeId);
            if (enoughTrains) {
                goToHand = verifyHand(trainCards, routeId);
            }
        }
        if (goToHand) {
            Route route = RootClientModel.getRoute(routeId);
            String routeColor = route.getColor();
            int length = route.getLength();
            claimRouteView.startHandFragment(routeColor, length, routeId);
        }
        // set claimRoute command stuff
    }

    public boolean verifyEnoughTrainPieces(int trains, int routeId) {
        int cost = RootClientModel.getRoute(routeId).getWorth();
        if (trains >= cost) {
            return true;
        } else {
            claimRouteView.showMessage("Not enough train pieces to claim route");
        }
        return false;
    }

    public List<TrainCard> verifyRoute(int routeId) {
        Route route = null;
        for (Route r : RootClientModel.getCurrentGame().getClaimedRoutesList()) {
            if (r.getId() == routeId) {
                route = r;
            }
        }
        if (route == null) { // route not claimed
            return RootClientModel.getCurrentGame().getMyPlayer().getTrainCards();
        } else {
            claimRouteView.showMessage("Route already claimed");
            return null;
        }
    }

    public boolean verifyHand(List<TrainCard> trainCards, int routeId) {
        Route route = RootClientModel.getRoute(routeId);
        int routeLength = route.getLength();
        String routeColor = route.getColor();
        boolean enoughCards = countMatchingTrainCards(routeColor, routeLength,
                                                                    trainCards);
        if (!enoughCards) {
            claimRouteView.showMessage("Not enough train cards to claim this route");
            return enoughCards;
        } else {
            return enoughCards;
        }
    }

    public void discardTrainCards(int routeId, List<Integer> pickedCards) {
        String username = RootClientModel.getCurrentGame().getMyPlayer().getUsername();
        claimRouteData = new ClaimRouteData(pickedCards, routeId, username);
        state.submit(GamePlayPresenter.getInstance());
    }

    public void callClaimRouteAsyncTask() {
        DiscardTrainCardAsyncTask task = new DiscardTrainCardAsyncTask();
        task.execute(claimRouteData);
    }

    public Map<String, Integer> mapColorToCount(String color, Map<String, Integer> pickedCards) {
        int count = 0;
        if (pickedCards.isEmpty()) {
            pickedCards.put(utils.RED, 0);
            pickedCards.put(utils.ORANGE, 0);
            pickedCards.put(utils.YELLOW, 0);
            pickedCards.put(utils.GREEN, 0);
            pickedCards.put(utils.BLUE, 0);
            pickedCards.put(utils.PINK, 0);
            pickedCards.put(utils.WHITE, 0);
            pickedCards.put(utils.BLACK, 0);
            pickedCards.put(utils.LOCOMOTIVE, 0);
        }
        if (color.equals(utils.RED)) {
            count = pickedCards.get(utils.RED);
            count++;
            pickedCards.put(utils.RED, count);
        }
        if (color.equals(utils.ORANGE)) {
            count = pickedCards.get(utils.ORANGE);
            count++;
            pickedCards.put(utils.ORANGE, count);
        }
        if (color.equals(utils.YELLOW)) {
            count = pickedCards.get(utils.YELLOW);
            count++;
            pickedCards.put(utils.YELLOW, count);
        }
        if (color.equals(utils.GREEN)) {
            count = pickedCards.get(utils.GREEN);
            count++;
            pickedCards.put(utils.GREEN, count);
        }
        if (color.equals(utils.BLUE)) {
            count = pickedCards.get(utils.BLUE);
            count++;
            pickedCards.put(utils.BLUE, count);
        }
        if (color.equals(utils.PINK)) {
            count = pickedCards.get(utils.PINK);
            count++;
            pickedCards.put(utils.PINK, count);
        }
        if (color.equals(utils.BLACK)) {
            count = pickedCards.get(utils.BLACK);
            count++;
            pickedCards.put(utils.BLACK, count);
        }
        if (color.equals(utils.WHITE)) {
            count = pickedCards.get(utils.WHITE);
            count++;
            pickedCards.put(utils.WHITE, count);
        }
        if (color.isEmpty()) {
            count = pickedCards.get(utils.LOCOMOTIVE);
            count++;
            pickedCards.put(utils.LOCOMOTIVE, count);
        }
        return pickedCards;
    }

    public boolean countMatchingTrainCards(String routeColor, int routeLength,
                                                  List<TrainCard> trainCards) {
        int count = 0;
        if (!routeColor.isEmpty()) {
            for (TrainCard trainCard : trainCards) {
                if (routeColor.equals(trainCard.getColor())) {
                    count++;
                }
            }
            if (count == routeLength) {
                return true;
            }
        } else {
            return checkTrainCardsCount(trainCards, routeLength);
        }
        return false;
    }

    public boolean checkTrainCardsCount(List<TrainCard> trainCards, int routeLength) {
        int redCount = 0;
        int orangeCount = 0;
        int yellowCount = 0;
        int greenCount = 0;
        int blueCount = 0;
        int pinkCount = 0;
        int blackCount = 0;
        int whiteCount = 0;
        int locomotiveCount = 0;
        for (TrainCard trainCard : trainCards) {
            if (trainCard.getColor().equals(utils.RED)) {
                redCount++;
            }
            else if (trainCard.getColor().equals(utils.ORANGE)) {
                orangeCount++;
            }
            else if (trainCard.getColor().equals(utils.YELLOW)) {
                yellowCount++;
            }
            else if (trainCard.getColor().equals(utils.GREEN)) {
                greenCount++;
            }
            else if (trainCard.getColor().equals(utils.BLUE)) {
                blueCount++;
            }
            else if (trainCard.getColor().equals(utils.PINK)) {
                pinkCount++;
            }
            else if (trainCard.getColor().equals(utils.WHITE)) {
                whiteCount++;
            }
            else if (trainCard.getColor().equals(utils.BLACK)) {
                blackCount++;
            }
        }
        if (redCount >= routeLength || (redCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (orangeCount >= routeLength || (orangeCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (yellowCount >= routeLength || (yellowCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (greenCount >= routeLength || (greenCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (blueCount >= routeLength || (blueCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (pinkCount >= routeLength || (pinkCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (whiteCount >= routeLength || (whiteCount + locomotiveCount) >= routeLength) {
            return true;
        }
        if (blackCount >= routeLength || (blackCount + locomotiveCount) >= routeLength) {
            return true;
        }
        return false;
    }

    public List<Integer> getDiscardingTrainCards(Map<String, Integer> pickedCards) {
        List<TrainCard> trainCards = RootClientModel.getCurrentGame().getMyPlayer().getTrainCards();
        List<Integer> pickedTrainCards = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pickedCards.entrySet()) {
            if (entry.getKey().equals(utils.RED) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.RED, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.ORANGE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.ORANGE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.YELLOW) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.YELLOW, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.GREEN) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.GREEN, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.BLUE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.BLUE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.PINK) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.PINK, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.BLACK) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.BLACK, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.WHITE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.WHITE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
            if (entry.getKey().equals(utils.LOCOMOTIVE) && entry.getValue() > 0) {

                List<Integer> values = findTrainCardByColor(trainCards, utils.LOCOMOTIVE, entry.getValue());
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i) != -1) {
                        pickedTrainCards.add(values.get(i));
                    }
                }
            }
        }
        return pickedTrainCards;
    }

    public List<Integer> findTrainCardByColor(List<TrainCard> trainCards, String color, int numberOfCards) {
        List<Integer> cardIds = new ArrayList<>();
        for (TrainCard trainCard : trainCards) {
            if (color.equals(trainCard.getColor()) && numberOfCards > 0) {
                cardIds.add(trainCard.getID());
                numberOfCards--;
            }
        }
        return cardIds;
    }

    public void discardDestinationCard(List<Integer> cardIDs) {
        DiscardDestCardAsyncTask task = new DiscardDestCardAsyncTask(discardActivity);
        task.execute(cardIDs);
    }

    public void callDrawDestCards(){
        gameView.goToDrawDestActivity();
    }

    public void stopLobbyPolling() {
        Poller.get().stop();
    }

    public String decrementTextViewCount(String count) {
        int value = Integer.parseInt(count);
        value--;
        String returnVal = Integer.toString(value);
        return returnVal;
    }
}
