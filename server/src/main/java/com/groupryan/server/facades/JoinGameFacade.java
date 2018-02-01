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
    CommandResult joinGame(Game game){return null;}
    String addUser(String userId, String gameId){return null;}
    ClientCommand createReturnCommand(String gameId){return null;}


}
