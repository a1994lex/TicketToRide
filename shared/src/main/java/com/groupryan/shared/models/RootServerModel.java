package com.groupryan.shared.models;

import java.util.ArrayList;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RootServerModel {
    private ArrayList<Game> games;
    private ArrayList<User> users;

    private static RootServerModel single_instance = new RootServerModel();



    public static void addUser(User user){
        single_instance._addUser(user);
    }

    public static void addGame(Game game){
        single_instance._addGame(game);
    }

    public static void startGame(String gameId){
        single_instance._startGame(gameId);
    }


    private RootServerModel(){
        games = new ArrayList<>();
        users = new ArrayList<>();
    }

    private void _addUser(User user){
        users.add(user);
    }

    private void _addGame(Game game){
        games.add(game);
    }

    private void _startGame(String gameId){
        //TODO: START GAME HERE
        //make command so switch to game activity!
    }

}
