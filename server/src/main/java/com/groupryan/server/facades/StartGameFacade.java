package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

/**
 * Created by bengu3 on 1/31/18.
 */

public class StartGameFacade {

    public CommandResult start(Game game) {
        String result = RootServerModel.getInstance().startGame(game);
        //setUp();
        CommandResult cr = new CommandResult();
        if(result.equals(utils.VALID)){
            cr.addClientCommand(activateGame(game));
        }
        cr.setResultType(result);
        return cr;
        //takes the game id and uses it ot shutdown the game and start everything
    }

    ClientCommand activateGame(Game game) {
        return CommandManager.getInstance().makeStartGameCommand(game);
    }


    /*
*   the set up finction{}
*   takes a game id
*   create a new bank to be used for the game consisting on new decks and a new starting top 5
    *   for each username in the game
    *       get them Dcards x3
    *       get the Tcards x4
    *       set their car amount
    *       set theor points to 0
    *       create a player object for them
    *       create a command result so that their pollar can pick it up
*       COmmandResult=setupGame (PLayer, gameID)
*       add COmmand to the pollar map(use the result and the username)
*       create a command so that they can have the top 5 cards in the bank
*       ""get face up cards""
*
 */


}
