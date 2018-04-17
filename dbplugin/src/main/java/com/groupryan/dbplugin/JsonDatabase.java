package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonDatabase implements IDatabase {
    private File databaseFile;
    private JsonGameDao gameDao;
    private JsonUserDao userDao;
    private JsonObject databaseCopy;
    private String databaseAddress;
    private int maxCommands;

    public JsonDatabase() {
        gameDao = null;
        userDao = null;
        databaseFile = null;
        databaseAddress = "database.json";
    }

    public int getMaxCommands() {
        return maxCommands;
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    private JsonObject copyDatabase() {
        File databaseFile = getDatabaseFile();
        JsonObject databaseObj = null;
        try {
            String fileStr = FileUtils.readFileToString(databaseFile, "UTF-8");
            if (!fileStr.isEmpty()) {
                databaseObj = new JsonParser().parse(fileStr).getAsJsonObject();
            }
            else {
                databaseObj = new JsonObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return databaseObj;
    }

    private JsonArray getUsersAsJsonObject(JsonObject databaseObj) {
        if (databaseObj.has("users")) {
            return databaseObj.getAsJsonArray("users");
        }
        return new JsonArray();
    }

    private JsonArray getGamesAsJsonObject(JsonObject databaseObj) {
        if (databaseObj.has("games")) {
            return databaseObj.getAsJsonArray("games");
        }
        return new JsonArray();
    }

    private void checkJsonFileExists() {
        if (databaseFile == null) {
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
            System.out.println("file contents: " + fileContents);
            file.write(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonArray getUserDaoModifications() {
        return userDao.getUsersObj();
    }

    private JsonArray getGameDaoModifications() {
        return gameDao.getGamesObj();
    }

    private void updateGameDao() {
        this.gameDao = new JsonGameDao(getGamesAsJsonObject(databaseCopy), maxCommands);
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
        JsonArray gamesObj = getGameDaoModifications();
        JsonArray usersObj = getUserDaoModifications();
        databaseCopy.add("games", gamesObj);
        databaseCopy.add("users", usersObj);
        writeToDatabase();
        updateUserDao();
        updateGameDao();
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
    public void setMaxCommands(int commands) {
        if (databaseFile == null) {
            File jsonFile = new File(databaseAddress);
            try {
                jsonFile.createNewFile();
                databaseFile = jsonFile;
                this.maxCommands = commands;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            this.maxCommands = commands;
        }
    }

    public void clearDatabase() {
        checkJsonFileExists();
        databaseCopy.remove("users");
        databaseCopy.remove("games");
        writeToDatabase();
        updateGameDao();
        updateUserDao();
    }
}
