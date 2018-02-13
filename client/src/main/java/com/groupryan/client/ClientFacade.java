package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.*;

public class ClientFacade {

    public ClientFacade() {}

    public void joinGame(Game game, User user, Color userColor) {
        RootClientModel.addUserToGame(game, user, userColor);
    }

    public void createGame(Game game) {
        RootClientModel.addGame(game);
        //RootClientModel.addUserToGame();
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
