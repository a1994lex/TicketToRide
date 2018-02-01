package com.groupryan.shared;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

public interface IServer {
    CommandResult createGame(Game game);
    CommandResult joinGame(String gameId, String userId);
    CommandResult startGame(String gameId);
    CommandResult register(User user);
    CommandResult login(User user);
    CommandResult getCommands();


}
