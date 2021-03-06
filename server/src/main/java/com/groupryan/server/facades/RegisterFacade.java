package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.DatabaseHolder;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RegisterFacade {

    public LoginResult register(User user) {
        Boolean exists = checkUserId(user);
        LoginResult lr = new LoginResult();
        if (!exists) {
            //send command to db
            DatabaseHolder.getInstance().getDatabase().startTransaction();
            DatabaseHolder.getInstance().getDatabase().getUserDao().registerUser(user);
            DatabaseHolder.getInstance().getDatabase().endTransaction();

            String result = createUser(user);
            lr.addClientCommand(createReturnCommand(user));
            lr.setSucceeded(true);
            lr.setUserMessage(result);
            lr.setGameList(RootServerModel.getInstance().getGames());
        } else {
            lr.setSucceeded(false);
            lr.setUserMessage("user already exists");
        }
        //ensures that the user id is unique
        //create a user in the model
        //creates a command to return the command
        return lr;
    }

    private Boolean checkUserId(User user) {
        return RootServerModel.getInstance().checkUser(user);
    }

    private String createUser(User user) {
        return RootServerModel.getInstance().addUser(user);
    }

    private ClientCommand createReturnCommand(User user) {
        return CommandManager.getInstance().makeRegisterCommand(user);
    }

}
