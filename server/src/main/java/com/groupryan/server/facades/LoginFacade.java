package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class LoginFacade {
    String userId;
    String password;
    String result;

    LoginResult login(User user){
        String result=verifyUser(user.getUsername(), user.getPassword());//checks the username and password against the model
        LoginResult lr =new LoginResult();//create the specific login reuslt instead of a command result
        lr.addClientCommand(createReturnCommand(result));//creates and stores the client command that comes through
        if(result.equals("valid")) {
            lr.setSucceeded(true);//im setting this so that the client knows to go to the next activity
        }
        else{lr.setSucceeded(false);}//else false
        lr.setUserMessage(result);//im adding the result too so that they can know if it was a bad username or password
        return lr;}
    private String verifyUser(String userId, String password){
        return RootServerModel.getInstance()._confirmUser(userId, password);
    }

    private ClientCommand createReturnCommand(String result){
        return CommandManager.getInstance().makeLoginCommand(result);}

}
