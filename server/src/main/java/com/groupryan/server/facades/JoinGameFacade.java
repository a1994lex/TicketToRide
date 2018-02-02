package com.groupryan.server.facades;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class JoinGameFacade {
    String userId;
    String gameId;
    String result;

    public CommandResult joinGame(Game game){
        //this should have the id too, allws the player to join the game
        //adds them to the game,
        //returns the command

        return null;}
    String addUser(String userId, String gameId){return null;}
    ClientCommand createReturnCommand(String gameId){return null;}


}
