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

    private static UIFacade instance;

    public static UIFacade getInstance() {
        if (instance == null) {
            instance = new UIFacade();
        }
        return instance;
    }

    private UIFacade() {
    }

    void createGame(Color userColor, String gameName, String username, String password, int numberOfPlayers) {
        Game game = new Game(gameName, "0", numberOfPlayers);
        User user = new User(username, password);
        Map<User, Color> users = new HashMap<User, Color>();
        users.put(user, userColor);
        //game.setUsers(users);
        ServerProxy.getInstance().createGame(game);
    }

    public LoginResult login(String username, String password) {
        User user = new User(username, password);
        return ServerProxy.getInstance().login(user);
    }

    public LoginResult register(String username, String password) {
        User user = new User(username, password);
        return ServerProxy.getInstance().register(user);
    }

    void joinGame(Game game, User user, Color userColor) {
        ServerProxy.getInstance().joinGame(game, user, userColor);
    }

    void startGame(Game game) {
        ServerProxy.getInstance().startGame(game);
    }
}
