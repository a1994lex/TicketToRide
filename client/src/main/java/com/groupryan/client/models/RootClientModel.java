package com.groupryan.client.models;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class RootClientModel extends Observable {

    private ArrayList<Game> games;
    private User user;
    private ClientGame clientGame;

    public static RootClientModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootClientModel();
        }
        return single_instance;
    }

    public static RootClientModel getSingle_instance(){
        return single_instance;
    }

    public static ArrayList<Game> getGames() {
        return single_instance.games;
    }

    public static User getUser() {
        return single_instance.user;
    }

    public static ClientGame getCurrentGame() {
        return single_instance.clientGame;
    }

    private static RootClientModel single_instance = new RootClientModel();


    public static void addUser(User user) {
        // TODO: check if i am doing add user as intended
        single_instance._addUser(user);
    }

    public static void addGame(Game game) {
        single_instance._addGame(game);
    }

    public static void startGame(Game game, Player p) {
        single_instance._startGame(game, p);
    }

    public static void addUserToGame(Game game, User user, String userColor) {
        single_instance._addUserToGame(game, user, userColor);
    }
    public static void setGames(List<Game> games){
        single_instance._setGames(games);
    }

    private RootClientModel() {
        games = new ArrayList();
        user = new User(null, null);
    }

    private void _addUser(User user) {
        this.user = user;
        setChanged();
        notifyObservers();
    }

    private void _addGame(Game game) {
        games.add(game);
        setChanged();
        notifyObservers();
    }

    private void _startGame(Game game, Player p) {
        clientGame = new ClientGame(game, p);
        //MAKE THE PLAYA LIVEEEEE
        for (Game g : games) {
            if (g.equals(game)) {
                g.setStarted(true);
                games.remove(game);
                setChanged();
                notifyObservers();
            }
        }
    }

    private void _addUserToGame(Game game, User user, String userColor) {
        for (Game g : this.games) {
            if (g.equals(game)) {
                g.addUser(user, userColor);
                this.user.addGame(g);
                setChanged();
                notifyObservers();
            }
        }
    }


    private void _setGames(List<Game> games) {
        this.games = (ArrayList<Game>)games;
    }
}
