package com.groupryan.server.models;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import java.util.ArrayList;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RootServerModel {

    private ArrayList<Game> games;
    private ArrayList<User> users;

    private static RootServerModel single_instance = new RootServerModel();

    public static RootServerModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootServerModel();
        }
        return single_instance;
    }

    public static String addUser(User user) {
        return single_instance._addUser(user);
    }

    public static String addGame(Game game) {
        return single_instance._addGame(game);
    }

    public static String confirmUser(User user) {
        return single_instance._confirmUser(user);
    }

    public static Boolean checkUser(User user) {
        return single_instance._checkUser(user);
    }

    public static String addUserToGame(Game game, User user, Color userColor) {
        return single_instance._addUserToGame(game, user, userColor);
    }

    public static String startGame(Game game) {
        return single_instance._startGame(game);
    }

    private RootServerModel() {
        games = new ArrayList();
        users = new ArrayList();
    }

    private String _addUser(User user) {
        users.add(user);
        return "valid";
    }

    private String _addGame(Game game) {
        games.add(game);
        return "null";
    }

    private String _confirmUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                if (u.getPassword().equals(user.getPassword())) {
                    return "valid";
                }
                return "invalid password";
            }
        }
        return "invalid username";
    }

    private Boolean _checkUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private String _addUserToGame(Game game, User user, Color userColor) {
        for (Game g : this.games) {
            if (g.equals(game)) {
                g.addUser(user, userColor);
            }
        }
        return "valid";
    }


    private String _startGame(Game game) {
        for (Game g : games) {
            if (g.equals(game)) {
                g.setStarted(true);
            }
        }
        // TODO: make command to switch to game activity!
        return "good";
    }

}
