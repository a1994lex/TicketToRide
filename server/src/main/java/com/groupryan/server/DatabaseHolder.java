package com.groupryan.server;

/**
 * Created by clairescout on 4/10/18.
 */

import com.groupryan.dbplugin.IDatabase;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.JavaSerializer;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.User;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.utils;
import com.groupryan.shared.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseHolder {

    private static DatabaseHolder instance = new DatabaseHolder();
    private IDatabase database;
    private ServerGame serverGame;

    private DatabaseHolder(){

    }

    public static DatabaseHolder getInstance(){
        return instance;
    }


    public void loadActiveServerGames(){
//        makeGame();
//        byte[] stream = JavaSerializer.getInstance().serializeObject(serverGame);
//        DatabaseHolder.getInstance().getDatabase().startTransaction();
//        DatabaseHolder.getInstance().getDatabase().getGameDao().updateGameSnapshot(serverGame.getServerGameID(), stream);
//        byte[] afterstream = DatabaseHolder.getInstance().getDatabase().getGameDao().getSnapshotByGameId(serverGame.getServerGameID());
//        DatabaseHolder.getInstance().getDatabase().endTransaction();



        database.startTransaction();
        List<byte[]> gameBlobs = database.getGameDao().getAllSnapshots();
        List<User> users = database.getUserDao().getUsersList();
        Map<String, List<byte[]>> commandMap = database.getGameDao().getAllCommands();
        database.endTransaction();

        //update root server model
        RootServerModel.getInstance().setServerGameIdMap(this.deserializeGames(gameBlobs));
        RootServerModel.getInstance().setUserMap(this.makeUserMap(users));
        RootServerModel.getInstance().setUserGames(this.makeUserGamesMap(users));

        //run commands
        for(Map.Entry<String, List<byte[]>> entry : commandMap.entrySet()){
            for (byte[] serializedCommand : entry.getValue()){
                ServerCommand sc = (ServerCommand) JavaSerializer.getInstance().toObject(serializedCommand);
                sc.execute();
            }
        }

    }

    public void setDatabase(IDatabase database){
        this.database = database;


        database.startTransaction();
//        /*
//        User u=new User("q", "q");
//        User uu=new User("qq","qq");
//        database.getUserDao().registerUser(u);
//        database.getUserDao().registerUser(uu);
//        database.getUserDao().addGameToUser(u, "one");
//        database.getUserDao().addGameToUser(uu, "two");
//        List<User> users= database.getUserDao().getUsersList();*/
//       /*  String example = "Convert Java String";
//         byte[] bytes = example.getBytes();
//         database.getGameDao().updateGameSnapshot("one", bytes);
//         example = "Convert  ";
//         bytes = example.getBytes();
//         database.getGameDao().updateGameSnapshot("two", bytes);
//         Map<String,List<byte[]>> que= database.getGameDao().getAllCommands();
//         List<byte[]> gay=database.getGameDao().getAllSnapshots();*/
        database.getUserDao().dropTables();
         database.getGameDao().dropTables();
         database.endTransaction();
//        database.startTransaction();
//        User u=new User("q", "q");
//        User uu=new User("qq","qq");
//        database.getUserDao().registerUser(u);
//        database.getUserDao().registerUser(uu);
//        database.getUserDao().addGameToUser(u, "one");
//        database.getUserDao().addGameToUser(uu, "two");
//        List<User> users= database.getUserDao().getUsersList();
//        database.endTransaction();
//         String example = "Convert Java String";
//         byte[] bytes = example.getBytes();
         //database.getGameDao().updateGameSnapshot("one", bytes);
//         example = "Convert  ";
//         bytes = example.getBytes();
//         //database.getGameDao().updateGameSnapshot("two", bytes);
//         Map<String,List<byte[]>> que= database.getGameDao().getAllCommands();
//         List<byte[]> gay=database.getGameDao().getAllSnapshots();
//         database.getGameDao().dropTables();
//         database.getUserDao().dropTables();
//         database.endTransaction();

    }

    public IDatabase getDatabase(){
        return database;
    }

    public void addCommand(String gameId, ServerCommand serverCommand){
        byte[] serializedCommand = JavaSerializer.getInstance().serializeObject(serverCommand);
        database.startTransaction();
        boolean maxCommandsHit =  database.getGameDao().addCommandToGame(gameId, serializedCommand, -1);
        database.endTransaction();
        if(maxCommandsHit){
            ServerGame serverGame = RootServerModel.getInstance().getServerGameByGameId(gameId);
            byte[] serializedGame = JavaSerializer.getInstance().serializeObject(serverGame);
            database.startTransaction();
            database.getGameDao().updateGameSnapshot(gameId,serializedGame );
            database.endTransaction();
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
//            List<Game> gameList = u.getGameList();
//            for(Game game: gameList){
//                String gameid = game.getGameId();
//                ServerGame sg = RootServerModel.getInstance().getServerGameByGameId(gameid);
//                if(sg != null){ //TODO: CHECK IF THIS WORKS
//                    userGames.put(u.getUsername(), sg);
//                }
//            }

//            ServerGame sg = RootServerModel.getInstance().getServerGameByGameId(u.getGameId());
//            if(sg != null){
//                userGames.put(u.getUsername(), sg);
//            }
        }
        return userGames;
    }

    public void addGameToUser(String gameid, User user){
        database.startTransaction();
        database.getUserDao().addGameToUser(user, gameid);
        database.endTransaction();
    }

    private void makeGame(){
        ArrayList<Card> destinationCards = new ArrayList<>();
        ArrayList<Card> trainCards = new ArrayList<>();

        DestCard d = new DestCard(11, "WINNIPEG", "LITTLE ROCK", 1);

        destinationCards.add(d);
        d = new DestCard(7, "CALGARY", "SALT LAKE CITY", 2);

        destinationCards.add(d);
        d = new DestCard(10, "TORONTO", "MIAMI", 3);

        destinationCards.add(d);
        d = new DestCard(11, "DALLAS", "NEW YORK", 4);

        destinationCards.add(d);
        d = new DestCard(12, "BOSTON", "MIAMI", 5);

        destinationCards.add(d);
        d = new DestCard(21, "LOS ANGELES", "NEW YORK", 6);

        destinationCards.add(d);
        d = new DestCard(8, "HELENA", "LOS ANGELES", 7);

        destinationCards.add(d);
        d = new DestCard(13, "MONTREAL", "NEW ORLEANS", 8);

        destinationCards.add(d);
        d = new DestCard(10, "DULUTH", "EL PASO", 9);

        destinationCards.add(d);
        d = new DestCard(9, "SAULT STE. MARIE", "OKLAHOMA CITY", 10);

        destinationCards.add(d);
        d = new DestCard(17, "SAN FRANCISCO", "ATLANTA", 11);

        destinationCards.add(d);
        d = new DestCard(22, "SEATTLE", "NEW YORK", 12);

        destinationCards.add(d);
        d = new DestCard(12, "WINNIPEG", "HOUSTON", 13);

        destinationCards.add(d);
        d = new DestCard(20, "LOS ANGELES", "MIAMI", 14);

        destinationCards.add(d);
        d = new DestCard(9, "CHICAGO", "SANTA FE", 15);

        destinationCards.add(d);
        d = new DestCard(8, "DULUTH", "HOUSTON", 16);

        destinationCards.add(d);
        d = new DestCard(6, "NEW YORK", "ATLANTA", 17);

        destinationCards.add(d);
        d = new DestCard(11, "PORTLAND", "PHOENIX", 18);

        destinationCards.add(d);
        d = new DestCard(20, "VANCOUVER", "MONTREAL", 19);

        destinationCards.add(d);
        d = new DestCard(7, "CHICAGO", "NEW ORLEANS", 20);

        destinationCards.add(d);
        d = new DestCard(5, "KANSAS CITY", "HOUSTON", 21);

        destinationCards.add(d);
        d = new DestCard(13, "CALGARY", "PHOENIX", 22);

        destinationCards.add(d);
        d = new DestCard(16, "LOS ANGELES", "CHICAGO", 23);

        destinationCards.add(d);
        d = new DestCard(13, "VANCOUVER", "SANTA FE", 24);

        destinationCards.add(d);
        d = new DestCard(9, "SEATTLE", "LOS ANGELES", 25);

        destinationCards.add(d);
        d = new DestCard(9, "MONTREAL", "ATLANTA", 26);

        destinationCards.add(d);
        d = new DestCard(4, "DENVER", "EL PASO", 27);

        destinationCards.add(d);
        d = new DestCard(8, "SAULT STE. MARIE", "NASHVILLE", 28);

        destinationCards.add(d);
        d = new DestCard(11, "DENVER", "PITTSBURGH", 29);

        destinationCards.add(d);
        d = new DestCard(17, "PORTLAND", "NASHVILLE", 30);

        destinationCards.add(d);

        int id = 1;
        TrainCard t;
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.PINK, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.WHITE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLUE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.YELLOW, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.ORANGE, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLACK, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.RED, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.GREEN, id);
            trainCards.add(t);
            id++;
        }
        for (int i = 0; i < 14; i++) {
            t = new TrainCard(utils.LOCOMOTIVE, id);
            trainCards.add(t);
            id++;
        }
        serverGame = new ServerGame("gameid", new Deck(trainCards), new Deck(destinationCards));

        String hi = "first fake history";
        String hello = "second fake history";
        String indeed = "thirdFakeHistory";
        serverGame.addHistory(hi);
        serverGame.addHistory(hello);
        serverGame.addHistory(indeed);

        List<TrainCard> tCards = new ArrayList<>();
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        tCards.add((TrainCard) serverGame.drawTrainCard());
        Player p = new Player(utils.RED, serverGame.drawDestinationCards(), tCards, "claire", 0, false);
        p.addRoute(new Route(2, "PITTSBURGH", "NEW YORK", 2, utils.GREEN, 82));
        p.addPoints(10);
        List<TrainCard> tCards2 = new ArrayList<>();
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        tCards2.add((TrainCard) serverGame.drawTrainCard());
        Player p2 = new Player(utils.BLUE, serverGame.drawDestinationCards(), tCards2, "grace", 1, false);
        p2.addRoute(new Route(2, "PITTSBURGH", "WASHINGTON", 2, "", 83));
        p2.addPoints(25);
        serverGame.addPlayer(p);
        serverGame.addPlayer(p2);
        serverGame.getStat("claire");
        serverGame.getStat("grace");
    }

}
