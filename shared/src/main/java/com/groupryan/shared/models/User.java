package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class User implements Comparable{
    public static User mapToObject(LinkedTreeMap map){
        String username;
        String password;
        List<Game> gameList=new ArrayList<>();
        username = (String)map.get("username");
        password = (String)map.get("password");
        ArrayList<LinkedTreeMap> treeGames = (ArrayList)map.get("gameList");
        if (treeGames.size()>0)
        {
            for (LinkedTreeMap l: treeGames){
                gameList.add(Game.mapToObject(l));
            }
        }
        //gameList = (List<Game>)map.get("gameList");
        return new User(username, password, gameList);
    }

    private String username;
    private String password;
    private String gameId;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        gameId = "";
    }

    private User(String username, String password, String gameId){
        this.username = username;
        this.password = password;
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<User> makeTestUsers(){
        ArrayList<User> users = new ArrayList();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID",5);
        //game.addUser(u, RED);
        u.addGame(game);
        users.add(u);
        User u2 = new User("sheila", "parker");
        //game.addUser(u2, BLUE);
        Game game2 = new Game("game2", "gameID2", 3);
        u2.addGame(game);
        users.add(u2);
        User u3 = new User("jimbob", "duggar");
        u3.addGame(game2);
        users.add(u3);
        User u4 = new User("joanna", "newsom");
        u4.addGame(game2);

        users.add(u4);
//        game2.addUser(u3, GREEN);
//        game2.addUser(u4, YELLOW);

        return users;
    }

    public User makeOneTestUser(){
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID", 3);
        u.addGame(game);
        return u;
    }


    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof User){
            User u=(User)o;
           return this.getUsername().compareTo(u.getUsername());
        }
        return 0;
    }
}
