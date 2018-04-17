package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonGameDao implements IGameDao {

    private JsonArray gamesObj;

    private JsonObject commandsObj;

    private int maxCommands;

    public JsonArray getGamesObj() {
        return gamesObj;
    }

    public void setGamesObj(JsonArray gamesObj) {
        this.gamesObj = gamesObj;
    }

    public JsonGameDao(JsonArray gamesObj, JsonObject commandsObj, int maxCommands) {
        this.gamesObj = gamesObj;
        this.commandsObj = commandsObj;
        this.maxCommands = maxCommands;
    }

    public int getMaxCommands() {
        return maxCommands;
    }

    public void setMaxCommands(int maxCommands) {
        this.maxCommands = maxCommands;
    }

    public JsonObject getCommandsObj() {
        return commandsObj;
    }

    public void setCommandsObj(JsonObject commandsObj) {
        this.commandsObj = commandsObj;
    }

    private JsonObject findGameById(String gameId, JsonArray gamesArray) {
        int i = 0;
        JsonObject obj = null;
        while (i < gamesArray.size()) {
            JsonElement game = gamesArray.get(i);
            JsonObject gameObj = game.getAsJsonObject();
            if (gameObj.get("gameId").getAsString().equals(gameId)) {
                obj = gameObj;
                i = gamesArray.size();
            }
            i++;
        }
        return obj;
    }

    private JsonArray findCommandListById(String gameId) {
        JsonArray array = null;
        array = commandsObj.getAsJsonArray(gameId);
        return array;
    }

    private int getGameIndex(String gameId, JsonArray gamesArray) {
        int i = 0;
        while (i < gamesArray.size()) {
            JsonElement game = gamesArray.get(i);
            JsonObject gameObj = game.getAsJsonObject();
            if (gameObj.get("gameId").getAsString().equals(gameId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public Boolean addCommandToGame(String gameid, byte[] command, int order) {
        boolean maxReached = false;
        JsonArray commandsArray = findCommandListById(gameid);
        String commandStr = new String(command);
        JsonElement commandElem = new JsonParser().parse(commandStr);
        if (commandsArray == null) {
            commandsArray = new JsonArray();
        }
        commandsArray.add(commandElem);
        commandsObj.remove(gameid);
        commandsObj.add(gameid, commandsArray);
        if (commandsArray.size() == maxCommands) {
            maxReached = true;
        }

        return maxReached;
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        String snapshotStr = new String(gameSnapshot);
        JsonElement snapshotElem = new JsonParser().parse(snapshotStr);
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
            }
            gamesObj = gamesArray;
        }

        commandsObj.remove(gameid);
    }

    @Override
    public void clearCommands(String gameid) {
        commandsObj.remove(gameid);
    }

    @Override
    public List<byte[]> getCommandsByGameId(String gameid) {
        List<byte[]> commandsList = new ArrayList<>();
        JsonArray commands = commandsObj.getAsJsonArray(gameid);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (commands != null) {
            for (int i = 0; i < commands.size(); i++) {
                String command = gson.toJson(commands.get(i));
                commandsList.add(command.getBytes());
            }
        }
        return commandsList;
    }

    @Override
    public byte[] getSnapshotByGameId(String gameid) {
        byte[] snapshot = null;
        JsonArray gamesArray = gamesObj.getAsJsonArray();
        JsonObject gameObj = findGameById(gameid, gamesArray);
        if (gameObj != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            snapshot = gson.toJson(gameObj).getBytes();
        }
        return snapshot;
    }

    @Override
    public Map<String, List<byte[]>> getAllCommands() {
        Map<String, List<byte[]>> allCommands = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (Map.Entry<String, JsonElement> entry : commandsObj.entrySet()) {
            JsonArray commandsArray = entry.getValue().getAsJsonArray();
            List<byte[]> commandList = new ArrayList<>();
            for (int i = 0; i < commandsArray.size(); i++) {
                byte[] command = gson.toJson(commandsArray.get(i)).getBytes();
                commandList.add(command);
            }
            allCommands.put(entry.getKey(), commandList);
        }
        commandsObj = new JsonObject();
        return allCommands;
    }

    @Override
    public List<byte[]> getAllSnapshots() {
        List<byte[]> snapshots = new ArrayList<>();
        JsonArray gamesArray = gamesObj.getAsJsonArray();
        for (int i = 0; i < gamesArray.size(); i++) {
            JsonObject gameObj = gamesArray.get(i).getAsJsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String gameStr = gson.toJson(gameObj);
            snapshots.add(gameStr.getBytes());
        }
        return snapshots;
    }

    @Override
    public void dropTables() {
        gamesObj = new JsonArray();
        commandsObj = new JsonObject();
        System.out.println("games table is now empty: " + gamesObj);
        System.out.println("commands table is now empty: " + commandsObj);
    }
}
