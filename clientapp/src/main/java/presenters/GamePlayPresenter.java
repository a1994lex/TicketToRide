package presenters;

//import com.groupryan.client.RegisterAsyncTask;
import android.app.Activity;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

import java.util.Observable;
import java.util.Observer;

import async.LoginAsyncTask;
import async.OnLogin;
import async.RegisterAsyncTask;

public class GamePlayPresenter implements Observer{

    private Activity gamePlayActivity;
    private RootClientModel root = RootClientModel.getSingle_instance();
    public GamePlayPresenter(Activity gamePlayActivity ){ this.gamePlayActivity = gamePlayActivity;}


    @Override
    public void update(Observable observable, Object o) {
        // phase 3
    }

    public void testClaimRoute(){
//        Game game2 = new Game("game2", "gameID2", 3);
//
//        User u3 = new User("jimbob", "duggar");
//        User u4 = new User("joanna", "newsom");
//        game2.addUser(u3, GREEN);
//        game2.addUser(u4, YELLOW);

        // route from Helena to Duluth
        Route route1 = new Route(6,"HELENA", "DULUTH", 15, "orange", 26);
        // route from Duluth to Toronto
        Route route2 = new Route(6, "DULUTH", "TORONTO", 15, "pink", 42);
        // route from St. Louis to Nashville
        Route route3 = new Route(2, "SAINT LOUIS", "NASHVILLE", 2, "", 58);
        // route from Houston to New Orleans
        Route route4 = new Route(2, "HOUSTON", "NEW ORLEANS", 2, "", 54);
//        Player myPlayer = root.getCurrentGame().getMyPlayer();
//        String username = myPlayer.getUsername();
//        root.getCurrentGame().getStats().get(username).subtractTrains(6);
//        root.getCurrentGame().getStats().get(username).addPoints(15);
//        root.getCurrentGame().getMyPlayer().addRoute(route1); // adding route to myPlayer
//        root.getCurrentGame().getStats()
//        root.getCurrentGame().getStats().get("jimbob").subtractTrains(2);
//        root.getCurrentGame().getStats().get("jimbob").addPoints(2);
//        root.getCurrentGame().getStats().get("jimbob").incrementTotalRoutes(); // adding route to myPlayer
//        root.getCurrentGame().getStats().get("joanna").subtractTrains(2);
//        root.getCurrentGame().getStats().get("joanna").addPoints(2);
//        root.getCurrentGame().getStats().get("joanna").incrementTotalRoutes();
    }
}
