package com.groupryan.server.models;

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

    public static RootServerModel getInstance(){
        if(single_instance == null) {
            single_instance = new RootServerModel();
        }
        return single_instance;
    }

    public static void addUser(User user){
        single_instance._addUser(user);
    }

    public static void addGame(Game game){
        single_instance._addGame(game);
    }



    private RootServerModel(){
        games = new ArrayList();
        users = new ArrayList();
    }

    public String _addUser(User user){
        users.add(user);
        return "valid";
    }

    public String _addGame(Game game){
        games.add(game);
        return "null";
    }
    
    public String _confirmUser(String userId, String password){
        for (User user:users) {
            if (user.getUsername().equals(userId)){
                if(user.getPassword().equals(password)){
                    return "valid";
                }
                return "invalid password";
            }
        }
        return "invalid username";
    }

    public Boolean _checkUser(String userId){
        for (User u:users) {
            if(u.getUsername().equals(userId)){
                return true;
            }
        }
        return false;
    }

    public String _addUserToGame(String gameId, String userId){return null;} /////needs to be made



    private void _startGame(String gameId){
        for(Game game : games){
            if(game.getGameId().equals(gameId)){
                game.setStarted(true);
            }
        }
        //make command so switch to game activity!
        return "null:";
    }

}
