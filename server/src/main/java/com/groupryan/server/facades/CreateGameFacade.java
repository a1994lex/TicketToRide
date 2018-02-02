package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CreateGameFacade {
    String userId;
    Game game;

    public CommandResult createGame() {
        //call the model and create a game
        //add the game to the list of games
        //return everything
        return null;
    }
        CommandResult create (Game game){
            return null;
        }
        String addGame (Game game){
            return null;
        }// String gameId

        CommandManager cm=CommandManager.getInstance();
        ClientCommand createReturnCommand (String gameId){
            return null;
        }


}
