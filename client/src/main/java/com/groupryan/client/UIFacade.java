package com.groupryan.client;

import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.LoginResult;

import java.util.HashMap;
import java.util.Map;

public class UIFacade {

    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    public UIFacade() {}

    public void createGame(Color userColor, String gameName, String username, String password, int numberOfPlayers) {
        Game game = new Game(gameName, null, numberOfPlayers);
        User user = new User(username, password);
        Map<User, Color> users = new HashMap<User, Color>();
        users.put(user, userColor);
        game.setUsers(users);
        ServerProxy.getInstance().createGame(game);
    }

    public LoginResult login(String username, String password) {
        User user = new User(username, password);
        LoginResult loginResult = ServerProxy.getInstance().login(user);
        return loginResult;
    }

    public void register(String username, String password) {
        User user = new User(username, password);
        ServerProxy.getInstance().register(user);
    }

    public void joinGame(String gameId, String userId) {
        ServerProxy.getInstance().joinGame(gameId, userId);
    }

    public void startGame(String gameId) {
        ServerProxy.getInstance().startGame(gameId);
    }
}
