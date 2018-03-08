package presenters;

import android.app.Activity;
import android.widget.Toast;

import com.example.clientapp.IGameView;
import com.groupryan.client.UIGameFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.utils;

import java.util.HashMap;
import java.util.HashSet;
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


public class GamePlayPresenter implements Observer, IGamePlayPresenter{

    private RootClientModel root = RootClientModel.getInstance();
    int totalClaimedRoutes = root.getClaimedRoutes().size();
    private UIGameFacade uiGameFacade = UIGameFacade.getInstance();
    private Activity gameActivity;

    public GamePlayPresenter(Activity gameActivity ){
        this.gameActivity = gameActivity;
        root.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root){
            int secondSize = root.getClaimedRoutes().size();
            if (secondSize > totalClaimedRoutes){
                    IGameView gameView = (IGameView)gameActivity;
                Route r = (Route) o;
                HashSet<RouteSegment> routeSegments = root.getRouteSegmentSet(r.getId());
                gameView.drawRoute(r.getColor(), routeSegments);
            }
        }
    }

    public void claimRouteTest(String username) {
        String playerColor = root.getCurrentGame().getPlayersColors().get(username);
        switch (playerColor) {
            case utils.GREEN: {
                HashMap<String, Stat> stats = root.getCurrentGame().getStats();
                Stat s = stats.get(username);
                int trainCards = s.getTrainCards();
                if (trainCards == 4) {
                    Toast.makeText(this.gameActivity, "Updating Green player cards.", Toast.LENGTH_SHORT);
                    Stat stat = new Stat(username, 0, 45, 6, 3);
                    root.getCurrentGame().updateStat(stat);
                    root.getCurrentGame().updateHistory("Green player now has 6 cards.");
                    break;
                }
                else {
                    root.addClaimedRoute(username, new Route(6, "HELENA",
                            "DULUTH", 15, utils.GREEN, 26));
                    Toast.makeText(this.gameActivity, "Green player is claiming route.", Toast.LENGTH_SHORT);
                    Stat stat = new Stat(username, 15, 39, 0, 3);
                    root.getCurrentGame().updateStat(stat);
                    root.getCurrentGame().updateHistory("Green player claimed Helena to Duluth.");
                    break;
                }
            }
            case utils.RED: {
                Toast.makeText(this.gameActivity, "Red player is claiming route.", Toast.LENGTH_SHORT);
                Stat stat = new Stat(username, 2, 43, 3, 3);
                root.getCurrentGame().updateStat(stat);
                root.addClaimedRoute(username, new Route(2, "OKLAHOMA CITY",
                                "LITTLE ROCK", 2, utils.RED, 50));
                root.getCurrentGame().updateHistory("Red player claimed Oklahoma City to Little Rock.");
                break;
            }
            case utils.YELLOW: {
                Toast.makeText(this.gameActivity, "Yellow player is claiming route.", Toast.LENGTH_SHORT);
                Stat stat = new Stat(username, 2, 43, 3, 3);
                root.getCurrentGame().updateStat(stat);
                root.addClaimedRoute(username, new Route(2, "ATLANTA",
                                "CHARLESTON", 2, utils.YELLOW, 86));
                root.getCurrentGame().updateHistory("Yellow player claimed Atlanta to Charleston.");
                break;
            }
        }
    }

    public void changeDestinationCards(String username) {
        Toast.makeText(this.gameActivity, "Yellow player now has 2 destination cards.", Toast.LENGTH_SHORT);
        Stat stat = new Stat(username, 2, 43, 3, 2);
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
        DestCardList dcl= new DestCardList(cardIDs);
        DiscardDestCardAsyncTask task = new DiscardDestCardAsyncTask(gameActivity);
        task.execute(cardIDs);
    }

    public void stopLobbyPolling() {
        Poller.get().stop();
    }
}
