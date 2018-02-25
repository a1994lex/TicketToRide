package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Playa;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class StartGameFacade {
    ArrayList<ClientCommand> commands=new ArrayList<>();


    public CommandResult start(String gameId) {
        //sets the game id = to TRUE and returns the literal VALID
        String result = RootServerModel.getInstance().startGame(gameId);
        CommandResult cr = new CommandResult();
        if(result.equals(utils.VALID)){
            //if valid it will create a start game command
            activateGame(gameId);
            //then it will prepare the game to be started
        }
        cr.setResultType(result);
        return cr;
        //takes the game id and uses it ot shutdown the game and start everything
    }

    void activateGame(String gameId) {
        RootServerModel root= RootServerModel.getInstance();
        Game g=root.getGame(gameId);
        root.createServerGame(gameId);
        for (Map.Entry<String, String> entry : g.getUsers().entrySet()) {
            Playa p=root.createPlaya(gameId, entry);
            root.addPlayatoGame(p.getUsername(), gameId);
            CommandManager.getInstance().makeStartGameCommand(g, p);
            //we create a create playa command call using the last line as the playa value
        }
      //  return CommandManager.getInstance().makeStartGameCommand(g,p);
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
