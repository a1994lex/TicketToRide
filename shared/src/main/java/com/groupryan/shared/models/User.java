package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class User {

    private String username;
    private String password;
    private List<Game> gameList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        gameList = new ArrayList();
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
        ArrayList<User> users = new ArrayList();
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID",2);
        //game.addUser(u, Color.RED);
        u.addGame(game);
        users.add(u);
        User u2 = new User("sheila", "parker");
        //game.addUser(u2, Color.BLUE);
        Game game2 = new Game("game2", "gameID2", 2);
        u2.addGame(game);
        users.add(u2);
        User u3 = new User("jimbob", "duggar");
        u3.addGame(game2);
        users.add(u3);
        User u4 = new User("joanna", "newsom");
        u4.addGame(game2);

        users.add(u4);
//        game2.addUser(u3, Color.GREEN);
//        game2.addUser(u4, Color.YELLOW);

        return users;
    }

    public User makeOneTestUser(){
        User u = new User("clairescout", "gammon");
        Game game = new Game("game1", "gameID", 3);
        u.addGame(game);
        return u;
    }
}
