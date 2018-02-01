package com.groupryan.server.facades;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CreateGameFacade {
    String userId;
    Game game;

    CommandResult create(Game game){return null;}
    String addGame(Game game){return null;}// String gameId
  ClientCommand createReturnCommand(String gameId){return null;}


}
