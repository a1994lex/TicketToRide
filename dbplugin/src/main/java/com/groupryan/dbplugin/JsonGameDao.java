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

    private int maxCommands;

    public JsonArray getGamesObj() {
        return gamesObj;
    }

    public void setGamesObj(JsonArray gamesObj) {
        this.gamesObj = gamesObj;
    }

    public JsonGameDao(JsonArray gamesObj, int maxCommands) {
        this.gamesObj = gamesObj;
        this.maxCommands = maxCommands;
    }

    private void addGamesElement() {
//        List<Integer> games = new ArrayList<>();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String gamesJson = gson.toJson(games);
//        JsonElement gamesElem = new JsonParser().parse(gamesJson);
//        gamesObj.add("games", gamesElem);
    }

    private void addCommandsElement() {
//        List<Integer> commands = new ArrayList<>();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String commandsJson = gson.toJson(commands);
//        JsonElement commandsElem = new JsonParser().parse(commandsJson);
//        JsonObject commandsObj = commandsElem.getAsJsonObject();
//
//        for (int i = 0; i < gamesObj.getAsJsonArray().size(); i++) {
//            JsonElement gameElem = gamesObj.getAsJsonArray().get(i);
//            JsonObject gameObj = gameElem.getAsJsonObject();
//            if (!gameObj.has("commands")) {
//                gameObj.add("commands", commandsObj);
//                gamesObj.getAsJsonArray().set(i, gameObj);
//            }
//        }
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
        if (gamesObj != null) {
            JsonObject gameObj = findGameById(gameid, gamesObj.getAsJsonArray());
            if (gameObj != null) {
                String commandStr = new String(command);
                JsonElement commandElem = new JsonParser().parse(commandStr);
                JsonArray commandsArray = new JsonArray();
                if (gameObj.has("commands")) {
                    commandsArray = gameObj.get("commands").getAsJsonArray();
                }
                commandsArray.add(commandElem);
                if (commandsArray.size() == maxCommands) {
                    maxReached = true;
                }
                gameObj.add("commands", commandsArray);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String gameStr = gson.toJson(gameObj);
                JsonElement gameElem = new JsonParser().parse(gameStr);
                int gameIndex = getGameIndex(gameid, gamesObj);
                gamesObj.set(gameIndex, gameElem);
            }
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
                gamesObj = gamesArray;
            }
        }
    }

    @Override
    public void clearCommands(String gameid) {
        if (gamesObj.size() > 0) {
            JsonObject gameObj = findGameById(gameid, gamesObj);
            if (gameObj != null) {
                gameObj.remove("commands");
                int gameIndex = getGameIndex(gameid, gamesObj);
                gamesObj.set(gameIndex, gameObj);
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
    public byte[] getSnapshotByGameId(String gameid) {
        byte[] snapshot = null;
        JsonArray gamesArray = gamesObj.getAsJsonArray();
        JsonObject gameObj = findGameById(gameid, gamesArray);
        if (gameObj != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (gameObj.has("commands")) {
                JsonArray commandsCopy = gameObj.getAsJsonArray("commands");
                gameObj.remove("commands");
                snapshot = gson.toJson(gameObj).getBytes();
                gameObj.add("commands", commandsCopy);
            }
            else {
                snapshot = gson.toJson(gameObj).getBytes();
            }
        }
        return snapshot;
    }

    @Override
    public Map<String, List<byte[]>> getAllCommands() {
        Map<String, List<byte[]>> allCommands = new HashMap<>();
        JsonArray gamesArray = gamesObj;
        for (int i = 0; i < gamesArray.size(); i++) {
            List<byte[]> commands = new ArrayList<>();
            JsonObject gameObj = gamesArray.get(i).getAsJsonObject();
            if (gameObj.has("commands")) {
                JsonArray commandsArray = gameObj.getAsJsonArray("commands");
                for (int j = 0; j < commandsArray.size(); j++) {
                    JsonElement command = commandsArray.get(j);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String commandStr = gson.toJson(command);
                    commands.add(commandStr.getBytes());
                }
            }
            String gameId = gameObj.get("gameId").getAsString();
            if (commands.size() > 0) {
                allCommands.put(gameId, commands);
            }
        }
        return allCommands;
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
