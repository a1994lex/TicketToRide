package com.groupryan.server.models;

import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Playa;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.User;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bengu3 on 1/31/18.
 */

public class RootServerModel {

    private ArrayList<Game> games;
    private ArrayList<User> users;
    private Map<Integer, TrainCard> trainCardMap=new HashMap<>();
    private Map<Integer, DestCard> destinationCardMap=new HashMap<>();
    private Map<String, ServerGame> serverGameIdMap=new HashMap<>();
    private List<Card> trainCards;
    private List<Card> destinationCards;
    private Map<String, ServerGame> userGames=new HashMap<>();
    private Map<Integer, Route> routeMap=new HashMap<>();
    private Map<Integer, Integer> routeLengthPoints=new HashMap<>();

    private static RootServerModel single_instance; /*= new RootServerModel();*/

    public static RootServerModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootServerModel();
            Game game = new Game();
            single_instance.games = (ArrayList) game.makeTestGames();
            single_instance.makeBank();
        }
        return single_instance;
    }

    public ArrayList<Game> getGames() {
        ArrayList<Game> notStartedGames=new ArrayList<>();
        for (Game g:games) {
            if(!g.isStarted()){
                notStartedGames.add(g);
            }
        }
        return notStartedGames;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public static String addUser(User user) {
        return single_instance._addUser(user);
    }

    public static String addGame(Game game) {
        return single_instance._addGame(game);
    }

    public static String confirmUser(User user) {
        return single_instance._confirmUser(user);
    }


    public static Boolean checkUser(User user) {
        return single_instance._checkUser(user);
    }

    public static String addUserToGame(Game game, User user, String userColor) {
        return single_instance._addUserToGame(game, user, userColor);
    }

    public static String startGame(String gameId) {
        return single_instance._startGame(gameId);
    }


    private RootServerModel() {
        games = new ArrayList();
        users = new ArrayList();
    }

    private String _addUser(User user) {
        users.add(user);
        return utils.VALID;
    }

    public static User getUser(String userId) {
        return single_instance._getUser(userId);
    }

    private User _getUser(String userId) {
        for (User u : users) {
            if(u.getUsername().equals(userId)){
                return u;
            }
        }
        return null;
    }

    private String _addGame(Game game) {
        if(game.getGameId()==null){
            game.makeGameId();
        }
        games.add(game);
        Set<String> keys = game.getUsers().keySet();
        for (String s:keys) {
            getUser(s).addGame(game);
            break;
        }
//        Set<String> keys = game.getUsers().keySet();
//        for (String username : keys)
        return utils.VALID;
    }

    public Game getGame(String gameId) {
        for (Game g : games) {
            if (g.getGameId().equals(gameId)) {
                return g;
            }
        }
        return null;
    }
    public String _createGame(Game game){
        for (Game g:games) {
            if(g.getGameName().equals(game.getGameName())){
                return utils.GAME_NAME_IN_USE;
            }
        }
        return _addGame(game);
    }

    private String _confirmUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                if (u.getPassword().equals(user.getPassword())) {
                    return utils.VALID;
                }
                return utils.INVALID_PW;
            }
        }
        return utils.INVALID_UN;
    }

    private Boolean _checkUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private String _addUserToGame(Game game, User user, String userColor) {
        for (Game g : this.games) {
            if (g.equals(game)) {
                if(g.getMaxPlayers()==g.getUsers().size()){
                    return utils.FULL_GAME;
                }
               return g.addUser(user, userColor);
            }
        }
        return utils.INVALID_GAMEID;
    }

    private  void makeBank(){
        destinationCards=new ArrayList<>();
        trainCards=new ArrayList<>();

        DestCard d=new DestCard(11, "WINNIPEG", "LITTLE ROCK", 1);
        destinationCardMap.put(1, d);
        destinationCards.add(d);
        d=new DestCard(7, "CALGARY", "SALT LAKE CITY", 2);
        destinationCardMap.put(2, d);
        destinationCards.add(d);
        d=new DestCard(10, "TORONTO", "MIAMI", 3);
        destinationCardMap.put(3, d);
        destinationCards.add(d);
        d=new DestCard(11, "DALLAS", "NEW YORK", 4);
        destinationCardMap.put(4, d);
        destinationCards.add(d);
        d=new DestCard(12, "BOSTON", "MIAMI", 5);
        destinationCardMap.put(5, d);
        destinationCards.add(d);
        d=new DestCard(21, "LOS ANGELES", "NEW YORK", 6);
        destinationCardMap.put(6, d);
        destinationCards.add(d);
        d=new DestCard(8, "HELENA", "LOS ANGELES", 7);
        destinationCardMap.put(7, d);
        destinationCards.add(d);
        d=new DestCard(13, "MONTREAL", "NEW ORLEANS", 8);
        destinationCardMap.put(8, d);
        destinationCards.add(d);
        d=new DestCard(10, "DULUTH", "EL PASO", 9);
        destinationCardMap.put(9, d);
        destinationCards.add(d);
        d=new DestCard(9, "SAULT STE. MARIE", "OKLAHOMA CITY", 10);
        destinationCardMap.put(10, d);
        destinationCards.add(d);
        d=new DestCard(17, "SAN FRANCISCO", "ATLANTA", 11);
        destinationCardMap.put(11, d);
        destinationCards.add(d);
        d=new DestCard(22, "SEATTLE", "NEW YORK", 12);
        destinationCardMap.put(12, d);
        destinationCards.add(d);
        d=new DestCard(12, "WINNIPEG", "HOUSTON", 13);
        destinationCardMap.put(13, d);
        destinationCards.add(d);
        d=new DestCard(20, "LOS ANGELES", "MIAMI", 14);
        destinationCardMap.put(14, d);
        destinationCards.add(d);
        d=new DestCard(9, "CHICAGO", "SANTA FE", 15);
        destinationCardMap.put(15, d);
        destinationCards.add(d);
        d=new DestCard(8, "DULUTH", "HOUSTON", 16);
        destinationCardMap.put(16, d);
        destinationCards.add(d);
        d=new DestCard(6, "NEW YORK", "ATLANTA", 17);
        destinationCardMap.put(17, d);
        destinationCards.add(d);
        d=new DestCard(11, "PORTLAND", "PHOENIX", 18);
        destinationCardMap.put(18, d);
        destinationCards.add(d);
        d=new DestCard(20, "VANCOUVER", "MONTREAL", 19);
        destinationCardMap.put(19, d);
        destinationCards.add(d);
        d=new DestCard(7, "CHICAGO", "NEW ORLEANS", 20);
        destinationCardMap.put(20, d);
        destinationCards.add(d);
        d=new DestCard(5, "KANSAS CITY", "HOUSTON", 21);
        destinationCardMap.put(21, d);
        destinationCards.add(d);
        d=new DestCard(13, "CALGARY", "PHOENIX", 22);
        destinationCardMap.put(22, d);
        destinationCards.add(d);
        d=new DestCard(16, "LOS ANGELES", "CHICAGO", 23);
        destinationCardMap.put(23, d);
        destinationCards.add(d);
        d=new DestCard(13, "VANCOUVER", "SANTA FE", 24);
        destinationCardMap.put(24, d);
        destinationCards.add(d);
        d=new DestCard(9, "SEATTLE", "LOS ANGELES", 25);
        destinationCardMap.put(25, d);
        destinationCards.add(d);
        d=new DestCard(9, "MONTREAL", "ATLANTA", 26);
        destinationCardMap.put(26, d);
        destinationCards.add(d);
        d=new DestCard(4, "DENVER", "EL PASO", 27);
        destinationCardMap.put(27, d);
        destinationCards.add(d);
        d=new DestCard(8, "SAULT STE. MARIE", "NASHVILLE", 28);
        destinationCardMap.put(28, d);
        destinationCards.add(d);
        d=new DestCard(11, "DENVER", "PITTSBURGH", 29);
        destinationCardMap.put(29, d);
        destinationCards.add(d);
        d=new DestCard(17, "PORTLAND", "NASHVILLE", 30);
        destinationCardMap.put(30, d);
        destinationCards.add(d);

        int id=1;
        TrainCard t;
        for(int i=0; i<12; i++) {
            t=new TrainCard(utils.PINK, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.WHITE, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.BLUE, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.YELLOW, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.ORANGE, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.BLACK, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.RED, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<12; i++) {
            t=new TrainCard(utils.GREEN, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        } for(int i=0; i<14; i++) {
            t=new TrainCard(utils.LOCOMOTIVE, id);
            trainCards.add(t);
            trainCardMap.put(id,t);
            id++;
        }
    }

    public Card getCard(String type, int cardID){
        if(type.equals(utils.DESTINATION)){
            return destinationCardMap.get(cardID);
        }
        else{
            return trainCardMap.get(cardID);
        }
    }

    public void createServerGame(String gameId){
        ServerGame sg=new ServerGame(gameId, new Deck(trainCards), new Deck(destinationCards));

        serverGameIdMap.put(gameId, sg);

    }

    public ServerGame getServerGame(String username){
        return userGames.get(username);
    }
    public void addPlayatoGame(String username, String gameID){
        userGames.put(username, serverGameIdMap.get(gameID));
    }

    public Playa createPlaya(String gameId, Map.Entry<String, String> entry){
        ServerGame sg=serverGameIdMap.get(gameId);
        List<Card> tCards=new ArrayList<>();
        tCards.add(sg.drawTrainCard());
        tCards.add(sg.drawTrainCard());
        tCards.add(sg.drawTrainCard());
        tCards.add(sg.drawTrainCard());
        Playa p=new Playa(entry.getValue(), sg.drawDestinationCards(),tCards ,entry.getKey());
        //get the top 4 train cards
        //get the top 3 D cards,
        //store the player ,
        sg.addPlaya(p);
        return p;
    }

    private String _startGame(String gameId) {
        for (Game g : games) {
            if (g.getGameId().equals(gameId)) {
                g.setStarted(true);
                return utils.VALID;
            }
        }
        return utils.INVALID_GAMEID;
    }

}