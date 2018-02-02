package com.groupryan.server.facades;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RegisterFacade {
    String userId;
    String password;
    String result;

    public CommandResult register(User user){

        //ensures that the user id is unique
        //create a user in the model
        //creates a command to return the command

        return null;}
    String checkUserId(String userId){return null;}
    String createUser(User user) {return null;}
    ClientCommand createReturnCommand(String result){return null;}

}
