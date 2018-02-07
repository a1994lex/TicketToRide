package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bengu3 on 1/31/18.
 */

public class Game {

    private Map<User, String> users;
    private String gameName;
    private String gameId;
    private boolean started = false;
    private int maxPlayers;

    public Game(){}

    public Game(String gameName, String gameId, int maxPlayers){
        this.gameName = gameName;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
    }

    public void addUser(User u, String color){
        if(!started){
            users.put(u, color);
        }
        //RETURN SOMETHING SO THEY KNOW USER WAS NOT ADDED.
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Map<User, String> getUsers() {
        return users;
    }

    public void setUsers(Map<User, String> users) {
        this.users = users;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void makeGameId(){
        gameId = UUID.randomUUID().toString();
    }

    public int getMaxPlayers() { return maxPlayers; }

    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    public List<Game> makeTestGames(){
        ArrayList<Game> games = new ArrayList<Game>();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID");
        game.addUser(u, "red");
        User u2 = new User("sheila", "parker");
        game.addUser(u2, "blue");
        u.addGame(game);
        u2.addGame(game);

        Game game2 = new Game("game2", "gameID2");
        User u3 = new User("jimbob", "duggar");
        User u4 = new User("joanna", "newsom");
        game2.addUser(u3, "green");
        game2.addUser(u4, "yellow");
        u3.addGame(game2);
        u4.addGame(game2);

        games.add(game);
        games.add(game2);
        return games;

    }
}
