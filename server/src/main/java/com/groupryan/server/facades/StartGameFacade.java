package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class StartGameFacade {
    String gameId;

    public CommandResult start(String gameId) {
        String result=RootServerModel.getInstance()._startGame(gameId);
        CommandResult cr = new CommandResult();
        cr.addClientCommand(activateGame(gameId));
        cr.setResultType(result);
        return cr;
        //takes the game id and uses it ot shutdown the game and start everything
    }
    ClientCommand activateGame(String gameId){
        return CommandManager.getInstance().makeStartGameCommand(gameId);
        }

}
