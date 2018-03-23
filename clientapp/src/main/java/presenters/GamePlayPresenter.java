package presenters;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.clientapp.GameActivity;
import com.example.clientapp.IGameView;
import com.example.clientapp.dialogs.DiscardDestCardDialogActivity;
import com.groupryan.client.UIGameFacade;
import com.groupryan.client.models.ClientGame;
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
    private IGameView gameActivity;
    private Activity discardActivity;
    private ClientGame game=RootClientModel.getCurrentGame();


    private static GamePlayPresenter instance;

    private GamePlayPresenter() {
    }

    public static GamePlayPresenter getInstance() {
        if (instance == null) {
            instance = new GamePlayPresenter();
        }
        return instance;
    }

    @Override
    public void setGameActivity(IGameView gameView) {
        this.gameActivity = gameActivity;
        root.addObserver(this);
        game.addObserver(this);
    }

    @Override
    public void setUpIfFirst() {
        if (this.game.getCurrentTurn()<0){
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

    }

    @Override
    public void clickDrawCard() {

    }
    ///////////////////////////////



    @Override
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
