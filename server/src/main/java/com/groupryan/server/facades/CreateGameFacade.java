package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CreateGameFacade {

    public CommandResult createGame(Game game) {
        String result = addGame(game);
        CommandResult cr = new CommandResult();
        cr.addClientCommand(createReturnCommand(game));
        cr.setResultType(result);
        return cr;
    }

    private String addGame(Game game) {
        return RootServerModel.getInstance().addGame(game);
    }// String gameId

    ClientCommand createReturnCommand(Game game) {
        CommandManager cm = CommandManager.getInstance();
        return cm.makeCreateGameCommand(game);
    }


}
