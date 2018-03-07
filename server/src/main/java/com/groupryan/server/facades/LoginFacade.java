package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class LoginFacade {

    LoginResult login(User user) {
        String result = verifyUser(user);//checks the username and password against the model

        LoginResult lr = new LoginResult();//create the specific login result instead of a command result
        if (result.equals(utils.VALID)) {
            user=RootServerModel.getUser(user.getUsername());
            lr.addClientCommand(createReturnCommand(user));//creates and stores the client command that comes through
            lr.setSucceeded(true);//im setting this so that the client knows to go to the next activity
            lr.setGameList(RootServerModel.getInstance().getGames());
            checkIfInGame(user, lr);
        } else {
            lr.setSucceeded(false);
        }//else false
        lr.setUserMessage(result);//im adding the result too so that they can know if it was a bad username or password
        return lr;
    }

    private String verifyUser(User user) {
        return RootServerModel.getInstance().confirmUser(user);
    }

    private ClientCommand createReturnCommand(User user) {
        return CommandManager.getInstance().makeLoginCommand(user);
    }

    public LoginResult checkIfInGame(User user, LoginResult lr){
        Map<String, ServerGame> serversGames = RootServerModel.getInstance().getServerGameIdMap();
        for (ServerGame serverGame : serversGames.values()) {
            Map<String, Player> playerMap = serverGame.getPlayaMap();
            if(playerMap.containsKey(user.getUsername())){
                //create start game command
                Game g = RootServerModel.getInstance().getGame(serverGame.getServerGameID());
                Player p = playerMap.get(user.getUsername());
                lr.addClientCommand(CommandManager.getInstance().makeStartGameCommand(g, p));
            }
        }
        return lr;
    }

}
