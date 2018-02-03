package com.groupryan.client.models;

import java.util.ArrayList;
import java.util.Observable;

public class RootClientModel extends Observable {

    private ArrayList<Game> games;
    private User user;

    private static RootClientModel single_instance = new RootClientModel();



    public static void addUser(User user){
        //TODO: check if i am doing add user as intended
        single_instance._addUser(user);
    }

    public static void addGame(Game game){
        single_instance._addGame(game);
    }

    public static void startGame(String gameId){
        single_instance._startGame(gameId);
    }


    private RootClientModel(){
        games = new ArrayList();
        user = new User();
    }

    private void _addUser(User user){
        //what do i do here
        this.user = user;
    }

    private void _addGame(Game game){
        games.add(game);
    }

    private void _startGame(String gameId){
        //TODO: START GAME HERE
        //make command so switch to game activity
    }

}
