package com.groupryan.server.models;

import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
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

    private Map<String, Game> gameMap;
    private Map<String, User> userMap;
    private Map<Integer, TrainCard> trainCardMap = new HashMap<>();
    private Map<Integer, DestCard> destinationCardMap = new HashMap<>();
    private Map<String, ServerGame> serverGameIdMap = new HashMap<>();
    private List<Card> trainCards;
    private List<Card> destinationCards;
    private Map<String, ServerGame> userGames = new HashMap<>();
    private Map<Integer, Route> routeMap = new HashMap<>();
    private Map<Integer, Integer> routeLengthPoints = new HashMap<>();


    //usermap
    //server gameid map
    //usergames
    //call make bank (add it to constructor)
    private static final long serialVersionUID = 5230549922091722630L;

    private static RootServerModel single_instance; /*= new RootServerModel();*/

    public static RootServerModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootServerModel();
            Game game = new Game();
            //single_instance.gameMap = game.makeTestGames();
        }
        return single_instance;
    }

    public ArrayList<Game> getGames() {
        ArrayList<Game> notStartedGames = new ArrayList<>();
        for (Game g : gameMap.values()) {
            if (!g.isStarted()) {
                notStartedGames.add(g);
            }
        }
        return notStartedGames;
    }

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) userMap.values();
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
        gameMap = new HashMap<>();
        userMap = new HashMap<>();
    }

    private String _addUser(User user) {
        userMap.put(user.getUsername(), user);
        return utils.VALID;
    }

    public static User getUser(String userId) {
        return single_instance._getUser(userId);
    }

    private User _getUser(String userId) {
        if (userMap.containsKey((userId))) {
            return userMap.get(userId);
        }
        return null;
    }

    private String _addGame(Game game) {
        if (game.getGameId() == null) {
            game.makeGameId();
        }
        gameMap.put(game.getGameId(), game);
        Set<String> keys = game.getUsers().keySet();
        for (String s : keys) {
            getUser(s).addGame(game);
            break;
        }
//        Set<String> keys = game.getUsers().keySet();
//        for (String username : keys)
        return utils.VALID;
    }

    public Game getGame(String gameId) {
        if (gameMap.containsKey(gameId)) {
            return gameMap.get(gameId);
        }
        return null;
    }

    public String _createGame(Game game) {
        for (Game g : gameMap.values()) {
            if (g.getGameName().equals(game.getGameName())) {
                return utils.GAME_NAME_IN_USE;
            }
        }
        return _addGame(game);
    }

    private String _confirmUser(User user) {
        if (userMap.containsKey(user.getUsername())) {
            User u = userMap.get(user.getUsername());
            if (u.getPassword().equals(user.getPassword())) {
                return utils.VALID;
            }
            return utils.INVALID_PW;
        }
        return utils.INVALID_UN;
    }

    private Boolean _checkUser(User user) {
        return userMap.containsKey(user.getUsername());
    }

    private String _addUserToGame(Game game, User user, String userColor) {
        if (gameMap.containsKey(game.getGameId())) {
            Game g = gameMap.get(game.getGameId());
            if (g.getMaxPlayers() == g.getUsers().size()) {
                return utils.FULL_GAME;
            }
            return g.addUser(user, userColor);
        }
        return utils.INVALID_GAMEID;
    }

    private void makeBank() {
        destinationCards = new ArrayList<>();
        trainCards = new ArrayList<>();

        DestCard d = new DestCard(11, "WINNIPEG", "LITTLE ROCK", 1);
        destinationCardMap.put(1, d);
        destinationCards.add(d);
        d = new DestCard(7, "CALGARY", "SALT LAKE CITY", 2);
        destinationCardMap.put(2, d);
        destinationCards.add(d);
        d = new DestCard(10, "TORONTO", "MIAMI", 3);
        destinationCardMap.put(3, d);
        destinationCards.add(d);
        d = new DestCard(11, "DALLAS", "NEW YORK", 4);
        destinationCardMap.put(4, d);
        destinationCards.add(d);
        d = new DestCard(12, "BOSTON", "MIAMI", 5);
        destinationCardMap.put(5, d);
        destinationCards.add(d);
        d = new DestCard(21, "LOS ANGELES", "NEW YORK", 6);
        destinationCardMap.put(6, d);
        destinationCards.add(d);
        d = new DestCard(8, "HELENA", "LOS ANGELES", 7);
        destinationCardMap.put(7, d);
        destinationCards.add(d);
        d = new DestCard(13, "MONTREAL", "NEW ORLEANS", 8);
        destinationCardMap.put(8, d);
        destinationCards.add(d);
        d = new DestCard(10, "DULUTH", "EL PASO", 9);
        destinationCardMap.put(9, d);
        destinationCards.add(d);
        d = new DestCard(9, "SAULT STE. MARIE", "OKLAHOMA CITY", 10);
        destinationCardMap.put(10, d);
        destinationCards.add(d);
        d = new DestCard(17, "SAN FRANCISCO", "ATLANTA", 11);
        destinationCardMap.put(11, d);
        destinationCards.add(d);
        d = new DestCard(22, "SEATTLE", "NEW YORK", 12);
        destinationCardMap.put(12, d);
        destinationCards.add(d);
        d = new DestCard(12, "WINNIPEG", "HOUSTON", 13);
        destinationCardMap.put(13, d);
        destinationCards.add(d);
        d = new DestCard(20, "LOS ANGELES", "MIAMI", 14);
        destinationCardMap.put(14, d);
        destinationCards.add(d);
        d = new DestCard(9, "CHICAGO", "SANTA FE", 15);
        destinationCardMap.put(15, d);
        destinationCards.add(d);
        d = new DestCard(8, "DULUTH", "HOUSTON", 16);
        destinationCardMap.put(16, d);
        destinationCards.add(d);
        d = new DestCard(6, "NEW YORK", "ATLANTA", 17);
        destinationCardMap.put(17, d);
        destinationCards.add(d);
        d = new DestCard(11, "PORTLAND", "PHOENIX", 18);
        destinationCardMap.put(18, d);
        destinationCards.add(d);
        d = new DestCard(20, "VANCOUVER", "MONTREAL", 19);
        destinationCardMap.put(19, d);
        destinationCards.add(d);
        d = new DestCard(7, "CHICAGO", "NEW ORLEANS", 20);
        destinationCardMap.put(20, d);
        destinationCards.add(d);
        d = new DestCard(5, "KANSAS CITY", "HOUSTON", 21);
        destinationCardMap.put(21, d);
        destinationCards.add(d);
        d = new DestCard(13, "CALGARY", "PHOENIX", 22);
        destinationCardMap.put(22, d);
        destinationCards.add(d);
        d = new DestCard(16, "LOS ANGELES", "CHICAGO", 23);
        destinationCardMap.put(23, d);
        destinationCards.add(d);
        d = new DestCard(13, "VANCOUVER", "SANTA FE", 24);
        destinationCardMap.put(24, d);
        destinationCards.add(d);
        d = new DestCard(9, "SEATTLE", "LOS ANGELES", 25);
        destinationCardMap.put(25, d);
        destinationCards.add(d);
        d = new DestCard(9, "MONTREAL", "ATLANTA", 26);
        destinationCardMap.put(26, d);
        destinationCards.add(d);
        d = new DestCard(4, "DENVER", "EL PASO", 27);
        destinationCardMap.put(27, d);
        destinationCards.add(d);
        d = new DestCard(8, "SAULT STE. MARIE", "NASHVILLE", 28);
        destinationCardMap.put(28, d);
        destinationCards.add(d);
        d = new DestCard(11, "DENVER", "PITTSBURGH", 29);
        destinationCardMap.put(29, d);
        destinationCards.add(d);
        d = new DestCard(17, "PORTLAND", "NASHVILLE", 30);
        destinationCardMap.put(30, d);
        destinationCards.add(d);

        int id = 1;
        TrainCard t;
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.PINK, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.WHITE, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLUE, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.YELLOW, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.ORANGE, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.BLACK, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.RED, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 12; i++) {
            t = new TrainCard(utils.GREEN, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }
        for (int i = 0; i < 14; i++) {
            t = new TrainCard(utils.LOCOMOTIVE, id);
            trainCards.add(t);
            trainCardMap.put(id, t);
            id++;
        }

        routeMap.put(1, new Route(1, "VANCOUVER", "SEATTLE", 1, "", 1));
        routeMap.put(2, new Route(1, "VANCOUVER", "SEATTLE", 1, "", 2));
        routeMap.put(3, new Route(1, "SEATTLE", "PORTLAND", 1, "", 3));
        routeMap.put(4, new Route(1, "SEATTLE", "PORTLAND", 1, "", 4));
        routeMap.put(5, new Route(5, "PORTLAND", "SAN FRANCISCO", 10, utils.GREEN, 5));
        routeMap.put(6, new Route(5, "PORTLAND", "SAN FRANCISCO", 10, utils.PINK, 6));
        routeMap.put(7, new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.YELLOW, 7));
        routeMap.put(8, new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.PINK, 8));
        routeMap.put(9, new Route(3, "VANCOUVER", "CALGARY", 4, "", 9));
        routeMap.put(10, new Route(4, "SEATTLE", "CALGARY", 7, "", 10));
        routeMap.put(11, new Route(6, "SEATTLE", "HELENA", 15, utils.YELLOW, 11));
        routeMap.put(12, new Route(6, "PORTLAND", "SALT LAKE CITY", 15, utils.BLUE, 12));
        routeMap.put(13, new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, utils.ORANGE, 13));
        routeMap.put(14, new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, utils.WHITE, 14));
        routeMap.put(15, new Route(2, "LOS ANGELES", "LAS VEGAS", 2, "", 15));
        routeMap.put(16, new Route(3, "LOS ANGELES", "PHOENIX", 4, "", 16));
        routeMap.put(17, new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        routeMap.put(18, new Route(6, "CALGARY", "WINNIPEG", 15, utils.WHITE, 18));
        routeMap.put(19, new Route(4, "CALGARY", "HELENA", 7, "", 19));
        routeMap.put(20, new Route(3, "SALT LAKE CITY", "HELENA", 4, utils.PINK, 20));
        routeMap.put(21, new Route(3, "SALT LAKE CITY", "DENVER", 4, utils.RED, 21));
        routeMap.put(22, new Route(3, "SALT LAKE CITY", "DENVER", 4, utils.YELLOW, 22));
        routeMap.put(23, new Route(3, "LAS VEGAS", "SALT LAKE CITY", 4, utils.ORANGE, 23));
        routeMap.put(24, new Route(5, "PHOENIX", "DENVER", 10, utils.WHITE, 24));
        routeMap.put(25, new Route(3, "PHOENIX", "SANTA FE", 4, "", 25));
        routeMap.put(26, new Route(3, "PHOENIX", "EL PASO", 4, "", 26));
        routeMap.put(27, new Route(4, "HELENA", "WINNIPEG", 7, utils.BLUE, 27));
        routeMap.put(28, new Route(6, "HELENA", "DULUTH", 15, utils.ORANGE, 28));
        routeMap.put(29, new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        routeMap.put(30, new Route(4, "HELENA", "DENVER", 7, utils.GREEN, 30));
        routeMap.put(31, new Route(2, "DENVER", "SANTA FE", 2, "", 31));
        routeMap.put(32, new Route(4, "DENVER", "OMAHA", 7, utils.PINK, 32));
        routeMap.put(33, new Route(4, "DENVER", "KANSAS CITY", 7, utils.BLACK, 33));
        routeMap.put(34, new Route(4, "DENVER", "KANSAS CITY", 7, utils.ORANGE, 34));
        routeMap.put(35, new Route(4, "DENVER", "OKLAHOMA CITY", 7, utils.RED, 345));
        routeMap.put(36, new Route(3, "SANTA FE", "OKLAHOMA CITY", 4, utils.BLUE, 36));
        routeMap.put(37, new Route(2, "SANTA FE", "EL PASO", 2, "", 37));
        routeMap.put(38, new Route(5, "EL PASO", "OKLAHOMA CITY", 10, utils.YELLOW, 38));
        routeMap.put(39, new Route(4, "EL PASO", "DALLAS", 7, utils.RED, 39));
        routeMap.put(40, new Route(6, "EL PASO", "HOUSTON", 15, utils.GREEN, 40));
        routeMap.put(41, new Route(4, "WINNIPEG", "DULUTH", 7, utils.BLACK, 41));
        routeMap.put(42, new Route(6, "WINNIPEG", "SAULT ST. MARIE", 15, "", 42));
        routeMap.put(43, new Route(3, "DULUTH", "SAULT ST. MARIE", 4, "", 43));
        routeMap.put(44, new Route(6, "DULUTH", "TORONTO", 15, utils.PINK, 44));
        routeMap.put(45, new Route(3, "DULUTH", "CHICAGO", 4, utils.RED, 45));
        routeMap.put(46, new Route(2, "DULUTH", "OMAHA", 2, "", 46));
        routeMap.put(47, new Route(2, "DULUTH", "OMAHA", 2, "", 47));
        routeMap.put(48, new Route(4, "OMAHA", "CHICAGO", 7, utils.BLUE, 48));
        routeMap.put(49, new Route(1, "OMAHA", "KANSAS CITY", 1, "", 49));
        routeMap.put(50, new Route(1, "OMAHA", "KANSAS CITY", 1, "", 50));
        routeMap.put(51, new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, utils.BLUE, 51));
        routeMap.put(52, new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, utils.PINK, 52));
        routeMap.put(53, new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 53));
        routeMap.put(54, new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 54));
        routeMap.put(55, new Route(2, "OKLAHOMA CITY", "LITTLE ROCK", 2, "", 55));
        routeMap.put(56, new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 56));
        routeMap.put(57, new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 57));
        routeMap.put(58, new Route(1, "DALLAS", "HOUSTON", 1, "", 58));
        routeMap.put(59, new Route(1, "DALLAS", "HOUSTON", 1, "", 59));
        routeMap.put(60, new Route(2, "DALLAS", "LITTLE ROCK", 2, "", 60));
        routeMap.put(61, new Route(2, "HOUSTON", "NEW ORLEANS", 2, "", 61));
        routeMap.put(62, new Route(3, "LITTLE ROCK", "NEW ORLEANS", 4, utils.GREEN, 62));
        routeMap.put(63, new Route(3, "LITTLE ROCK", "NASHVILLE", 4, utils.WHITE, 63));
        routeMap.put(64, new Route(2, "SAINT LOUIS", "LITTLE ROCK", 2, "", 64));
        routeMap.put(65, new Route(2, "SAINT LOUIS", "NASHVILLE", 2, "", 65));
        routeMap.put(66, new Route(5, "SAINT LOUIS", "PITTSBURGH", 10, "", 66));
        routeMap.put(67, new Route(2, "SAINT LOUIS", "CHICAGO", 2, utils.GREEN, 67));
        routeMap.put(68, new Route(2, "SAINT LOUIS", "CHICAGO", 2, utils.WHITE, 68));
        routeMap.put(69, new Route(3, "CHICAGO", "PITTSBURGH", 4, utils.ORANGE, 69));
        routeMap.put(70, new Route(3, "CHICAGO", "PITTSBURGH", 4, utils.BLACK, 70));
        routeMap.put(71, new Route(4, "CHICAGO", "TORONTO", 7, utils.WHITE, 71));
        routeMap.put(72, new Route(5, "SAULT ST. MARIE", "MONTREAL", 10, utils.BLACK, 72));
        routeMap.put(73, new Route(2, "SAULT ST. MARIE", "TORONTO", 2, "", 73));
        routeMap.put(74, new Route(3, "TORONTO", "MONTREAL", 4, "", 74));
        routeMap.put(75, new Route(2, "TORONTO", "PITTSBURGH", 2, "", 75));
        routeMap.put(76, new Route(2, "MONTREAL", "BOSTON", 2, "", 76));
        routeMap.put(77, new Route(2, "MONTREAL", "BOSTON", 2, "", 77));
        routeMap.put(78, new Route(3, "MONTREAL", "NEW YORK", 4, utils.BLUE, 78));
        routeMap.put(79, new Route(2, "BOSTON", "NEW YORK", 2, utils.YELLOW, 79));
        routeMap.put(80, new Route(2, "BOSTON", "NEW YORK", 2, utils.RED, 80));
        routeMap.put(81, new Route(2, "PITTSBURGH", "NEW YORK", 2, utils.WHITE, 81));
        routeMap.put(82, new Route(2, "PITTSBURGH", "NEW YORK", 2, utils.GREEN, 82));
        routeMap.put(83, new Route(2, "PITTSBURGH", "WASHINGTON", 2, "", 83));
        routeMap.put(84, new Route(2, "PITTSBURGH", "RALEIGH", 2, "", 84));
        routeMap.put(85, new Route(2, "NEW YORK", "WASHINGTON", 2, utils.ORANGE, 85));
        routeMap.put(86, new Route(2, "NEW YORK", "WASHINGTON", 2, utils.BLACK, 86));
        routeMap.put(87, new Route(4, "NASHVILLE", "PITTSBURGH", 7, utils.YELLOW, 87));
        routeMap.put(88, new Route(3, "NASHVILLE", "RALEIGH", 4, utils.BLACK, 88));
        routeMap.put(89, new Route(1, "NASHVILLE", "ATLANTA", 1, "", 89));
        routeMap.put(90, new Route(4, "NEW ORLEANS", "ATLANTA", 7, utils.YELLOW, 90));
        routeMap.put(91, new Route(4, "NEW ORLEANS", "ATLANTA", 7, utils.ORANGE, 91));
        routeMap.put(92, new Route(6, "NEW ORLEANS", "MIAMI", 15, utils.RED, 92));
        routeMap.put(93, new Route(5, "ATLANTA", "MIAMI", 10, utils.BLUE, 93));
        routeMap.put(94, new Route(2, "ATLANTA", "RALEIGH", 2, "", 94));
        routeMap.put(95, new Route(2, "ATLANTA", "RALEIGH", 2, "", 95));
        routeMap.put(96, new Route(2, "ATLANTA", "CHARLESTON", 2, "", 96));
        routeMap.put(97, new Route(4, "CHARLESTON", "MIAMI", 7, utils.PINK, 97));
        routeMap.put(98, new Route(2, "RALEIGH", "CHARLESTON", 2, "", 98));
        routeMap.put(99, new Route(2, "RALEIGH", "WASHINGTON", 2, "", 99));
        routeMap.put(100, new Route(2, "RALEIGH", "WASHINGTON", 2, "", 100));
    }

    public Card getCard(String type, int cardID) {
        if (type.equals(utils.DESTINATION)) {
            return destinationCardMap.get(cardID);
        } else {
            return trainCardMap.get(cardID);
        }
    }

    public void createServerGame(String gameId) {
        makeBank();
        ServerGame sg = new ServerGame(gameId, new Deck(trainCards), new Deck(destinationCards));
        serverGameIdMap.put(gameId, sg);
    }

    public ServerGame getServerGame(String username) {
        return userGames.get(username);
    }

    public void addPlayertoGame(String username, String gameID) {
        userGames.put(username, serverGameIdMap.get(gameID));
    }

    public Player createPlayer(String gameId, Map.Entry<String, String> entry, int turn) {
        ServerGame sg = serverGameIdMap.get(gameId);
        List<TrainCard> tCards = new ArrayList<>();
        tCards.add((TrainCard) sg.drawTrainCard());
        tCards.add((TrainCard) sg.drawTrainCard());
        tCards.add((TrainCard) sg.drawTrainCard());
        tCards.add((TrainCard) sg.drawTrainCard());
        Player p = new Player(entry.getValue(), sg.drawDestinationCards(), tCards, entry.getKey(), turn, false);
        p.setAvailableRoutes(routeMap);
        //get the top 4 train cards
        //get the top 3 D cards,
        //store the player ,
        sg.addPlayer(p);
        return p;
    }

    private String _startGame(String gameId) {
        if (gameMap.containsKey(gameId)&&!gameMap.get(gameId).isStarted()) {
            gameMap.get(gameId).setStarted(true);
            return utils.VALID;
        }
        return utils.INVALID_GAMEID;
    }

    public ServerGame getServerGameByGameId(String gameId) {
        return serverGameIdMap.get(gameId);
    }

    public Map<String, ServerGame> getServerGameIdMap() {
        return serverGameIdMap;
    }

    public Route getRoute(int ID){
        return routeMap.get(ID);
    }
}