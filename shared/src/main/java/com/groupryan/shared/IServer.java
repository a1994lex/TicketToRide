package com.groupryan.shared;

import com.groupryan.shared.commands.ICommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

import java.util.List;

public interface IServer {
    CommandResult createGame(Game game);
    CommandResult joinGame(String gameId, String userId);
    CommandResult startGame(String gameId);
    LoginResult register(User user);
    LoginResult login(User user);
    CommandResult getCommands();


}
