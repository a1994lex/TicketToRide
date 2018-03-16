package presenters;

import android.app.Activity;
import android.widget.Toast;

import com.example.clientapp.GameActivity;
import com.example.clientapp.IGameView;
import com.groupryan.client.UIGameFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import async.DiscardDestCardAsyncTask;
import async.LoginAsyncTask;
import async.OnLogin;
import async.Poller;
import async.RegisterAsyncTask;


public class GamePlayPresenter implements Observer, IGamePlayPresenter {

    private RootClientModel root = RootClientModel.getInstance();
    int totalClaimedRoutes = root.getClaimedRoutesMap().size();
    private UIGameFacade uiGameFacade = UIGameFacade.getInstance();
    private Activity gameActivity;
    private Activity discardActivity;


    private static GamePlayPresenter instance;

    private GamePlayPresenter() {
    }

    public static GamePlayPresenter getInstance() {
        if (instance == null) {
            instance = new GamePlayPresenter();
        }
        return instance;
    }

    public void setGameActivity(Activity gameActivity) {
        this.gameActivity = gameActivity;
        root.addObserver(this);
    }

    public void setDiscardActivity(Activity discardActivity) {
        this.discardActivity = discardActivity;
    }

    public void redrawRoutes() {
        root.setShowRoutes();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root) {
            int secondSize = root.getClaimedRoutesMap().size();
            if (o.getClass().equals(Route.class)) {
                if (secondSize > totalClaimedRoutes) {
                    IGameView gameView = (IGameView) gameActivity;
                    Route r = (Route) o;
                    HashSet<RouteSegment> routeSegments = root.getRouteSegmentSet(r.getId());
                    gameView.drawRoute(r.getColor(), routeSegments);
                }
            } else if (o.equals(utils.DISCARD_DESTCARD)) {
                IGameView gameView = (IGameView) gameActivity;
                gameView.cardsDiscarded();
            } else if (o.equals(utils.REDRAW_ROUTES)) {
                drawRoutes();
            }
        }
    }

    public void drawRoutes() {
        IGameView gameView = (IGameView) gameActivity;
        List<Route> claimedRoutes = root.getClaimedRoutes();
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

    public void claimRouteTest(String keyValue) {
        if (keyValue.equals(utils.CURRENT_PLAYER)) {
            String playerColor = root.getCurrentGame().getMyPlayer().getColor();
            String myPlayerName = root.getCurrentGame().getMyPlayer().getUsername();
            Toast.makeText(this.gameActivity, "Current player is claiming route.", Toast.LENGTH_SHORT);
            root.getCurrentGame().getMyPlayer().removeTrains(2);
            List<TrainCard> trainCards = root.getCurrentGame().getMyPlayer().getTrainCards();
            trainCards.remove(trainCards.size() - 1);
            trainCards.remove(trainCards.size() - 1);
            Stat stat = new Stat(myPlayerName, 3,2, 43, 3, 3);
            root.getCurrentGame().updateStat(stat);
            root.addClaimedRoute(myPlayerName, new Route(2, "OKLAHOMA CITY",
                    "LITTLE ROCK", 2, playerColor, 50));
            root.getCurrentGame().updateHistory("Red player claimed Oklahoma City to Little Rock.");
        }
        else {
            String playerColor = root.getCurrentGame().getPlayersColors().get(keyValue);
            switch (playerColor) {
                case utils.GREEN: {
                    HashMap<String, Stat> stats = root.getCurrentGame().getStats();
                    Stat s = stats.get(keyValue);
                    int trainCards = s.getTrainCards();
                    if (trainCards == 4) {
                        Toast.makeText(this.gameActivity, "Updating Green player cards.", Toast.LENGTH_SHORT);
                        Stat stat = new Stat(keyValue, 1, 0, 45, 6, 3);
                        root.getCurrentGame().updateStat(stat);
                        root.getCurrentGame().updateHistory("Green player now has 6 cards.");
                        break;
                    } else {
                        root.addClaimedRoute(keyValue, new Route(6, "HELENA",
                                "DULUTH", 15, utils.GREEN, 26));
                        root.addClaimedRoute(keyValue, new Route(6, "DULUTH",
                                "TORONTO", 15, utils.GREEN, 42));
                        root.addClaimedRoute(keyValue, new Route(5, "ATLANTA",
                                "MIAMI", 10, utils.GREEN, 84));
                        root.addClaimedRoute(keyValue, new Route(6, "SEATTLE",
                                "HELENA", 15, utils.GREEN, 9));
                        root.addClaimedRoute(keyValue, new Route(6, "LA",
                                "EL PASO", 15, utils.GREEN, 15));
                        root.addClaimedRoute(keyValue, new Route(6, "EL PASO",
                                "HOUSTON", 15, utils.GREEN, 38));
                        root.addClaimedRoute(keyValue, new Route(6, "NEW ORLEANS",
                                "MIAMI", 15, utils.GREEN, 83));
                        root.addClaimedRoute(keyValue, new Route(6, "CALGARY",
                                "WINNIPEG", 15, utils.BLUE, 16));
                        root.addClaimedRoute(keyValue, new Route(6, "PORTLAND",
                                "SALT LAKE CITY", 15, utils.GREEN, 10));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 87));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 40));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 65));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 22));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 8));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 45));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 59));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 27));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 3));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 4));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 11));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 12));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 33));
                        root.addClaimedRoute(keyValue, new Route(6, "CHARLESTON",
                                "MIAMI", 8, utils.GREEN, 36));

                        Toast.makeText(this.gameActivity, "Green player is claiming route.", Toast.LENGTH_SHORT);
                        Stat stat = new Stat(keyValue,1, 15, 39, 0, 3);
                        root.getCurrentGame().updateStat(stat);
                        root.getCurrentGame().updateHistory("Green player claimed Helena to Duluth.");
                        break;
                    }
                }
                case utils.YELLOW: {
                    Toast.makeText(this.gameActivity, "Yellow player is claiming route.", Toast.LENGTH_SHORT);
                    Stat stat = new Stat(keyValue, 2, 2, 43, 3, 3);
                    root.getCurrentGame().updateStat(stat);
                    root.addClaimedRoute(keyValue, new Route(2, "ATLANTA",
                            "CHARLESTON", 2, utils.YELLOW, 86));
                    root.getCurrentGame().updateHistory("Yellow player claimed Atlanta to Charleston.");
                    break;
                }
            }
        }
    }

    public void changeDestinationCards(String username) {
        Toast.makeText(this.gameActivity, "Yellow player now has 2 destination cards.", Toast.LENGTH_SHORT);
        Stat stat = new Stat(username, 3,2, 43, 3, 2);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().updateHistory("Yellow discarded 1 destination card.");
    }

    public void addChatMessage(String username, String message) {
        String playerColor = root.getCurrentGame().getPlayersColors().get(username);
        Toast.makeText(this.gameActivity, "Adding message from jimbob to chat.", Toast.LENGTH_SHORT);
        Chat myChat = new Chat(message, playerColor);
        root.getCurrentGame().updateChat(myChat);
    }

    @Override
    public void claimRoute(String playerColor, int routeId) {

    }

    public void discardDestinationCard(List<Integer> cardIDs) {
        DiscardDestCardAsyncTask task = new DiscardDestCardAsyncTask(discardActivity);
        task.execute(cardIDs);
    }

    public void changeTrainCards() {
        Toast.makeText(this.gameActivity, "Removing train card from current player", Toast.LENGTH_SHORT);
        List<TrainCard> trainCards = root.getCurrentGame().getMyPlayer().getTrainCards();
        trainCards.remove(trainCards.size() - 1);
        Stat stat = root.getCurrentGame().getMyPlayer().makeStat();
        stat.setTrainCards(stat.getTrainCards() - 1);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().getMyPlayer().setTrainCards(trainCards);
    }

    public void changeDestCards() {
        Toast.makeText(this.gameActivity, "Removing destination card from current player", Toast.LENGTH_SHORT);
        List<DestCard> destCards = root.getCurrentGame().getMyPlayer().getDestCards();
        destCards.remove(destCards.size() - 1);
        Stat stat = root.getCurrentGame().getMyPlayer().makeStat();
        stat.setDestinationCards(stat.getDestinationCards() - 1);
        root.getCurrentGame().updateStat(stat);
        root.getCurrentGame().getMyPlayer().setDestCards(destCards);
    }

    public void stopLobbyPolling() {
        Poller.get().stop();
    }
}
