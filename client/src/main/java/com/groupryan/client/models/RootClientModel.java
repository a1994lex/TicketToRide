package com.groupryan.client.models;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import java.util.ArrayList;
import java.util.Observable;

public class RootClientModel extends Observable {

    private ArrayList<Game> games;
    private User user;

    private static RootClientModel single_instance = new RootClientModel();

    public static RootClientModel getInstance(){
        return single_instance;
    }

    public static void addUser(User user) {
        // TODO: check if i am doing add user as intended
        single_instance._addUser(user);
    }

    public static void addGame(Game game) {
        single_instance._addGame(game);
    }

    public static void startGame(Game game) {
        single_instance._startGame(game);
    }

    public static void addUserToGame(Game game, User user, Color userColor) {
        single_instance._addUserToGame(game, user, userColor);

    }

    private RootClientModel() {
        games = new ArrayList();
        user = new User(null, null);
    }

    private void _addUser(User user) {

        this.user = user;
        setChanged();
        notifyObservers("logged in");
        //notify register observer
    }

    private void _addGame(Game game) {
        games.add(game);
    }

    private void _startGame(Game game) {
        for (Game g : games) {
            if (g.equals(game)) {
                g.setStarted(true);
            }
        }
        //notify observers
    }

    private void _addUserToGame(Game game, User user, Color userColor) {
        for (Game g : this.games) {
            if (g.equals(game)) {
                g.addUser(user, userColor);
            }
        }
        //notify observers
    }

}
