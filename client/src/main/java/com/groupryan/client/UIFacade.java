package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
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

    public void createGame(Color userColor, String gameName, int numberOfPlayers) {
        Game game = new Game(gameName, "0", numberOfPlayers);
        User user = RootClientModel.getUser();
        Map<User, Color> users = new HashMap<User, Color>();
        users.put(user, userColor);
        //game.setUsers(users);
        ServerProxy.getInstance().createGame(game);
    }

    public LoginResult login(String username, String password) {
        User user = new User(username, password);
        LoginResult lr = ServerProxy.getInstance().login(user);
        if (lr.getGameList() != null){
            RootClientModel.setGames(lr.getGameList());
        }
        return lr;
    }

    public LoginResult register(String username, String password) {
        User user = new User(username, password);
        LoginResult lr = ServerProxy.getInstance().register(user);
        if (lr.getGameList() != null){
            RootClientModel.setGames(lr.getGameList());
        }
        return lr;
    }

    public void joinGame(Game game, Color userColor) {
        User user = RootClientModel.getUser();
        ServerProxy.getInstance().joinGame(game, user, userColor);
    }

    public void startGame(Game game) {
        ServerProxy.getInstance().startGame(game);
    }
}
