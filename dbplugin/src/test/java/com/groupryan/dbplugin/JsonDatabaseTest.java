package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Daniel on 4/13/2018.
 */
public class JsonDatabaseTest {
    private JsonDatabase database;

    private List<User> addUsers() {
        JsonUserDao userDao = (JsonUserDao) database.getUserDao();
        String username1 = "daniel";
        String pass1 = "najarro";
        String gameName = "gname";
        String gameId = "1234567890a";
        User user = new User(username1, pass1);
        userDao.registerUser(user);
        userDao.addGameToUser(user, gameId);
        return userDao.getUsersList();
    }

    private List<byte[]> addGames() {
        JsonGameDao gameDao = (JsonGameDao) database.getGameDao();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gameName1 = "gameName1";
        String gameName2 = "gameName2";
        String gameId1 = "1234a";
        String gameId2 = "1234567890a";
        Game game1 = new Game(gameName1, gameId1, 2);
        Game game2 = new Game(gameName2, gameId2, 2);
        String gameStr = gson.toJson(game1);
        String gameStr2 = gson.toJson(game2);
        byte[] game1Bytes = gameStr.getBytes();
        byte[] game2Bytes = gameStr2.getBytes();
        List<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);
        gameDao.updateGameSnapshot("1234a", game1Bytes);
        gameDao.updateGameSnapshot("1234567890a", game2Bytes);
        gameDao.updateGameSnapshot("1234a", game1Bytes);
        return gameDao.getAllSnapshots();
    }

    @Before
    public void setUp() throws Exception {
        database = new JsonDatabase();
        database.setMaxCommands(10);
        database.startTransaction();
    }

    @After
    public void tearDown() throws Exception {
        database.clearDatabase();
        database.endTransaction();
    }

    @Test
    public void userDaoTest() throws Exception {
        List<User> users = addUsers();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (int i = 0; i < users.size(); i++) {
            System.out.println("username: " + users.get(i).getUsername() +
                                " password: " + users.get(i).getPassword() +
                                " gameId: " + gson.toJson(users.get(i).getGameId()));
            assertEquals(users.get(i).getUsername(), "daniel");
            assertEquals(users.get(i).getPassword(), "najarro");
            assertEquals(users.get(i).getGameId(), "1234567890a");
        }
        database.endTransaction();
    }

    @Test
    public void gameDaoTest() throws Exception {
        List<byte[]> snapshots = addGames();
        JsonGameDao gameDao = (JsonGameDao) database.getGameDao();
        ServerCommand sc = new ServerCommandFactory().createDrawThreeCardsCommand("daniel");
        ServerCommand sc2 = new ServerCommandFactory().createDrawColorCardCommand(15, "daniel");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        byte[] cmd1 = gson.toJson(sc).getBytes();
        byte[] cmd2 = gson.toJson(sc2).getBytes();
        gameDao.addCommandToGame("1234a", cmd1, 0);
        gameDao.addCommandToGame("1234567890a", cmd2, 0);
        gameDao.updateGameSnapshot("1234a", snapshots.get(0));
        Map<String, List<byte[]>> commands = gameDao.getAllCommands();
        assertEquals(commands.size(), 1);
        for (int i = 0; i < snapshots.size(); i++) {
            String snapshot = new String(snapshots.get(i));
            System.out.println("SNAPSHOT:\n" + gson.toJson(snapshot));
        }

        for (Map.Entry<String, List<byte[]>> entry : commands.entrySet()) {
            List<byte[]> commandList = entry.getValue();
            for (int i = 0; i < commandList.size(); i++) {
                String command = new String(commandList.get(i));
                System.out.println("COMMAND:\n" + gson.toJson(command));
            }
        }
        List<byte[]> testCommandList = gameDao.getCommandsByGameId("1234a");
        assertEquals(testCommandList.size(), 0);

        for (int i = 0; i < testCommandList.size(); i++) {
            String command = new String(testCommandList.get(i));
            System.out.println("COMMAND:\n" + gson.toJson(command));
        }

        database.endTransaction();
    }
}