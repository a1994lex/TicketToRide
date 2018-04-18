package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonGameDao implements IGameDao {

    private JsonObject gamesObj;

    private JsonObject commandsObj;

    private int maxCommands;

    public JsonObject getGamesObj() {
        return gamesObj;
    }

    public void setGamesObj(JsonObject gamesObj) {
        this.gamesObj = gamesObj;
    }

    public JsonGameDao(JsonObject gamesObj, JsonObject commandsObj, int maxCommands) {
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

    private JsonElement findGameById(String gameId) {
//        int i = 0;
        JsonElement game = null;
        game = gamesObj.get(gameId);
//        while (i < gamesArray.size()) {
//            JsonElement game = gamesArray.get(i);
//            JsonObject gameObj = game.getAsJsonObject();
//            if (gameObj.get("gameId").getAsString().equals(gameId)) {
//                obj = gameObj;
//                i = gamesArray.size();
//            }
//            i++;
//        }
        return game;
    }

    private JsonArray findCommandListById(String gameId) {
        JsonArray commandList= null;
        commandList = commandsObj.getAsJsonArray(gameId);
        return commandList;
    }

//    private int getGameIndex(String gameId, JsonArray gamesArray) {
//        int i = 0;
//        while (i < gamesArray.size()) {
//            JsonElement game = gamesArray.get(i);
//            JsonObject gameObj = game.getAsJsonObject();
//            if (gameObj.get("gameId").getAsString().equals(gameId)) {
//                return i;
//            }
//            i++;
//        }
//        return -1;
//    }

    @Override
    public Boolean addCommandToGame(String gameid, byte[] command, int order) {
        boolean maxReached = false;
        JsonArray commandList = findCommandListById(gameid);
        //String commandStr = new String(command);
        String commandStr = Arrays.toString(command);
        if (commandList == null) {
            commandList = new JsonArray();
        }
        commandList.add(commandStr);
        commandsObj.add(gameid, commandList);
        if (commandList.size() == maxCommands) {
            maxReached = true;
        }
        return maxReached;
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        String snapshotStr = Arrays.toString(gameSnapshot);
        gamesObj.addProperty(gameid, snapshotStr);
//        if (gameObj == null) {
//            gamesArray.add(snapshotElem);
//        }
//        else {
//            int gameIndex = getGameIndex(gameid, gamesArray);
//            if (gameIndex != -1) {
//                gamesArray.set(gameIndex, snapshotElem);
//            }
//            else {
//                gamesArray.add(snapshotElem);
//            }
//            gamesObj = gamesArray;
//        }

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
        if (commands != null) {
            for (int i = 0; i < commands.size(); i++) {
                //commandsList.add(commands.get(i).getAsString().getBytes());
                commandsList.add(makeBytes(commands.get(i).getAsString()));

            }
        }
        return commandsList;
    }

    @Override
    public byte[] getSnapshotByGameId(String gameid) {
        byte[] snapshot = null;
        JsonElement gameElem = findGameById(gameid);
        if (gameElem != null) {
            snapshot = makeBytes(gameElem.getAsString());
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
                byte[] theCommand = makeBytes(commandsArray.get(i).getAsString());
                commandList.add(theCommand);
            }
            allCommands.put(entry.getKey(), commandList);
        }
        commandsObj = new JsonObject();
        return allCommands;
    }

    @Override
    public List<byte[]> getAllSnapshots() {
        List<byte[]> snapshots = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : gamesObj.entrySet()) {
            snapshots.add(makeBytes(entry.getValue().getAsString()));
        }
        //        JsonArray gamesArray = gamesObj.getAsJsonArray();
//        for (int i = 0; i < gamesArray.size(); i++) {
//            JsonObject gameObj = gamesArray.get(i).getAsJsonObject();
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String gameStr = gson.toJson(gameObj);
//            snapshots.add(gameStr.getBytes());
//        }
        return snapshots;
    }

    @Override
    public void dropTables() {
        gamesObj = new JsonObject();
        commandsObj = new JsonObject();
        System.out.println("games table is now empty: " + gamesObj);
        System.out.println("commands table is now empty: " + commandsObj);
    }

    public byte[] makeBytes(String s){
        String[] byteValues = s.substring(1, s.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, len=bytes.length; i<len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;

    }
}
