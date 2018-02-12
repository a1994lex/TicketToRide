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

public class RegisterPresenter implements Observer{

    private Activity registerActivity;
    private RootClientModel root = RootClientModel.getSingle_instance();
    public RegisterPresenter(Activity registerActivity ){ this.registerActivity = registerActivity;}


    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String message = (String)o;
            if(message.equals("logged in")){
                OnLogin signedIn = (OnLogin)registerActivity;
                signedIn.onLogin();
            }
        }
        //if logged in, start next activity.
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
}
