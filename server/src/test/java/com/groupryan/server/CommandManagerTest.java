package com.groupryan.server;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

// NOTE: Unfortunately each test depends on the previous one,
// so you need to run entire test suite at once
public class CommandManagerTest {

    private CommandManager commandManager = CommandManager.getInstance();

    private User user10 = new User("user10", "password10");
    private User user11 = new User("user11", "password11");
    private User user12 = new User("user12", "password12");
    private User user20 = new User("user20", "password20");
    private User user21 = new User("user21", "password21");
    private ArrayList<User> userList;

    private Game game1 = new Game("game1", "12345", 3);
    private Game game2 = new Game("game2", "12345", 2);
    private ArrayList<Game> gameList;


    @Before
    public void setUp() throws Exception {
        this.userList = new ArrayList<>();
        this.userList.add(user10);
        this.userList.add(user11);
        this.userList.add(user12);
        this.userList.add(user20);
        this.userList.add(user21);
        this.gameList = new ArrayList<>();
        this.gameList.add(game1);
        this.gameList.add(game2);
    }

    @Test
    public void loginAndRegisterTest() {
        for (User user : this.userList) {
            this.commandManager.makeRegisterCommand(user);
        }
        assertEquals(5, this.commandManager.numberOfUsers());
    }

    @Test
    public void createGameTest() {
        for (Game game : this.gameList) {
            this.commandManager.makeCreateGameCommand(game);
        }
        for (User user : this.userList) {
            assertEquals(2, this.commandManager.getCommands(user).size());
        }
    }

    @Test
    public void joinGameTest() {
        this.game1.addUser(user10, "RED");
        this.commandManager.makeJoinGameCommand(game1, user10, "RED");
        this.game1.addUser(user11, "YELLOW");
        this.commandManager.makeJoinGameCommand(game1, user11, "YELLOW");
        this.game1.addUser(user12, "BLACK");
        this.commandManager.makeJoinGameCommand(game1, user12, "BLACK");
        this.game2.addUser(user20, "YELLOW");
        this.commandManager.makeJoinGameCommand(game2, user20, "YELLOW");
        this.game2.addUser(user21, "BLACK");
        this.commandManager.makeJoinGameCommand(game2, user21, "BLACK");
        for (User user : this.userList) {
            assertEquals(5, this.commandManager.getCommands(user).size());
        }
    }

    @Test
    public void startGameTest() {
        this.game1.setStarted(true);
//        this.commandManager.makeStartGameCommand(game1, );
        this.game2.setStarted(true);
//        this.commandManager.makeStartGameCommand(game2, );
    }
}