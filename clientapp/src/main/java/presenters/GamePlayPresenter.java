package presenters;

//import com.groupryan.client.RegisterAsyncTask;
import android.app.Activity;

import com.groupryan.client.models.RootClientModel;
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

    }

    public void testClaimRoute(){
        // go to the root, get the current player, figure out their color
        // add points to that player, increment the number of claimed routes
    }
}
