package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonGameDao implements IGameDao {

    public JsonGameDao() {

    }

    private JsonObject getDatabaseAsJsonObject() {
        JsonDatabase database = new JsonDatabase();
        File databaseFile = database.getDatabaseFile();
        JsonObject databaseObj = null;
        try {
            String jsonStr = FileUtils.readFileToString(databaseFile, "UTF-8");
            JsonElement jsonElem = new JsonParser().parse(jsonStr);
            databaseObj = jsonElem.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return databaseObj;
    }

    private JsonObject addGamesElement(JsonObject databaseObj) {
        List<Game> games = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gamesJson = gson.toJson(games);
        JsonElement gamesElem = new JsonParser().parse(gamesJson);
        databaseObj.add("games", gamesElem);
        return databaseObj;
    }

    private JsonObject addCommandsElement(JsonObject databaseObj, JsonArray gamesArray) {
        List<ClientCommand> commands = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String commandsJson = gson.toJson(commands);
        JsonElement commandsElem = new JsonParser().parse(commandsJson);
        JsonObject commandsObj = commandsElem.getAsJsonObject();

        for (int i = 0; i < gamesArray.size(); i++) {
            JsonElement gameElem = gamesArray.get(i);
            JsonObject gameObj = gameElem.getAsJsonObject();
            if (!gameObj.has("commands")) {
                gameObj.add("commands", commandsObj);
                gamesArray.set(i, gameObj);
            }
        }
        databaseObj.add("games", gamesArray);
        return databaseObj;
    }

    private JsonObject findGameById(String gameId, JsonArray gamesArray) {
        int i = 0;
        JsonObject obj = null;
        while (i < gamesArray.size()) {
            JsonElement game = gamesArray.get(i);
            JsonObject gameObj = game.getAsJsonObject();
            if (gameObj.get("gameId").equals(gameId)) {
                obj = gameObj;
                i = gamesArray.size();
            }
            i++;
        }
        return obj;
    }

    private int getGameIndex(String gameId, JsonArray gamesArray) {
        int i = 0;
        while (i < gamesArray.size()) {
            JsonElement game = gamesArray.get(i);
            JsonObject gameObj = game.getAsJsonObject();
            if (gameObj.get("gameId").equals(gameId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public void addCommandToGame(String gameid, byte[] command) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        if (!databaseObj.has("games")) {
            databaseObj = addGamesElement(databaseObj);
        }
        JsonElement gamesElem = databaseObj.get("games");
        JsonArray gamesArray = gamesElem.getAsJsonArray();
        if (gamesArray.size() > 0) {
            databaseObj = addCommandsElement(databaseObj, gamesArray);
            gamesElem = databaseObj.get("games");
            gamesArray = gamesElem.getAsJsonArray();
            JsonObject gameObj = findGameById(gameid, gamesArray);
            if (gameObj != null) {
                String commandStr = new String(command);
                JsonElement commandElem = new JsonParser().parse(commandStr);
                JsonArray commandsArray = gameObj.get("commands").getAsJsonArray();
                commandsArray.add(commandElem);
                gameObj.add("commands", commandsArray);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String gameStr = gson.toJson(gameObj);
                JsonElement gameElem = new JsonParser().parse(gameStr);
                int gameIndex = getGameIndex(gameid, gamesArray);
                gamesArray.set(gameIndex, gameElem);
                databaseObj.add("games", gamesArray);
            }
        }
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        String snapshotStr = new String(gameSnapshot);
        JsonElement snapshotElem = new JsonParser().parse(snapshotStr);
        if (!databaseObj.has("games")) {
            databaseObj = addGamesElement(databaseObj);
        }
        JsonArray gamesArray = databaseObj.getAsJsonArray("games");
        JsonObject gameObj = findGameById(gameid, gamesArray);
        if (gameObj == null) {
            gamesArray.add(snapshotElem);
        }
        else {
            int gameIndex = getGameIndex(gameid, gamesArray);
            if (gameIndex != -1) {
                gamesArray.set(gameIndex, snapshotElem);
            }
            else {
                gamesArray.add(snapshotElem);
            }
        }
    }

    @Override
    public void clearCommands(String gameid) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        if (!databaseObj.has("games")) {
            databaseObj = addGamesElement(databaseObj);
        }
        JsonArray gamesArray = databaseObj.getAsJsonArray("games");
        if (gamesArray.size() > 0) {
            JsonObject gameObj = findGameById(gameid, gamesArray);
            if (gameObj != null) {
                if (gameObj.has("commands")) {
                    JsonArray commandsArray = gameObj.getAsJsonArray("commands");
                    int i = 0;
                    while (commandsArray.size() > 0) {
                        commandsArray.remove(i);
                    }
                    gameObj.add("commands", commandsArray);
                    int gameIndex = getGameIndex(gameid, gamesArray);
                    gamesArray.set(gameIndex, gameObj);
                    databaseObj.add("games", gamesArray);
                }
            }
        }
    }

    @Override
    public List<byte[]> getCommandsByGameId(String gameid) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        List<byte[]> commandsList = new ArrayList<>();
        if (databaseObj.has("games")) {
            JsonArray gamesArray = databaseObj.getAsJsonArray("games");
            JsonObject game = findGameById(gameid, gamesArray);
            if (game != null) {
                if (game.has("commands")) {
                    JsonArray commandsArray = game.getAsJsonArray("commands");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    for (int i = 0; i < commandsArray.size(); i++) {
                        String command = gson.toJson(commandsArray.get(i));
                        commandsList.add(command.getBytes());
                    }
                }
            }
        }
        return commandsList;
    }

    @Override
    public String getSnapshotByGameId(String gameid) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        String snapshot = null;
        if (databaseObj.has("games")) {
            JsonArray gamesArray = databaseObj.getAsJsonArray("games");
            JsonObject gameObj = findGameById(gameid, gamesArray);
            if (gameObj != null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (gameObj.has("commands")) {
                    JsonArray commandsCopy = gameObj.getAsJsonArray("commands");
                    gameObj.remove("commands");
                    snapshot = gson.toJson(gameObj);
                    gameObj.add("commands", commandsCopy);
                }
                else {
                    snapshot = gson.toJson(gameObj);
                }
            }
        }
        return snapshot;
    }

    @Override
    public List<byte[]> getAllCommands() {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        List<byte[]> commands = new ArrayList<>();
        if (databaseObj.has("games")) {
            JsonArray gamesArray = databaseObj.getAsJsonArray("games");
            for (int i = 0; i < gamesArray.size(); i++) {
                JsonObject gameObj = gamesArray.get(i).getAsJsonObject();
                if (gameObj.has("commands")) {
                    JsonArray commandsArray = gameObj.getAsJsonArray("commands");
                    for (int j = 0; j < commandsArray.size(); j++) {
                        JsonElement command = commandsArray.get(i);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String commandStr = gson.toJson(command);
                        commands.add(commandStr.getBytes());
                    }
                }
            }
        }
        return commands;
    }

    @Override
    public List<byte[]> getAllSnapshots() {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        List<byte[]> snapshots = new ArrayList<>();
        if (databaseObj.has("games")) {
            JsonArray gamesArray = databaseObj.getAsJsonArray("games");
            for (int i = 0; i < gamesArray.size(); i++) {
                JsonObject gameObj = gamesArray.get(i).getAsJsonObject();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (gameObj.has("commands")) {
                    JsonArray commandsCopy = gameObj.getAsJsonArray("commands");
                    gameObj.remove("commands");
                    String snapshot = gson.toJson(gameObj);
                    snapshots.add(snapshot.getBytes());
                    gameObj.add("commands", commandsCopy);
                }
                else {
                    String gameStr = gson.toJson(gameObj);
                    snapshots.add(gameStr.getBytes());
                }
            }
        }
        return snapshots;
    }
}
