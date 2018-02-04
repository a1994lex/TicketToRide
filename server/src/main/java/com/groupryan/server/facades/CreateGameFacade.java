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
    String userId;
    Game game;

    public CommandResult createGame(Game game) {
        addGame(game);
        CommandResult cr=new CommandResult();
        cr.addClientCommand(createReturnCommand(game.getGameId()));
        // something cr.getResultType();
        return cr;
    }

    private void addGame (Game game){
        RootServerModel.getInstance()._addGame(game);

    }// String gameId

        ClientCommand createReturnCommand (String gameId){
            CommandManager cm=CommandManager.getInstance();
            return cm.makeCreateGameCommand(gameId);
        }


}
