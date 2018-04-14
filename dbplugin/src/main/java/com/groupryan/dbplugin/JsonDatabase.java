package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonDatabase implements IDatabase {
    private File databaseFile;
    private JsonGameDao gameDao;
    private JsonUserDao userDao;
    private JsonObject databaseCopy;
    private String databaseAddress;

    public JsonDatabase() {
        gameDao = null;
        userDao = null;
        databaseAddress = "database.json";
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    private JsonObject copyDatabase() {
        File databaseFile = getDatabaseFile();
        JsonObject databaseObj = null;
        try {
            String fileStr = FileUtils.readFileToString(databaseFile, "UTF-8");
            databaseObj = new JsonParser().parse(fileStr).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return databaseObj;
    }

    private JsonObject getUsersAsJsonObject(JsonObject databaseObj) {
        if (databaseObj.has("users")) {
            return databaseObj.getAsJsonObject("users");
        }
        return null;
    }

    private JsonObject getGamesAsJsonObject(JsonObject databaseObj) {
        if (databaseObj.has("games")) {
            return databaseObj.getAsJsonObject("games");
        }
        return null;
    }

    private void checkJsonFileExists() {
        if (!databaseFile.isFile()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToDatabase() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter file = new FileWriter(databaseAddress)) {
            String fileContents = gson.toJson(databaseCopy);
            file.write(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject getUserDaoModifications() {
        return userDao.getUsersObj();
    }

    private JsonObject getGameDaoModifications() {
        return gameDao.getGamesObj();
    }

    private void updateGameDao() {
        this.gameDao = new JsonGameDao(getGamesAsJsonObject(databaseCopy));
    }

    private void updateUserDao() {
        this.userDao = new JsonUserDao(getUsersAsJsonObject(databaseCopy));
    }

    @Override
    public void startTransaction() {
        checkJsonFileExists();
        this.databaseCopy = copyDatabase();
        if (userDao == null) {
            updateUserDao();
        }
        if (gameDao == null) {
            updateGameDao();
        }
        System.out.println("Transaction started.");
    }

    @Override
    public void endTransaction() {
        JsonObject gamesObj = getGameDaoModifications();
        JsonObject usersObj = getUserDaoModifications();
        databaseCopy.add("games", gamesObj);
        databaseCopy.add("users", usersObj);
        writeToDatabase();
        userDao = null;
        gameDao = null;
        System.out.println("Database updated successfully!");
    }

    @Override
    public IUserDao getUserDao() {
        if(userDao == null) {
            updateUserDao();
        }
        return userDao;
    }

    @Override
    public IGameDao getGameDao() {
        if(gameDao == null) {
            updateGameDao();
        }
        return gameDao;
    }

    @Override
    public void setUp() {
        File jsonFile = new File(databaseAddress);
        if (!jsonFile.isFile()) {
            try {
                jsonFile.createNewFile();
                databaseFile = jsonFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
