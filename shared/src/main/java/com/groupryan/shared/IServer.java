package com.groupryan.shared;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

import java.util.List;

public interface IServer {
    CommandResult createGame(Game game);

    CommandResult joinGame(Game game, User user, String userColor);

    CommandResult startGame(String gameId);

    LoginResult register(User user);

    LoginResult login(User user);

    CommandResult discardDestinationCard(List<Integer> cardID, String username);

    CommandResult sendChat(String gameId);

    CommandResult drawColorCard(String username);

    CommandResult updateFaceUp(String gameId);

    CommandResult drawDestinationCards(String username);
}
