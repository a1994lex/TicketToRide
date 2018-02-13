package com.groupryan.shared;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

public interface IServer {
    CommandResult createGame(Game game);

    CommandResult joinGame(Game game, User user, String userColor);

    CommandResult startGame(Game game);

    LoginResult register(User user);

    LoginResult login(User user);
}
