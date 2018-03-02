package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import static com.groupryan.shared.utils.BLUE;
import static com.groupryan.shared.utils.GREEN;
import static com.groupryan.shared.utils.RED;
import static com.groupryan.shared.utils.YELLOW;

/**
 * Created by bengu3 on 1/31/18.
 */

public class Game{
    public static Game mapToObject(LinkedTreeMap map){
      String gameName;
      String gameId;
      Map<String, String> users=new TreeMap<>();
      boolean started;
      double maxPlayers;
      gameName = (String)map.get("gameName");
      gameId = (String)map.get("gameId");
      started = (boolean)map.get("started");
      double players = (double)map.get("maxPlayers");
      maxPlayers = (int)players;
      users = (Map<String, String>)map.get("users");
      return new Game(users, gameName, gameId, maxPlayers, started);
    }

    private Map<String, String> users;
    private String gameName;
    private String gameId;
    private boolean started = false;
    private double maxPlayers;

    public Game(){}

    public Game(String gameName, String gameId, double maxPlayers){
        users=new TreeMap<>();
        this.gameName = gameName;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
    }

    public Game(Map<String, String> users, String gameName, String gameId, double maxPlayers, boolean started){
        this.gameName = gameName;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.started = started;
        this.users = users;
    }

    public String addUser(User u,  String color){

        String c=users.get(u.getUsername());//ensures the player isnt already in

        if(!started && c==null){
            if(users.containsValue(color)){
                return "Color in use";
            }
            users.put(u.getUsername(), color);
            //return "User added to "+gameId;
            u.addGame(this);
            return utils.VALID;
        }
        return "User already in game";
    }


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
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

    public double getMaxPlayers() { return maxPlayers; }

    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    public HashMap<String,Game> makeTestGames(){
        HashMap<String,Game> games = new HashMap<>();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID", 5);
        game.addUser(u, RED);

        User u2 = new User("sheila", "parker");
        game.addUser(u2, BLUE);
        u.addGame(game);
        u2.addGame(game);

        Game game2 = new Game("game2", "gameID2", 3);

        User u3 = new User("jimbob", "duggar");
        User u4 = new User("joanna", "newsom");
        game2.addUser(u3, GREEN);
        game2.addUser(u4, YELLOW);
        u3.addGame(game2);
        u4.addGame(game2);

        games.put(game.gameId, game);
        games.put(game2.gameId, game2);
        return games;

    }


    @Override
    public int hashCode() {
        int result = gameName.hashCode();
        result = 31 * result + gameId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId != null ? gameId.equals(game.gameId) : game.gameId == null;
    }

}
