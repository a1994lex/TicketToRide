package com.groupryan.server;

/**
 * Created by clairescout on 4/10/18.
 */

import com.groupryan.dbplugin.IDatabase;
import com.groupryan.shared.JavaSerializer;
import com.groupryan.shared.models.User;

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
        //TODO: load active server games
        database.startTransaction();
        List<byte[]> gameBlobs = database.getGameDao().getAllSnapshots();
        database.endTransaction();
        //WHAT DO I DO NOW.
    }

    public void setDatabase(IDatabase database){
        this.database = database;
        database.startTransaction();
        /*
        User u=new User("q", "q");
        User uu=new User("qq","qq");
        database.getUserDao().registerUser(u);
        database.getUserDao().registerUser(uu);
        database.getUserDao().addGameToUser(u, "one");
        database.getUserDao().addGameToUser(uu, "two");
        List<User> users= database.getUserDao().getUsersList();*/
       /*  String example = "Convert Java String";
         byte[] bytes = example.getBytes();
         database.getGameDao().updateGameSnapshot("one", bytes);
         example = "Convert  ";
         bytes = example.getBytes();
         database.getGameDao().updateGameSnapshot("two", bytes);
         Map<String,List<byte[]>> que= database.getGameDao().getAllCommands();
         List<byte[]> gay=database.getGameDao().getAllSnapshots();*/
         database.getGameDao().dropTables();
         database.endTransaction();

    }

    public IDatabase getDatabase(){
        return database;
    }

    public void addCommand(String gameId, ServerCommand serverCommand){
        byte[] blob = JavaSerializer.getInstance().serializeObject(serverCommand);
        database.getGameDao().addCommandToGame(gameId, blob, -1);
    }
}
