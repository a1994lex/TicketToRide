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

public class RegisterFacade {
    User user;
    String result;

    public LoginResult register(User user){
        this.user=user;
        Boolean exists=checkUserId(user.getUsername());
        LoginResult lr=new LoginResult();
        if(!exists){
            String result=createUser();
            lr.addClientCommand(createReturnCommand("valid"));
            lr.setSucceeded(true);
            lr.setUserMessage(result);
        }
        else{
            lr.addClientCommand(createReturnCommand("user already exists"));
            lr.setSucceeded(false);
            lr.setUserMessage("user already exists");

        }
        //ensures that the user id is unique
        //create a user in the model
        //creates a command to return the command
        return lr;}

    private Boolean checkUserId(String userId){
       return RootServerModel.getInstance()._checkUser(userId);}

    private String createUser() {
        return RootServerModel.getInstance()._addUser(user);}

    private ClientCommand createReturnCommand(String result){
            return CommandManager.getInstance().makeRegisterCommand(result, user);
        }

}
