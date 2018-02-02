package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class User {
    /*- username: String
- password: String
- gameList: List<Game>*/

    private String username;
    private String password;
    private List<Game> gameList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        gameList = new ArrayList<>();
    }

    public void addGame(Game game){
        gameList.add(game);
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

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<User> makeTestUsers(){
        ArrayList<User> users = new ArrayList<>();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID");
        u.addGame(game);
        users.add(u);
        User u2 = new User("sheila", "parker");
        Game game2 = new Game("game2", "gameID2");
        u2.addGame(game2);
        users.add(u2);
        User u3 = new User("jimbob", "duggar");
        Game game3 = new Game("game3", "gameID3");
        u3.addGame(game3);
        users.add(u3);
        User u4 = new User("joanna", "newsom");
        Game game4 = new Game("game4", "gameID4");
        u4.addGame(game4);
        users.add(u4);

        return users;
    }

    public User makeOneTestUser(){
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID");
        u.addGame(game);
        return u;
    }
}
