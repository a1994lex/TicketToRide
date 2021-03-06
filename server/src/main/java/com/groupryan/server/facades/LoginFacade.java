package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.ClientFacingGame;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bengu3 on 1/31/18.
 */

public class LoginFacade {

    LoginResult login(User user) {
        String result = verifyUser(user);//checks the username and password against the model

        LoginResult lr = new LoginResult();//create the specific login result instead of a command result
        if (result.equals(utils.VALID)) {
            user = RootServerModel.getUser(user.getUsername());
            checkIfInGame(user, lr);
            if (lr.getClientCommands().size() == 0) {
                lr.addClientCommand(createReturnCommand(user));//creates and stores the client command that comes through
            }
            lr.setSucceeded(true);//im setting this so that the client knows to go to the next activity
            lr.setGameList(RootServerModel.getInstance().getGames());

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

    public LoginResult checkIfInGame(User user, LoginResult lr) {
        Map<String, ServerGame> serversGames = RootServerModel.getInstance().getServerGameIdMap();
        boolean foundGame = false;
        for (ServerGame serverGame : serversGames.values()) {
            Map<String, Player> playerMap = serverGame.getPlayaMap();
            if (playerMap.containsKey(user.getUsername())) {
                //create start game command
                Player p = playerMap.get(user.getUsername());
                lr.addClientCommand(CommandManager.getInstance().makeRetrieveGameCommand(getClientGame(p, serverGame), user));
                foundGame = true;
                break;
            }
        }
        if (!foundGame) {
            if (user.getGameId() != "") {
                lr.addClientCommand(CommandManager.getInstance()
                        .makeRejoinLobbyCommand(RootServerModel.getInstance()
                                .getGame(user.getGameId())));
            }
        }
        return lr;
    }

    private ClientFacingGame getClientGame(Player receiver, ServerGame serverGame) {
        ClientFacingGame cGame = new ClientFacingGame(serverGame.getServerGameID(), receiver);
        cGame.setAvailableRoutes((ArrayList<Route>) receiver.getAvailableRoutes());
        cGame.setHistory((ArrayList<String>) serverGame.getAllHistory());
        cGame.setStats((HashMap<String, Stat>) serverGame.getStats());
        cGame.setBankCards((ArrayList<TrainCard>) serverGame.getBankList());
        cGame.setPlayersColors(serverGame.getPlayerColors());
        cGame.setCurrentTurn(serverGame.getCurrentTurn());
        cGame.setClaimedRoutes(serverGame.getClaimedRoutes());
        cGame.setOriginal(!serverGame.updateReady());
        return cGame;
    }

}
