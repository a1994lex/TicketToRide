package com.groupryan.client.ui;

//import com.groupryan.client.RegisterAsyncTask;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

import java.util.Observable;
import java.util.Observer;

public class RegisterPresenter implements Observer{

    //private Activity registerActivity;
    private RootClientModel root = RootClientModel.getInstance();
    public RegisterPresenter(){ } //Activity registerActivity ){ this.registerActivity = registerActivity;}


    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String message = (String)o;
            if(message.equals("logged in")){
                //startActivity(LobbyActivity.class);
            }
        }
        //if logged in, start next activity.
    }

    public void login(String username, String password){
        User u = new User(username, password);
        //LoginAsyncTask loginAsyncTask = new LoginAsyncTask(registerActivity);
       // loginAsyncTask.execute(u);

    }

    public void register(String username, String password){
        User u = new User(username, password);
       // RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(registerActivity);
       // registerAsyncTask.execute(u);
    }
}
