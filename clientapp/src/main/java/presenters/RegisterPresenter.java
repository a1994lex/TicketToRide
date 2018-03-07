package presenters;

//import com.groupryan.client.RegisterAsyncTask;
import android.app.Activity;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import async.LoginAsyncTask;
import async.OnLogin;
import async.RegisterAsyncTask;

public class RegisterPresenter implements Observer{

    private Activity registerActivity;
    private RootClientModel root = RootClientModel.getSingle_instance();
    public RegisterPresenter(Activity registerActivity ){ this.registerActivity = registerActivity;}


    @Override
    public void update(Observable observable, Object o) {
                //Starting new activity
                OnLogin signedIn = (OnLogin) registerActivity;
                signedIn.onLogin();
    }

    public void login(String username, String password){
        User u = new User(username, password);
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(registerActivity);
        loginAsyncTask.execute(u);

    }

    public void register(String username, String password){
        User u = new User(username, password);
        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(registerActivity);
        registerAsyncTask.execute(u);
    }

    public boolean checkIfJoinedGame(){
        //checks to see if a user has already joined a game, and if they have, takes them directly to game activity
        if(RootClientModel.getCurrentGame() == null){
            return false;
        }
        else{
            return true;
        }

    }
}
