package presenters;

import android.app.Activity;
import com.example.clientapp.IGameView;
import com.groupryan.client.UIGameFacade;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

import async.DiscardDestCardAsyncTask;
import async.Poller;
import states.GameState;
import states.game.InactiveState;


public class GamePlayPresenter implements Observer, IGamePlayPresenter {

    private RootClientModel root = RootClientModel.getInstance();
    int totalClaimedRoutes = root.getClaimedRoutesMap().size();
    private UIGameFacade uiGameFacade = UIGameFacade.getInstance();
    private IGameView gameActivity;
    private Activity discardActivity;
    private ClientGame game=RootClientModel.getCurrentGame();
    private GameState state;


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

    @Override
    public void setGameActivity(IGameView gameView) {
        this.gameActivity = gameView;
        root.addObserver(this);
        game.addObserver(this);
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

    @Override
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
            else if(observable==game){
                if (o.equals(utils.DISCARD_DESTCARD)) {
                    IGameView gameView = (IGameView) gameActivity;
                    gameView.cardsDiscarded();
                   // this.gameActivity.finish();
                }
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
    /// STATE FUNCTIONS //////////
    @Override
    public void clickClaimRoute() {
        state.clickClaimRoute(this);
        // depending on the state, the state will call gameView.showClaimRouteModal();
    }

    @Override
    public void clickDrawCard() {
        state.clickDrawCard(this);
    }

    public void setState(GameState state){
        this.state = state;
    }
    ////END OF STATE FUNCTIONS//////



    @Override // DO WE WANT TO MOVE THIS TO A CLAIM ROUTE DIALOG ACTIVITY??
    public void claimRoute(String playerColor, int routeId) {

    }

    public void discardDestinationCard(List<Integer> cardIDs) {
        DiscardDestCardAsyncTask task = new DiscardDestCardAsyncTask(discardActivity);
        task.execute(cardIDs);
    }

    public void callDrawDestCards(){
        gameActivity.goToDrawDestActivity();
    }

    public void stopLobbyPolling() {
        Poller.get().stop();
    }
}
