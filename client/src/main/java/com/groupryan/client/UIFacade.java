package com.groupryan.client;

import com.groupryan.shared.commands.ServerCommandFactory;
//import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import java.util.HashMap;
import java.util.Map;

public class UIFacade {

    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    public UIFacade() {}

    void createGame(String userColor, String gameName, String username, String password, int numberOfPlayers) {
        Game game = new Game(gameName, "0", numberOfPlayers);
        User user = new User(username, password);
        Map<User, String> users = new HashMap<User, String>();
       // users.put(user, userColor);
       // game.setUsers(users);
        ServerProxy.getInstance().createGame(game);
    }

    void login(String username, String password) {
        User user = new User(username, password);
        ServerProxy.getInstance().login(user);
    }

    void register(String username, String password) {
        User user = new User(username, password);
        ServerProxy.getInstance().register(user);
    }

    void joinGame(String gameId, String userId) {
        ServerProxy.getInstance().joinGame(gameId, userId);
    }

    void startGame(String gameId) {
        ServerProxy.getInstance().startGame(gameId);
    }
}
