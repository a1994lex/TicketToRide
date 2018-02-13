package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CreateGameFacade {

    public CommandResult createGame(Game game) {
        String result = addGame(game);
        CommandResult cr = new CommandResult();
        if(result.equals(utils.VALID)){
            cr.addClientCommand(createReturnCommand(game));
        }
        for (Map.Entry<String, String> entry : game.getUsers().entrySet()) {
            User u = RootServerModel.getUser(entry.getKey());
            String c=entry.getValue();

            cr.addClientCommand(createReturCommand(game, u, c));
            break;
        }

        cr.setResultType(result);
        return cr;
    }

    private String addGame(Game game) {
        return RootServerModel.getInstance()._createGame(game);
    }// String gameId

    ClientCommand createReturnCommand(Game game) {
        CommandManager cm = CommandManager.getInstance();
        return cm.makeCreateGameCommand(game);
    }
    ClientCommand createReturCommand(Game game, User u, String c) {
        CommandManager cm = CommandManager.getInstance();
        return cm.makeJoinGameCommand(game, u, c);
    }


}
