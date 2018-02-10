package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class StartGameFacade {

    public CommandResult start(Game game) {
        String result = RootServerModel.getInstance().startGame(game);
        CommandResult cr = new CommandResult();
        cr.addClientCommand(activateGame(game));
        cr.setResultType(result);
        return cr;
        //takes the game id and uses it ot shutdown the game and start everything
    }

    ClientCommand activateGame(Game game) {
        return CommandManager.getInstance().makeStartGameCommand(game);
    }

}
