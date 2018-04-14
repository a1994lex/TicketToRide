package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonGameDao implements IGameDao {

    private JsonObject gamesObj;

    public JsonObject getGamesObj() {
        return gamesObj;
    }

    public void setGamesObj(JsonObject gamesObj) {
        this.gamesObj = gamesObj;
    }

    public JsonGameDao(JsonObject gamesObj) {
        this.gamesObj = gamesObj;
    }

    private void addGamesElement() {
        List<Integer> games = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gamesJson = gson.toJson(games);
        JsonElement gamesElem = new JsonParser().parse(gamesJson);
        gamesObj.add("games", gamesElem);
    }

    private void addCommandsElement() {
        List<Integer> commands = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String commandsJson = gson.toJson(commands);
        JsonElement commandsElem = new JsonParser().parse(commandsJson);
        JsonObject commandsObj = commandsElem.getAsJsonObject();

        for (int i = 0; i < gamesObj.getAsJsonArray().size(); i++) {
            JsonElement gameElem = gamesObj.getAsJsonArray().get(i);
            JsonObject gameObj = gameElem.getAsJsonObject();
            if (!gameObj.has("commands")) {
                gameObj.add("commands", commandsObj);
                gamesObj.getAsJsonArray().set(i, gameObj);
            }
        }
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
        if (gamesObj != null) {
            addCommandsElement();
            JsonObject gameObj = findGameById(gameid, gamesObj.getAsJsonArray());
            if (gameObj != null) {
                String commandStr = new String(command);
                JsonElement commandElem = new JsonParser().parse(commandStr);
                JsonArray commandsArray = gameObj.get("commands").getAsJsonArray();
                commandsArray.add(commandElem);
                gameObj.add("commands", commandsArray);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String gameStr = gson.toJson(gameObj);
                JsonElement gameElem = new JsonParser().parse(gameStr);
                int gameIndex = getGameIndex(gameid, gamesObj.getAsJsonArray());
                gamesObj.getAsJsonArray().set(gameIndex, gameElem);
            }
        }
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        String snapshotStr = new String(gameSnapshot);
        JsonElement snapshotElem = new JsonParser().parse(snapshotStr);
        if (gamesObj == null) {
            addGamesElement();
        }
        JsonArray gamesArray = gamesObj.getAsJsonArray();
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
                gamesObj = gamesArray.getAsJsonObject();
            }
        }
    }

    @Override
    public void clearCommands(String gameid) {
        if (gamesObj.getAsJsonArray().size() > 0) {
            JsonObject gameObj = findGameById(gameid, gamesObj.getAsJsonArray());
            if (gameObj != null) {
                gameObj.remove("commands");
                int gameIndex = getGameIndex(gameid, gamesObj.getAsJsonArray());
                gamesObj.getAsJsonArray().set(gameIndex, gameObj);
            }
        }
    }

    @Override
    public List<byte[]> getCommandsByGameId(String gameid) {
        List<byte[]> commandsList = new ArrayList<>();
        JsonArray gamesArray = gamesObj.getAsJsonArray();
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
        return commandsList;
    }

    @Override
    public String getSnapshotByGameId(String gameid) {
        String snapshot = null;
        JsonArray gamesArray = gamesObj.getAsJsonArray();
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
        return snapshot;
    }

    @Override
    public List<byte[]> getAllCommands() {
        List<byte[]> commands = new ArrayList<>();
        JsonArray gamesArray = gamesObj.getAsJsonArray();
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
        return commands;
    }

    @Override
    public List<byte[]> getAllSnapshots() {
        List<byte[]> snapshots = new ArrayList<>();
        JsonArray gamesArray = gamesObj.getAsJsonArray();
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
        return snapshots;
    }
}
