package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by bengu3 on 1/31/18.
 */

public class Game{
    public static Game mapToObject(LinkedTreeMap map){
      String gameName;
      String gameId;
      Map<String, Color> users=new TreeMap<>();
      boolean started;
      double maxPlayers;
      gameName = (String)map.get("gameName");
      gameId = (String)map.get("gameId");
      started = (boolean)map.get("started");
      double players = (double)map.get("maxPlayers");
      maxPlayers = (int)players;
      users = (Map<String, Color>)map.get("users");
      return new Game(users, gameName, gameId, maxPlayers, started);
    }

    private Map<String, Color> users;
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

    public Game(Map<String, Color> users, String gameName, String gameId, double maxPlayers, boolean started){
        this.gameName = gameName;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.started = started;
        this.users = users;
    }

    public String addUser(User u,  Color color){

        Color c=users.get(u);//ensures the player isnt already in

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

    public Map<String, Color> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Color> users) {
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

    public List<Game> makeTestGames(){
        ArrayList<Game> games = new ArrayList<Game>();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID", 2);
        game.addUser(u, Color.RED);

        User u2 = new User("sheila", "parker");
        game.addUser(u2, Color.BLUE);
        u.addGame(game);
        u2.addGame(game);

        Game game2 = new Game("game2", "gameID2", 2);

        User u3 = new User("jimbob", "duggar");
        User u4 = new User("joanna", "newsom");
        game2.addUser(u3, Color.GREEN);
        game2.addUser(u4, Color.YELLOW);
        u3.addGame(game2);
        u4.addGame(game2);

        games.add(game);
        games.add(game2);
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
