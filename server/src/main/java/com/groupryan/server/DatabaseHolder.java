package com.groupryan.server;

/**
 * Created by clairescout on 4/10/18.
 */

import com.groupryan.dbplugin.IDatabase;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.JavaSerializer;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.commands.ServerCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseHolder {

    private static DatabaseHolder instance = new DatabaseHolder();
    private IDatabase database;

    private DatabaseHolder(){

    }

    public static DatabaseHolder getInstance(){
        return instance;
    }


    public void loadActiveServerGames(){
        database.startTransaction();
        List<byte[]> gameBlobs = database.getGameDao().getAllSnapshots();
        List<User> users = database.getUserDao().getUsersList();
        database.endTransaction();

        //update root server model
        RootServerModel.getInstance().setServerGameIdMap(this.deserializeGames(gameBlobs));
        RootServerModel.getInstance().setUserMap(this.makeUserMap(users));
        RootServerModel.getInstance().setUserGames(this.makeUserGamesMap(users));
    }

    public void setDatabase(IDatabase database){
        this.database = database;
    }

    public IDatabase getDatabase(){
        return database;
    }

    public void addCommand(String gameId, ServerCommand serverCommand){
        byte[] serializedCommand = JavaSerializer.getInstance().serializeObject(serverCommand);

        if(database.getGameDao().addCommandToGame(gameId, serializedCommand, -1)){
            ServerGame serverGame = RootServerModel.getInstance().getServerGameByGameId(gameId);
            byte[] serializedGame = JavaSerializer.getInstance().serializeObject(serverGame);
            database.getGameDao().updateGameSnapshot(gameId,serializedGame );

            //TODO: check if commands are cleared when snapshot is updated?
        }
    }

    private Map<String, ServerGame> deserializeGames(List<byte[]> gameBlobs){
        HashMap<String, ServerGame> idToGame = new HashMap<>();
        for(byte[] bytes : gameBlobs){
            ServerGame serverGame = (ServerGame) JavaSerializer.getInstance().toObject(bytes);
            idToGame.put(serverGame.getServerGameID(), serverGame);
        }
        return idToGame;
    }

    private Map<String, User> makeUserMap(List<User> users){
        Map<String, User> userMap = new HashMap<>();
        for(User u : users){
            userMap.put(u.getUsername(), u);
        }
        return userMap;
    }

    private Map<String, ServerGame> makeUserGamesMap(List<User> users){
        Map<String, ServerGame> userGames = new HashMap<>();
        for(User u : users){
            List<Game> gameList = u.getGameList();
            String gameid = gameList.get(0).getGameId();
            ServerGame sg = RootServerModel.getInstance().getServerGameByGameId(gameid);
            userGames.put(u.getUsername(), sg);
        }
        return userGames;
    }

    public void addGameToUser(String gameid, User user){
        database.getUserDao().addGameToUser(user, gameid);
    }


}
