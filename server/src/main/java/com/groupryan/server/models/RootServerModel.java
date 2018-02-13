package com.groupryan.server.models;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.utils;

import java.util.ArrayList;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RootServerModel {

    private ArrayList<Game> games;
    private ArrayList<User> users;

    private static RootServerModel single_instance; /*= new RootServerModel();*/

    public static RootServerModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootServerModel();
            Game game = new Game();
            single_instance.games = (ArrayList) game.makeTestGames();
        }
        return single_instance;
    }

    public ArrayList<Game> getGames() {
        ArrayList<Game> notStartedGames=new ArrayList<>();
        for (Game g:games) {
            if(!g.isStarted()){
                notStartedGames.add(g);
            }
        }
        return notStartedGames;
    }

    public ArrayList<User> getUsers() {
        return users;
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
        return utils.VALID;
    }

    private String _addGame(Game game) {

        games.add(game);
        return utils.GAME_CREATED;
    }

    public String _createGame(Game game){
        for (Game g:games) {
            if(g.getGameName().equals(game.getGameName())){
                return utils.GAME_NAME_IN_USE;
            }
        }
        return _addGame(game);
    }

    private String _confirmUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                if (u.getPassword().equals(user.getPassword())) {
                    return utils.VALID;
                }
                return utils.INVALID_PW;
            }
        }
        return utils.INVALID_UN;
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
                if(g.getMaxPlayers()==g.getUsers().size()){
                    return utils.FULL_GAME;
                }
               return g.addUser(user, userColor);
            }
        }
        return utils.INVALID_GAMEID;
    }


    private String _startGame(Game game) {
        for (Game g : games) {
            if (g.equals(game)) {
                g.setStarted(true);
                return utils.VALID;
            }
        }
        return utils.INVALID_GAMEID;
    }

}
