package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.*;

public class ClientFacade {

    private static ClientFacade instance;

    public static ClientFacade getInstance() {
        if (instance == null) {
            instance = new ClientFacade();
        }
        return instance;
    }

    private ClientFacade() {
    }

    public void joinGame(Game game, User user, Color userColor) {
        RootClientModel.addUserToGame(game, user, userColor);
    }

    public void createGame(Game game) {
        RootClientModel.addGame(game);
    }

    public void startGame(Game game) {
        RootClientModel.startGame(game);
    }

    public void login(User user) {
        RootClientModel.addUser(user);
    }

    public void register(User user) {
        RootClientModel.addUser(user);
    }
}
