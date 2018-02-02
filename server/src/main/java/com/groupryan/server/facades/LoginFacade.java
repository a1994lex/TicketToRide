package com.groupryan.server.facades;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class LoginFacade {
    String userId;
    String password;
    String result;

    CommandResult login(User user){

        //verifies that the username and passowrd are correct
        //create the command allowing login

        return null;}
    Boolean verifyUserId(String userId){return false;}
    Boolean verifyPassword(String password){return false;}
    ClientCommand createReturnCommand(String result){return null;}

}
