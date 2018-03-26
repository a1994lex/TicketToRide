package com.groupryan.client.models;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.User;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class RootClientModel extends Observable {

    private ArrayList<Game> games;
    private User user;
    private HashMap<String, String> players; // <username, Color>
    private HashMap<Integer, HashSet<RouteSegment>> routeSegments = new HashMap<>();
    private ClientGame clientGame = null;
    private boolean showRoutes = true;
    private ArrayList<Route> routes;

    public Map<Integer, HashSet<RouteSegment>> getRouteSegments() {
        return routeSegments;
    }

    public HashSet<RouteSegment> getRouteSegmentSet(int routeId) {
        return routeSegments.get(routeId);
    }

    public void setRouteSegments(HashMap<Integer, HashSet<RouteSegment>> routeSegments) {
        this.routeSegments = routeSegments;
    }

    public static RootClientModel getInstance() {
        if (single_instance == null) {
            single_instance = new RootClientModel();
        }
        return single_instance;
    }

    public static RootClientModel getSingle_instance(){
        return single_instance;
    }

    public static ArrayList<Game> getGames() {
        return single_instance.games;
    }

    public static User getUser() {
        return single_instance.user;
    }

    public static ClientGame getCurrentGame() {
        return single_instance.clientGame;
    }

    public static Route getRoute(int routeId) {
        return single_instance._getRoute(routeId);
    }

    public static List<Route> getClaimedRoutes() {
        return single_instance._getClaimedRoutes();
    }

    public static ArrayList<Route> getRoutesList() {
        return single_instance._getRoutes();
    }

    private static RootClientModel single_instance = new RootClientModel();


    public static void addUser(User user) {
        single_instance._addUser(user);
    }

    public static void setShowRoutes() {
        single_instance._setShowRoutes();
    }

    public static void updateStats(Stat stat) {
        single_instance._updateStats(stat);
    }

    public static void addGame(Game game) {
        single_instance._addGame(game);
    }

    public static void startGame(Game game, Player p) {
        single_instance._startGame(game, p);
    }

    public static void setCurrentGame(Game game, Player p){ single_instance._setCurrentGame(game, p);}

    public static void addUserToGame(Game game, User user, String userColor) {
        single_instance._addUserToGame(game, user, userColor);
    }

    public static void addClaimedRoute(String username, Route route) {
        single_instance._addClaimedRoute(username, route);
    }

//    public static HashMap<String, Route> getClaimedRoutesMap() {
//        return single_instance._getRoutesMap();
//    }

    public static void setGames(List<Game> games){
        single_instance._setGames(games);
    }

    public Map<String, String> getPlayers() {
        return single_instance._getPlayers();
    }

    public void addPlayers(HashMap<String, String> players) {
        single_instance._setPlayers(players);
    }

    private RootClientModel() {
        games = new ArrayList();
        user = new User(null, null);
        initializeRoutesList();
        initializeRouteSegmentData();
    }

    private void _addUser(User user) {
        this.user = user;
        setChanged();
        notifyObservers();
    }

    private void _addGame(Game game) {
        games.add(game);
        setChanged();
        notifyObservers();
    }

    private List<Route> _getClaimedRoutes() {
        return clientGame.getClaimedRoutesList();
    }

//    private HashMap<String, Route> _getRoutesMap() {
//        return clientGame.getRoutesMap();
//    }

    private ArrayList<Route> _getRoutes() {
        return routes;
    }

    private Route _getRoute(int routeId) {
        for (Route r : routes) {
            if (r.getId() == routeId) {
                return r;
            }
        }
        return null;
    }

    private void _addClaimedRoute(String username, Route route) {
        clientGame.addClaimedRoute(username, route);
        setChanged();
        notifyObservers(route);
    }

    private void _startGame(Game game, Player p) {
        // Builds a client game along with the Player
        for (Game g : games) {
            if (g.equals(game)) {
                g.setStarted(true);
                games.remove(g);
                setChanged();
                notifyObservers(g.getGameId());
            }
        }
    }

    private void _setShowRoutes() {
        setChanged();
        notifyObservers(utils.REDRAW_ROUTES);
    }

    private void _addUserToGame(Game game, User user, String userColor) {
        for (Game g : this.games) {
            if (g.equals(game)) {
                g.addUser(user, userColor);
                this.user.addGame(g);
                setChanged();
                notifyObservers();
            }
        }
    }

    private void _setGames(List<Game> games) {
        this.games = (ArrayList<Game>)games;
    }

    private void _setCurrentGame(Game gm, Player p){
        clientGame = new ClientGame(gm, p);
        clientGame.setAvailableRoutes(routes);
    }

    private Map<String, String> _getPlayers() {
        return players;
    }

    private void _setPlayers(HashMap<String, String> players) {
        this.players = players;
    }

    private void _updateStats(Stat stat) {
        clientGame.updateStat(stat);
        setChanged();
        notifyObservers();
    }

    private void initializeRoutesList() {
        routes = new ArrayList<>();
        routes.add(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 1));
        routes.add(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 2));
        routes.add(new Route(1, "SEATTLE", "PORTLAND", 1, "", 3));
        routes.add(new Route(1, "SEATTLE", "PORTLAND", 1, "", 4));
        routes.add(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, "green", 5));
        routes.add(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, "pink", 6));
        routes.add(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, "yellow", 7));
        routes.add(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, "pink", 8));
        routes.add(new Route(3, "VANCOUVER", "CALGARY", 4, "", 9));
        routes.add(new Route(4, "SEATTLE", "CALGARY", 7, "", 10));
        routes.add(new Route(6, "SEATTLE", "HELENA", 15, "yellow", 11));
        routes.add(new Route(6, "PORTLAND", "SALT LAKE CITY", 15, "blue", 12));
        routes.add(new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, "orange", 13));
        routes.add(new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, "white", 14));
        routes.add(new Route(2, "LOS ANGELES", "LAS VEGAS", 2, "", 15));
        routes.add(new Route(3, "LOS ANGELES", "PHOENIX", 4, "", 16));
        routes.add(new Route(6, "LOS ANGELES", "EL PASO", 15, "black", 17));
        routes.add(new Route(6, "CALGARY", "WINNIPEG", 15, "white", 18));
        routes.add(new Route(4, "CALGARY", "HELENA", 7, "", 19));
        routes.add(new Route(3, "SALT LAKE CITY", "HELENA", 4, "pink", 20));
        routes.add(new Route(3, "SALT LAKE CITY", "DENVER", 4, "red", 21));
        routes.add(new Route(3, "SALT LAKE CITY", "DENVER", 4, "yellow", 22));
        routes.add(new Route(3, "LAS VEGAS", "SALT LAKE CITY", 4, "orange", 23));
        routes.add(new Route(5, "PHOENIX", "DENVER", 10, "white", 24));
        routes.add(new Route(3, "PHOENIX", "SANTA FE", 4, "", 25));
        routes.add(new Route(3, "PHOENIX", "EL PASO", 4, "", 26));
        routes.add(new Route(4, "HELENA", "WINNIPEG", 7, "blue", 27));
        routes.add(new Route(6, "HELENA", "DULUTH", 15, "orange", 28));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, "red", 29));
        routes.add(new Route(4, "HELENA", "DENVER", 7, "green", 30));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 31));
        routes.add(new Route(4, "DENVER", "OMAHA", 7, "pink", 32));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "black", 33));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "orange", 34));
        routes.add(new Route(4, "DENVER", "OKLAHOMA CITY", 7, "red", 345));
        routes.add(new Route(3, "SANTA FE", "OKLAHOMA CITY", 4, "blue", 36));
        routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 37));
        routes.add(new Route(5, "EL PASO", "OKLAHOMA CITY", 10, "yellow", 38));
        routes.add(new Route(4, "EL PASO", "DALLAS", 7, "red", 39));
        routes.add(new Route(6, "EL PASO", "HOUSTON", 15, "green", 40));
        routes.add(new Route(4, "WINNIPEG", "DULUTH", 7, "black", 41));
        routes.add(new Route(6, "WINNIPEG", "SAULT ST. MARIE", 15, "", 42));
        routes.add(new Route(3, "DULUTH", "SAULT ST. MARIE", 4, "", 43));
        routes.add(new Route(6, "DULUTH", "TORONTO", 15, "pink", 44));
        routes.add(new Route(3, "DULUTH", "CHICAGO", 4, "red", 45));
        routes.add(new Route(2, "DULUTH", "OMAHA", 2, "", 46));
        routes.add(new Route(2, "DULUTH", "OMAHA", 2, "", 47));
        routes.add(new Route(4, "OMAHA", "CHICAGO", 7, "blue", 48));
        routes.add(new Route(1, "OMAHA", "KANSAS CITY", 1, "", 49));
        routes.add(new Route(1, "OMAHA", "KANSAS CITY", 1, "", 50));
        routes.add(new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, "blue", 51));
        routes.add(new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, "pink", 52));
        routes.add(new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 53));
        routes.add(new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 54));
        routes.add(new Route(2, "OKLAHOMA CITY", "LITTLE ROCK", 2, "", 55));
        routes.add(new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 56));
        routes.add(new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 57));
        routes.add(new Route(1, "DALLAS", "HOUSTON", 1, "", 58));
        routes.add(new Route(1, "DALLAS", "HOUSTON", 1, "", 59));
        routes.add(new Route(2, "DALLAS", "LITTLE ROCK", 2, "", 60));
        routes.add(new Route(2, "HOUSTON", "NEW ORLEANS", 2, "", 61));
        routes.add(new Route(3, "LITTLE ROCK", "NEW ORLEANS", 4, "green", 62));
        routes.add(new Route(3, "LITTLE ROCK", "NASHVILLE", 4, "white", 63));
        routes.add(new Route(2, "SAINT LOUIS", "LITTLE ROCK", 2, "", 64));
        routes.add(new Route(2, "SAINT LOUIS", "NASHVILLE", 2, "", 65));
        routes.add(new Route(5, "SAINT LOUIS", "PITTSBURGH", 10, "", 66));
        routes.add(new Route(2, "SAINT LOUIS", "CHICAGO", 2, "green", 67));
        routes.add(new Route(2, "SAINT LOUIS", "CHICAGO", 2, "white", 68));
        routes.add(new Route(3, "CHICAGO", "PITTSBURGH", 4, "orange", 69));
        routes.add(new Route(3, "CHICAGO", "PITTSBURGH", 4, "black", 70));
        routes.add(new Route(4, "CHICAGO", "TORONTO", 7, "white", 71));
        routes.add(new Route(5, "SAULT ST. MARIE", "MONTREAL", 10, "black", 72));
        routes.add(new Route(2, "SAULT ST. MARIE", "TORONTO", 2, "", 73));
        routes.add(new Route(3, "TORONTO", "MONTREAL", 4, "", 74));
        routes.add(new Route(2, "TORONTO", "PITTSBURGH", 2, "", 75));
        routes.add(new Route(2, "MONTREAL", "BOSTON", 2, "", 76));
        routes.add(new Route(2, "MONTREAL", "BOSTON", 2, "", 77));
        routes.add(new Route(3, "MONTREAL", "NEW YORK", 4, "blue", 78));
        routes.add(new Route(2, "BOSTON", "NEW YORK", 2, "yellow", 79));
        routes.add(new Route(2, "BOSTON", "NEW YORK", 2, "red", 80));
        routes.add(new Route(2, "PITTSBURGH", "NEW YORK", 2, "white", 81));
        routes.add(new Route(2, "PITTSBURGH", "NEW YORK", 2, "green", 82));
        routes.add(new Route(2, "PITTSBURGH", "WASHINGTON", 2, "", 83));
        routes.add(new Route(2, "PITTSBURGH", "RALEIGH", 2, "", 84));
        routes.add(new Route(2, "NEW YORK", "WASHINGTON", 2, "orange", 85));
        routes.add(new Route(2, "NEW YORK", "WASHINGTON", 2, "black", 86));
        routes.add(new Route(4, "NASHVILLE", "PITTSBURGH", 7, "yellow", 87));
        routes.add(new Route(3, "NASHVILLE", "RALEIGH", 4, "black", 88));
        routes.add(new Route(1, "NASHVILLE", "ATLANTA", 1, "", 89));
        routes.add(new Route(4, "NEW ORLEANS", "ATLANTA", 7, "yellow", 90));
        routes.add(new Route(4, "NEW ORLEANS", "ATLANTA", 7, "orange", 91));
        routes.add(new Route(6, "NEW ORLEANS", "MIAMI", 15, "red", 92));
        routes.add(new Route(5, "ATLANTA", "MIAMI", 10, "blue", 93));
        routes.add(new Route(2, "ATLANTA", "RALEIGH", 2, "", 94));
        routes.add(new Route(2, "ATLANTA", "RALEIGH", 2, "", 95));
        routes.add(new Route(2, "ATLANTA", "CHARLESTON", 2, "", 96));
        routes.add(new Route(4, "CHARLESTON", "MIAMI", 7, "pink", 97));
        routes.add(new Route(2, "RALEIGH", "CHARLESTON", 2, "", 98));
        routes.add(new Route(2, "RALEIGH", "WASHINGTON", 2, "", 99));
        routes.add(new Route(2, "RALEIGH", "WASHINGTON", 2, "", 100));
    }

    // create a set of RouteSegment objects
    // create new RouteSegments representing each segment of the route
    // put in x and y coordinates of point A and B
    // put in route id
    // add the segments to the set
    // routeSegments.put(routeId, set of routeSegments)
    // each segment is about 40 units long and 10 units thick
    // x values increase from left to right
    // y values increase from top to bottom
    private void initializeRouteSegmentData() {

        RouteSegment rs1;
        RouteSegment rs2;
        RouteSegment rs3;
        RouteSegment rs4;
        RouteSegment rs5;
        RouteSegment rs6;
        RouteSegment rs7;
        RouteSegment rs8;
        RouteSegment rs9;
        RouteSegment rs10;

        // Vancouver to Seattle, id 1
        HashSet<RouteSegment> vancouverSeattle1 = new HashSet<>();
        rs1 = new RouteSegment(112, 102, 111, 134, 1);
        vancouverSeattle1.add(rs1);
        routeSegments.put(1, vancouverSeattle1);

        // Vancouver to Seattle, id 2
        HashSet<RouteSegment> vancouverSeattle2 = new HashSet<>();
        rs1 = new RouteSegment(127, 99, 128, 133, 2);
        vancouverSeattle2.add(rs1);
        routeSegments.put(2, vancouverSeattle2);

        // Seattle to Portland, id 3
        HashSet<RouteSegment> seattlePortland1 = new HashSet<>();
        rs1 = new RouteSegment(102, 159, 89, 191, 3);
        seattlePortland1.add(rs1);
        routeSegments.put(3, seattlePortland1);

        // Seattle to Portland, id 4
        HashSet<RouteSegment> seattlePortland2 = new HashSet<>();
        rs1 = new RouteSegment(119, 165, 104, 196, 4);
        seattlePortland2.add(rs1);
        routeSegments.put(4, seattlePortland2);

        // Portland to San Francisco 1, id 5
        HashSet<RouteSegment> portlandSF1 = new HashSet<>();
        rs1 = new RouteSegment(64, 419, 53, 388,
                5);
        rs2 = new RouteSegment(52, 378, 49, 347,
                5);
        rs3 = new RouteSegment(49, 337, 50, 303,
                5);
        rs4 = new RouteSegment(50, 295, 58, 262,
                5);
        rs5 = new RouteSegment(61, 255, 76, 222,
                5);
        portlandSF1.add(rs1);
        portlandSF1.add(rs2);
        portlandSF1.add(rs3);
        portlandSF1.add(rs4);
        portlandSF1.add(rs5);
        routeSegments.put(5, portlandSF1);

        // Portland to San Francisco 2, id 6
        HashSet<RouteSegment> portlandSF2 = new HashSet<>();
        rs1 = new RouteSegment(80, 421, 69, 388,
                6);
        rs2 = new RouteSegment(68, 381, 65, 347,
                6);
        rs3 = new RouteSegment(65, 339, 67, 304,
                6);
        rs4 = new RouteSegment(67, 296, 75, 263,
                6);
        rs5 = new RouteSegment(76, 255, 93, 223,
                6);
        portlandSF2.add(rs1);
        portlandSF2.add(rs2);
        portlandSF2.add(rs3);
        portlandSF2.add(rs4);
        portlandSF2.add(rs5);
        routeSegments.put(6, portlandSF2);

        // San Francisco to Los Angeles, id 7
        HashSet<RouteSegment> SFtoLA1 = new HashSet<>();
        rs1 = new RouteSegment(75, 456, 90, 489,
                7);
        rs2 = new RouteSegment(95, 493, 117, 522,
                7);
        rs3 = new RouteSegment(121, 529, 149, 552,
                7);
        SFtoLA1.add(rs1);
        SFtoLA1.add(rs2);
        SFtoLA1.add(rs3);
        routeSegments.put(7, SFtoLA1);

        // San Francisco to Los Angeles, id 8
        HashSet<RouteSegment> SFtoLA2 = new HashSet<>();
        rs1 = new RouteSegment(87, 447, 104, 480,
                8);
        rs2 = new RouteSegment(108, 485, 130, 514,
                8);
        rs3 = new RouteSegment(135, 519, 161, 543,
                8);
        SFtoLA2.add(rs1);
        SFtoLA2.add(rs2);
        SFtoLA2.add(rs3);
        routeSegments.put(8, SFtoLA2);

        // Vancouver to Calgary, id 9
        HashSet<RouteSegment> vancouverCalgary = new HashSet<>();
        rs1 = new RouteSegment(141, 83,
                181, 80, 9);
        vancouverCalgary.add(rs1);
        rs2 = new RouteSegment(186, 77,
                230, 74, 9);
        vancouverCalgary.add(rs2);
        rs3 = new RouteSegment(234, 74,
                274, 70, 9);
        vancouverCalgary.add(rs3);
        routeSegments.put(9, vancouverCalgary);

        // Seattle to Calgary, id 10
        HashSet<RouteSegment> seattleCalgary = new HashSet<>();
        rs1 = new RouteSegment(137, 147,
                178, 147, 10);
        seattleCalgary.add(rs1);
        rs2 = new RouteSegment(185, 148,
                223, 143, 10);
        seattleCalgary.add(rs2);
        rs3 = new RouteSegment(231, 139,
                264, 118, 10);
        seattleCalgary.add(rs3);
        rs4 = new RouteSegment(269, 112,
                286, 80, 10);
        seattleCalgary.add(rs4);
        routeSegments.put(10, seattleCalgary);

        // Seattle to Helena, id 11
        HashSet<RouteSegment> seattleHelena = new HashSet<>();
        rs1 = new RouteSegment(137, 166,
                174, 176, 11);
        seattleHelena.add(rs1);
        rs2 = new RouteSegment(180, 177,
                219, 182, 11);
        seattleHelena.add(rs2);
        rs3 = new RouteSegment(227, 185,
                265, 193, 11);
        seattleHelena.add(rs3);
        rs4 = new RouteSegment(272, 193,
                311, 200, 11);
        seattleHelena.add(rs4);
        rs5 = new RouteSegment(317, 202,
                356, 210, 11);
        seattleHelena.add(rs5);
        rs6 = new RouteSegment(363, 210,
                401, 219, 11);
        seattleHelena.add(rs6);
        routeSegments.put(11, seattleHelena);

        // Portland to SLC, id 12
        HashSet<RouteSegment> portlandSLC = new HashSet<>();
        rs1 = new RouteSegment(109, 211,
                145, 218, 12);
        portlandSLC.add(rs1);
        rs2 = new RouteSegment(153, 219,
                190, 233, 12);
        portlandSLC.add(rs2);
        rs3 = new RouteSegment(198, 234,
                230, 254, 12);
        portlandSLC.add(rs3);
        rs4 = new RouteSegment(237, 258,
                265, 281, 12);
        portlandSLC.add(rs4);
        rs5 = new RouteSegment(272, 285,
                297, 312, 12);
        portlandSLC.add(rs5);
        rs6 = new RouteSegment(302, 317,
                319, 347, 12);
        portlandSLC.add(rs6);
        routeSegments.put(12, portlandSLC);

        // San Francisco to SLC, id 13
        HashSet<RouteSegment> SFtoSLC1 = new HashSet<>();
        rs1 = new RouteSegment(94, 422, 131, 411,
                13);
        rs2 = new RouteSegment(137, 410, 174, 399,
                13);
        rs3 = new RouteSegment(180, 397, 217, 385,
                13);
        rs4 = new RouteSegment(223, 384, 259, 373,
                13);
        rs5 = new RouteSegment(267, 370, 302, 360,
                13);
        SFtoSLC1.add(rs1);
        SFtoSLC1.add(rs2);
        SFtoSLC1.add(rs3);
        SFtoSLC1.add(rs4);
        SFtoSLC1.add(rs5);
        routeSegments.put(13, SFtoSLC1);

        // SF to SLC 2, id 14
        HashSet<RouteSegment> SFtoSLC2 = new HashSet<>();
        rs1 = new RouteSegment(101, 437, 138, 426,
                14);
        rs2 = new RouteSegment(145, 423, 182, 412,
                14);
        rs3 = new RouteSegment(187, 411, 224, 399,
                14);
        rs4 = new RouteSegment(231, 397, 267, 386,
                14);
        rs5 = new RouteSegment(274, 384, 308, 374,
                14);
        SFtoSLC2.add(rs1);
        SFtoSLC2.add(rs2);
        SFtoSLC2.add(rs3);
        SFtoSLC2.add(rs4);
        SFtoSLC2.add(rs5);
        routeSegments.put(14, SFtoSLC2);

        // Los Angeles to Las Vegas, id 15
        HashSet<RouteSegment> LAVegas = new HashSet<>();
        rs1 = new RouteSegment(176, 541,
                196, 509, 15);
        LAVegas.add(rs1);
        rs2 = new RouteSegment(201, 501,
                241, 494, 15);
        LAVegas.add(rs2);
        routeSegments.put(15, LAVegas);

        // Los Angeles to Phoenix, id 16
        HashSet<RouteSegment> LAPhoenix = new HashSet<>();
        rs1 = new RouteSegment(184, 554,
                226, 548, 16);
        LAPhoenix.add(rs1);
        rs2 = new RouteSegment(232, 548,
                273, 548, 16);
        LAPhoenix.add(rs2);
        rs3 = new RouteSegment(277, 548,
                320, 558, 16);
        LAPhoenix.add(rs3);
        routeSegments.put(16, LAPhoenix);

        // LA to El Paso, id 17
        HashSet<RouteSegment> LAelPaso = new HashSet<>();
        rs1 = new RouteSegment(190, 569,
                219, 589, 17);
        LAelPaso.add(rs1);
        rs2 = new RouteSegment(230, 595,
                261, 608, 17);
        LAelPaso.add(rs2);
        rs3 = new RouteSegment(274, 610,
                308, 619, 17);
        LAelPaso.add(rs3);
        rs4 = new RouteSegment(317, 619,
                356, 626, 17);
        LAelPaso.add(rs4);
        rs5 = new RouteSegment(363, 626,
                401, 623, 17);
        LAelPaso.add(rs5);
        rs6 = new RouteSegment(407, 622,
                445, 614, 17);
        LAelPaso.add(rs6);
        routeSegments.put(17, LAelPaso);

        // Calgary to Winnipeg, id 18
        HashSet<RouteSegment> calgaryWinnipeg = new HashSet<>();
        rs1 = new RouteSegment(309, 62,
                343, 47, 18);
        calgaryWinnipeg.add(rs1);
        rs2 = new RouteSegment(353, 47,
                390, 39, 18);
        calgaryWinnipeg.add(rs2);
        rs3 = new RouteSegment(398, 39,
                437, 37, 18);
        calgaryWinnipeg.add(rs3);
        rs4 = new RouteSegment(444, 35,
                483, 39, 18);
        calgaryWinnipeg.add(rs4);
        rs5 = new RouteSegment(490, 40,
                528, 49, 18);
        calgaryWinnipeg.add(rs5);
        rs6 = new RouteSegment(535, 52,
                571, 66, 18);
        calgaryWinnipeg.add(rs6);
        routeSegments.put(18, calgaryWinnipeg);

        // Calgary to Helena, id 19
        HashSet<RouteSegment> calgaryHelena = new HashSet<>();
        rs1 = new RouteSegment(305, 80,
                332, 109, 19);
        calgaryHelena.add(rs1);
        rs2 = new RouteSegment(333, 113,
                362, 140, 19);
        calgaryHelena.add(rs2);
        rs3 = new RouteSegment(364, 145,
                390, 174, 19);
        calgaryHelena.add(rs3);
        rs4 = new RouteSegment(393, 178,
                422, 206, 19);
        calgaryHelena.add(rs4);
        routeSegments.put(19, calgaryHelena);

        // SLC to Helena, id 20
        HashSet<RouteSegment> SLCHelena = new HashSet<>();
        rs1 = new RouteSegment(342, 340,
                364, 310, 20);
        SLCHelena.add(rs1);
        rs2 = new RouteSegment(366, 310,
                388, 274, 20);
        SLCHelena.add(rs2);
        rs3 = new RouteSegment(390, 268,
                413, 238, 20);
        SLCHelena.add(rs3);
        routeSegments.put(20, SLCHelena);

        // SLC to Denver1, id 21
        HashSet<RouteSegment> SLCtoDenver1 = new HashSet<>();
        rs1 = new RouteSegment(345, 356, 383, 363,
                21);
        rs2 = new RouteSegment(391, 363, 430, 369,
                21);
        rs3 = new RouteSegment(437, 371, 471, 378,
                21);
        SLCtoDenver1.add(rs1);
        SLCtoDenver1.add(rs2);
        SLCtoDenver1.add(rs3);
        routeSegments.put(21, SLCtoDenver1);

        // SLC to Denver 2, id 22
        HashSet<RouteSegment> SLCtoDenver2 = new HashSet<>();
        rs1 = new RouteSegment(343, 371, 382, 377,
                22);
        rs2 = new RouteSegment(389, 378, 428, 384,
                22);
        rs3 = new RouteSegment(435, 385, 471, 392,
                22);
        SLCtoDenver2.add(rs1);
        SLCtoDenver2.add(rs2);
        SLCtoDenver2.add(rs3);
        routeSegments.put(22, SLCtoDenver2);

        // Las Vegas to SLC, id 23
        HashSet<RouteSegment> lasVegasSLC = new HashSet<>();
        rs1 = new RouteSegment(271, 490,
                302, 463, 23);
        lasVegasSLC.add(rs1);
        rs2 = new RouteSegment(304, 455,
                321, 425, 23);
        lasVegasSLC.add(rs2);
        rs3 = new RouteSegment(322, 417,
                328, 382, 23);
        lasVegasSLC.add(rs3);
        routeSegments.put(23, lasVegasSLC);

        // Phoenix to Denver, id 24
        HashSet<RouteSegment> phoenixDenver = new HashSet<>();
        rs1 = new RouteSegment(334, 552,
                349, 521, 24);
        phoenixDenver.add(rs1);
        rs2 = new RouteSegment(352, 514,
                370, 484, 24);
        phoenixDenver.add(rs2);
        rs3 = new RouteSegment(374, 478,
                397, 449, 24);
        phoenixDenver.add(rs3);
        rs4 = new RouteSegment(404, 444,
                430, 423, 24);
        phoenixDenver.add(rs4);
        rs5 = new RouteSegment(439, 418,
                474, 406, 24);
        phoenixDenver.add(rs5);
        routeSegments.put(24, phoenixDenver);

        // Phoenix to Santa Fe, id 25
        HashSet<RouteSegment> phoenixSantaFe = new HashSet<>();
        rs1 = new RouteSegment(350, 560,
                388, 545, 25);
        phoenixSantaFe.add(rs1);
        rs2 = new RouteSegment(393, 543,
                430, 528, 25);
        phoenixSantaFe.add(rs2);
        rs3 = new RouteSegment(435, 526,
                473, 510, 25);
        phoenixSantaFe.add(rs3);
        routeSegments.put(25, phoenixSantaFe);

        // Phoenix to El Paso, id 26
        HashSet<RouteSegment> phoenixElPaso = new HashSet<>();
        rs1 = new RouteSegment(341, 575,
                380, 586, 26);
        phoenixElPaso.add(rs1);
        rs2 = new RouteSegment(385, 586,
                424, 595, 26);
        phoenixElPaso.add(rs2);
        rs3 = new RouteSegment(430, 596,
                470, 607, 26);
        phoenixElPaso.add(rs3);
        routeSegments.put(26, phoenixElPaso);

        // Helena to Winnipeg, id 27
        HashSet<RouteSegment> helenaWinnipeg = new HashSet<>();
        rs1 = new RouteSegment(439, 206,
                466, 181, 27);
        helenaWinnipeg.add(rs1);
        rs2 = new RouteSegment(469, 176,
                500, 149, 27);
        helenaWinnipeg.add(rs2);
        rs3 = new RouteSegment(505, 145,
                530, 199, 27);
        helenaWinnipeg.add(rs3);
        rs4 = new RouteSegment(537, 115,
                564, 92, 27);
        helenaWinnipeg.add(rs4);
        routeSegments.put(27, helenaWinnipeg);

        // Helena to Duluth, id 28
        HashSet<RouteSegment> helenaDuluth = new HashSet<>();
        rs1 = new RouteSegment(440, 220,
                480, 219, 28);
        helenaDuluth.add(rs1);
        rs2 = new RouteSegment(485, 219,
                526, 218, 28);
        helenaDuluth.add(rs2);
        rs3 = new RouteSegment(531, 218,
                572, 217, 28);
        helenaDuluth.add(rs3);
        rs4 = new RouteSegment(577, 217,
                618, 216, 28);
        helenaDuluth.add(rs4);
        rs5 = new RouteSegment(623, 215,
                664, 215, 28);
        helenaDuluth.add(rs5);
        rs6 = new RouteSegment(672, 215,
                713, 215, 28);
        helenaDuluth.add(rs6);
        routeSegments.put(28, helenaDuluth);

        // Helena to Omaha, id 29
        HashSet<RouteSegment> helenaOmaha = new HashSet<>();
        rs1 = new RouteSegment(452, 233,
                487, 247, 29);
        helenaOmaha.add(rs1);
        rs2 = new RouteSegment(496, 248,
                531, 262, 29);
        helenaOmaha.add(rs2);
        rs3 = new RouteSegment(537, 265,
                574, 277, 29);
        helenaOmaha.add(rs3);
        rs4 = new RouteSegment(581, 280,
                618, 293, 29);
        helenaOmaha.add(rs4);
        rs5 = new RouteSegment(623, 295,
                659, 310, 29);
        helenaOmaha.add(rs5);
        routeSegments.put(29, helenaOmaha);

        // Helena to Denver, id 30
        HashSet<RouteSegment> helenaDenver = new HashSet<>();
        rs1 = new RouteSegment(428, 236,
                443, 267, 30);
        helenaDenver.add(rs1);
        rs2 = new RouteSegment(447, 272,
                460, 304, 30);
        helenaDenver.add(rs2);
        rs3 = new RouteSegment(462, 311,
                477, 343, 30);
        helenaDenver.add(rs3);
        rs4 = new RouteSegment(479, 347,
                494, 381, 30);
        routeSegments.put(30, helenaDenver);

        // Denver to Santa Fe, id 31
        HashSet<RouteSegment> denverSantaFe = new HashSet<>();
        rs1 = new RouteSegment(490, 417,
                488, 455, 31);
        denverSantaFe.add(rs1);
        rs2 = new RouteSegment(488, 460,
                487, 497, 31);
        denverSantaFe.add(rs2);
        routeSegments.put(31, denverSantaFe);


        // Denver to Omaha, id 32
        HashSet<RouteSegment> denverOmaha = new HashSet<>();
        rs1 = new RouteSegment(507, 391,
                539, 366, 32);
        denverOmaha.add(rs1);
        rs2 = new RouteSegment(546, 362,
                483, 348, 32);
        denverOmaha.add(rs2);
        rs3 = new RouteSegment(588, 345,
                627, 334, 32);
        denverOmaha.add(rs3);
        rs4 = new RouteSegment(632, 335,
                673, 328, 32);
        denverOmaha.add(rs4);
        routeSegments.put(32, denverOmaha);

        // Denver to Kansas City 1, id 33
        HashSet<RouteSegment> denverKansasCity1 = new HashSet<>();
        rs1 = new RouteSegment(520, 396, 559, 399,
                33);
        rs2 = new RouteSegment(567, 400, 604, 396,
                33);
        rs3 = new RouteSegment(613, 396, 650, 389,
                33);
        rs4 = new RouteSegment(660, 386, 693, 374,
                33);
        denverKansasCity1.add(rs1);
        denverKansasCity1.add(rs2);
        denverKansasCity1.add(rs3);
        denverKansasCity1.add(rs4);
        routeSegments.put(33, denverKansasCity1);

        // Denver to Kansas City 2, id 34
        HashSet<RouteSegment> denverKansasCity2 = new HashSet<>();
        rs1 = new RouteSegment(520, 412, 559, 415,
                34);
        rs2 = new RouteSegment(567, 415, 604, 412,
                34);
        rs3 = new RouteSegment(613, 411, 650, 406,
                34);
        rs4 = new RouteSegment(660, 403, 693, 389,
                34);
        denverKansasCity2.add(rs1);
        denverKansasCity2.add(rs2);
        denverKansasCity2.add(rs3);
        denverKansasCity2.add(rs4);
        routeSegments.put(34, denverKansasCity2);

        // Denver to OKC, id 35
        HashSet<RouteSegment> denverOKC = new HashSet<>();
        rs1 = new RouteSegment(507, 421, 531, 445,
                35);
        rs2 = new RouteSegment(539, 449, 575, 465,
                35);
        rs3 = new RouteSegment(582, 466, 622, 473,
                35);
        rs4 = new RouteSegment(629, 473, 667, 474,
                35);
        denverOKC.add(rs1);
        denverOKC.add(rs2);
        denverOKC.add(rs3);
        denverOKC.add(rs4);
        routeSegments.put(35, denverOKC);

        // Santa Fe to OKC, id 36
        HashSet<RouteSegment> santaFeOKC = new HashSet<>();
        rs1 = new RouteSegment(505, 508, 545, 502,
                36);
        rs2 = new RouteSegment(551, 502, 593, 498,
                36);
        rs3 = new RouteSegment(598, 498, 639, 494,
                36);
        santaFeOKC.add(rs1);
        santaFeOKC.add(rs2);
        santaFeOKC.add(rs3);
        routeSegments.put(36, santaFeOKC);

        // Santa Fe to El Paso, id 37
        HashSet<RouteSegment> santaFeElPaso = new HashSet<>();
        rs1 = new RouteSegment(486, 523, 485, 556,
                37);
        rs2 = new RouteSegment(482, 562, 481, 597,
                37);
        santaFeElPaso.add(rs1);
        santaFeElPaso.add(rs2);
        routeSegments.put(37, santaFeElPaso);

        // El Paso to OKC, id 38
        HashSet<RouteSegment> elPasoOKC = new HashSet<>();
        rs1 = new RouteSegment(497, 603, 534, 592,
                38);
        rs2 = new RouteSegment(539, 589, 575, 573,
                38);
        rs3 = new RouteSegment(582, 570, 613, 551,
                38);
        rs4 = new RouteSegment(620, 545, 648, 522,
                38);
        rs5 = new RouteSegment(653, 515, 675, 488,
                38);
        elPasoOKC.add(rs1);
        elPasoOKC.add(rs2);
        elPasoOKC.add(rs3);
        elPasoOKC.add(rs4);
        elPasoOKC.add(rs5);
        routeSegments.put(38, elPasoOKC);

        // El Paso to Dallas, id 39
        HashSet<RouteSegment> elPasoDallas = new HashSet<>();
        rs1 = new RouteSegment(520, 617, 559, 610,
                39);
        rs2 = new RouteSegment(566, 610, 605, 605,
                39);
        rs3 = new RouteSegment(611, 604, 652, 599,
                39);
        rs4 = new RouteSegment(657, 596, 699, 591,
                39);
        elPasoDallas.add(rs1);
        elPasoDallas.add(rs2);
        elPasoDallas.add(rs3);
        elPasoDallas.add(rs4);
        routeSegments.put(39, elPasoDallas);

        // El Paso to Houston, id 40
        HashSet<RouteSegment> elPasoHouston = new HashSet<>();
        rs1 = new RouteSegment(493, 621,
                523, 639, 40);
        elPasoHouston.add(rs1);
        rs2 = new RouteSegment(531, 639,
                570, 649, 40);
        elPasoHouston.add(rs2);
        rs3 = new RouteSegment(578, 652,
                613, 656, 40);
        elPasoHouston.add(rs3);
        rs4 = new RouteSegment(622, 656,
                661, 658, 40);
        elPasoHouston.add(rs4);
        rs4 = new RouteSegment(670, 658,
                705, 652, 40);
        elPasoHouston.add(rs4);
        rs5 = new RouteSegment(716, 649,
                751, 640, 40);
        elPasoHouston.add(rs5);
        routeSegments.put(40, elPasoHouston);

        // Winnipeg to Duluth, id 41
        HashSet<RouteSegment> winnipegDuluth = new HashSet<>();
        rs1 = new RouteSegment(592, 91,
                619, 115, 41);
        winnipegDuluth.add(rs1);
        rs2 = new RouteSegment(623, 119,
                653, 145, 41);
        winnipegDuluth.add(rs2);
        rs3 = new RouteSegment(658, 151,
                686, 173, 41);
        winnipegDuluth.add(rs3);
        rs4 = new RouteSegment(691, 178,
                721, 203, 41);
        winnipegDuluth.add(rs4);
        routeSegments.put(41, winnipegDuluth);

        // Winnipeg to Sault St Marie, id 42
        HashSet<RouteSegment> winnipegStMarie = new HashSet<>();
        rs1 = new RouteSegment(607, 76,
                648, 84, 42);
        winnipegStMarie.add(rs1);
        rs2 = new RouteSegment(653, 84,
                692, 92, 42);
        winnipegStMarie.add(rs2);
        rs3 = new RouteSegment(698, 92,
                738, 99, 42);
        winnipegStMarie.add(rs3);
        rs4 = new RouteSegment(745, 100,
                782, 108, 42);
        winnipegStMarie.add(rs4);
        rs5 = new RouteSegment(790, 110,
                829, 118, 42);
        winnipegStMarie.add(rs5);
        rs6 = new RouteSegment(835, 119,
                872, 125, 42);
        winnipegStMarie.add(rs6);
        routeSegments.put(42, winnipegStMarie);

        // Duluth to Sault St. Marie, id 43
        HashSet<RouteSegment> duluthSaultStMarie = new HashSet<>();
        rs1 = new RouteSegment(754, 196,
                789, 179, 43);
        duluthSaultStMarie.add(rs1);
        rs2 = new RouteSegment(795, 177,
                833, 164, 43);
        duluthSaultStMarie.add(rs2);
        rs3 = new RouteSegment(839, 161,
                876, 145, 43);
        duluthSaultStMarie.add(rs3);
        routeSegments.put(43, duluthSaultStMarie);

        // Duluth to Toronto, id 44
        HashSet<RouteSegment> duluthToronto = new HashSet<>();
        rs1 = new RouteSegment(740, 212,
                780, 206, 44);
        duluthToronto.add(rs1);
        rs2 = new RouteSegment(786, 204,
                826, 198, 44);
        duluthToronto.add(rs2);
        rs3 = new RouteSegment(832, 196,
                872, 190, 44);
        duluthToronto.add(rs3);
        rs4 = new RouteSegment(878, 189,
                918, 183, 44);
        duluthToronto.add(rs4);
        rs5 = new RouteSegment(924, 182,
                964, 176, 44);
        duluthToronto.add(rs5);
        rs6 = new RouteSegment(970, 174,
                1010, 168, 44);
        duluthToronto.add(rs6);
        routeSegments.put(44, duluthToronto);

        // Duluth to Chicago, id 45
        HashSet<RouteSegment> duluthChicago = new HashSet<>();
        rs1 = new RouteSegment(741, 228,
                775, 245, 45);
        duluthChicago.add(rs1);
        rs2 = new RouteSegment(780, 246,
                819, 258, 45);
        duluthChicago.add(rs2);
        rs3 = new RouteSegment(824, 258,
                863, 267, 45);
        duluthChicago.add(rs3);
        routeSegments.put(45, duluthChicago);

        // Duluth to Omaha 1, id 46
        HashSet<RouteSegment> duluthOmaha1 = new HashSet<>();
        rs1 = new RouteSegment(711, 229, 698, 259,
                46);
        rs2 = new RouteSegment(694, 266, 681, 299,
                46);
        duluthOmaha1.add(rs1);
        duluthOmaha1.add(rs2);
        routeSegments.put(46, duluthOmaha1);

        // Duluth to Omaha 2, id 47
        HashSet<RouteSegment> duluthOmaha2 = new HashSet<>();
        rs1 = new RouteSegment(726, 234, 715, 265,
                47);
        rs2 = new RouteSegment(711, 273, 697, 304,
                47);
        duluthOmaha2.add(rs1);
        duluthOmaha2.add(rs2);
        routeSegments.put(47, duluthOmaha2);

        // Omaha to Chicago, id 48
        HashSet<RouteSegment> omahaChicago = new HashSet<>();
        rs1 = new RouteSegment(704, 317,
                734, 297, 48);
        omahaChicago.add(rs1);
        rs2 = new RouteSegment(741, 293,
                772, 274, 48);
        omahaChicago.add(rs2);
        rs3 = new RouteSegment(783, 270,
                822, 277, 48);
        omahaChicago.add(rs3);
        rs4 = new RouteSegment(829, 277,
                866, 282, 48);
        omahaChicago.add(rs4);
        routeSegments.put(48, omahaChicago);

        // Omaha to Kansas City 1, id 49
        HashSet<RouteSegment> omahaKansasCity1 = new HashSet<>();
        rs1 = new RouteSegment(689, 337, 704, 367,
                49);
        omahaKansasCity1.add(rs1);
        routeSegments.put(49, omahaKansasCity1);

        // Omaha to Kansas City 2, id 50
        HashSet<RouteSegment> omahaKansasCity2 = new HashSet<>();
        rs1 = new RouteSegment(704, 329, 719, 359,
                50);
        omahaKansasCity2.add(rs1);
        routeSegments.put(50, omahaKansasCity2);

        // Kansas to St Louis 1, id 51
        HashSet<RouteSegment> kansasStLouis1 = new HashSet<>();
        rs1 = new RouteSegment(729, 369, 764, 367,
                51);
        rs2 = new RouteSegment(774, 369, 811, 366,
                51);
        kansasStLouis1.add(rs1);
        kansasStLouis1.add(rs2);
        routeSegments.put(51, kansasStLouis1);

        // Kansas to St Louis 2, id 52
        HashSet<RouteSegment> kansasStLouis2 = new HashSet<>();
        rs1 = new RouteSegment(727, 384, 766, 382,
                52);
        rs2 = new RouteSegment(772, 384, 811, 381,
                52);
        kansasStLouis2.add(rs1);
        kansasStLouis2.add(rs2);
        routeSegments.put(52, kansasStLouis2);

        // Kansas City to OKC 1, id 53
        HashSet<RouteSegment> kansasCityOKC1 = new HashSet<>();
        rs1 = new RouteSegment(709, 392, 698, 423,
                53);
        rs2 = new RouteSegment(696, 430, 687, 463,
                53);
        kansasCityOKC1.add(rs1);
        kansasCityOKC1.add(rs2);
        routeSegments.put(53, kansasCityOKC1);

        // Kansas City to OKC 2, id 54
        HashSet<RouteSegment> kansasCityOKC2 = new HashSet<>();
        rs1 = new RouteSegment(726, 395, 715, 428,
                54);
        rs2 = new RouteSegment(711, 434, 703, 467,
                54);
        kansasCityOKC2.add(rs1);
        kansasCityOKC2.add(rs2);
        routeSegments.put(54, kansasCityOKC2);

        // OKC to Little Rock, id 55
        HashSet<RouteSegment> okcLittleRock = new HashSet<>();
        rs1 = new RouteSegment(706, 481,
                746, 480, 55);
        okcLittleRock.add(rs1);
        rs2 = new RouteSegment(752, 480,
                792, 480, 55);
        okcLittleRock.add(rs2);
        routeSegments.put(55, okcLittleRock);

        // OKC to Dallas 1, id 56
        HashSet<RouteSegment> okcDallas1 = new HashSet<>();
        rs1 = new RouteSegment(693, 493, 697, 528,
                56);
        rs2 = new RouteSegment(698, 534, 703, 569,
                56);
        okcDallas1.add(rs1);
        okcDallas1.add(rs2);
        routeSegments.put(56, okcDallas1);

        // OKC to Dallas 2, id 57
        HashSet<RouteSegment> okcDallas2 = new HashSet<>();
        rs1 = new RouteSegment(708, 491, 712, 523,
                57);
        rs2 = new RouteSegment(715, 530, 719, 566,
                57);
        okcDallas2.add(rs1);
        okcDallas2.add(rs2);
        routeSegments.put(57, okcDallas2);

        // Dallas to Houston 1, id 58
        HashSet<RouteSegment> dallasHouston1 = new HashSet<>();
        rs1 = new RouteSegment(722, 595, 746, 619,
                58);
        dallasHouston1.add(rs1);
        routeSegments.put(58, dallasHouston1);

        // Dallas to Houston 2, id 59
        HashSet<RouteSegment> dallasHouston2 = new HashSet<>();
        rs1 = new RouteSegment(734, 585, 757, 610,
                59);
        dallasHouston2.add(rs1);
        routeSegments.put(59, dallasHouston2);

        // Dallas to Little Rock, id 60
        HashSet<RouteSegment> dallasLittleRock = new HashSet<>();
        rs1 = new RouteSegment(739, 562,
                764, 532, 60);
        dallasLittleRock.add(rs1);
        rs2 = new RouteSegment(767, 527,
                790, 498, 60);
        dallasLittleRock.add(rs2);
        routeSegments.put(60, dallasLittleRock);

        // Houston to New Orleans, id 61
        HashSet<RouteSegment> houstonNewOrleans = new HashSet<>();
        rs1 = new RouteSegment(784, 629,
                826, 622, 61);
        houstonNewOrleans.add(rs1);
        rs2 = new RouteSegment(831, 621,
                871, 616, 61);
        houstonNewOrleans.add(rs2);
        routeSegments.put(61, houstonNewOrleans);

        // Little Rock to New Orleans, id 62
        HashSet<RouteSegment> littleRockNewOrleans = new HashSet<>();
        rs1 = new RouteSegment(815, 498,
                835, 529, 62);
        littleRockNewOrleans.add(rs1);
        rs2 = new RouteSegment(837, 535,
                856, 566, 62);
        littleRockNewOrleans.add(rs2);
        rs3 = new RouteSegment(858, 574,
                876, 604, 62);
        littleRockNewOrleans.add(rs3);
        routeSegments.put(62, littleRockNewOrleans);

        // Little Rock to Nashville, id 63
        HashSet<RouteSegment> littleRockNashville = new HashSet<>();
        rs1 = new RouteSegment(819, 485,
                862, 482, 63);
        littleRockNashville.add(rs1);
        rs2 = new RouteSegment(867, 478,
                904, 464, 63);
        littleRockNashville.add(rs2);
        rs3 = new RouteSegment(909, 459,
                939, 438, 63);
        littleRockNashville.add(rs3);
        routeSegments.put(63, littleRockNashville);

        // St Louis to Little Rock, id 64
        HashSet<RouteSegment> StLouisLittleRock = new HashSet<>();
        rs1 = new RouteSegment(827, 396,
                818, 430, 64);
        StLouisLittleRock.add(rs1);
        rs2 = new RouteSegment(816, 437,
                807, 471, 64);
        StLouisLittleRock.add(rs2);
        routeSegments.put(64, StLouisLittleRock);

        // St Louis to Nashville, id 65
        HashSet<RouteSegment> StLouisNashville = new HashSet<>();
        rs1 = new RouteSegment(841, 404,
                882, 412, 65);
        StLouisNashville.add(rs1);
        rs2 = new RouteSegment(886, 416,
                926, 425, 65);
        StLouisNashville.add(rs2);
        routeSegments.put(65, StLouisNashville);

        // St Louis to Pittsburg, id 66
        HashSet<RouteSegment> stLouisPittsburg = new HashSet<>();
        rs1 = new RouteSegment(842, 382,
                874, 366, 66);
        stLouisPittsburg.add(rs1);
        rs2 = new RouteSegment(883, 360,
                914, 344, 66);
        stLouisPittsburg.add(rs2);
        rs3 = new RouteSegment(923, 341,
                955, 323, 66);
        stLouisPittsburg.add(rs3);
        rs4 = new RouteSegment(963, 318,
                996, 302, 66);
        stLouisPittsburg.add(rs4);
        rs5 = new RouteSegment(1004, 297,
                1036, 281, 66);
        stLouisPittsburg.add(rs5);
        routeSegments.put(66, stLouisPittsburg);

        // St Louis to Chicago 1, id 67
        HashSet<RouteSegment> STLChicago1 = new HashSet<>();
        rs1 = new RouteSegment(820, 359, 838, 330,
                67);
        rs2 = new RouteSegment(844, 323, 863, 296,
                67);
        STLChicago1.add(rs1);
        STLChicago1.add(rs2);
        routeSegments.put(67, STLChicago1);

        // St Louis to Chicago 2, id 68
        HashSet<RouteSegment> STLChicago2 = new HashSet<>();
        rs1 = new RouteSegment(833, 366, 853, 339,
                68);
        rs2 = new RouteSegment(857, 332, 878, 303,
                68);
        STLChicago2.add(rs1);
        STLChicago2.add(rs2);
        routeSegments.put(68, STLChicago2);

        // Chicago to Pittsburgh 1, id 69
        HashSet<RouteSegment> chicagoPittsburgh1 = new HashSet<>();
        rs1 = new RouteSegment(896, 265, 933, 255,
                69);
        rs2 = new RouteSegment(941, 255, 979, 248,
                69);
        rs3 = new RouteSegment(988, 247, 1026, 251,
                69);
        chicagoPittsburgh1.add(rs1);
        chicagoPittsburgh1.add(rs2);
        chicagoPittsburgh1.add(rs3);
        routeSegments.put(69, chicagoPittsburgh1);

        // Chicago to Pittsburgh 2, id 70
        HashSet<RouteSegment> chicagoPittsburgh2 = new HashSet<>();
        rs1 = new RouteSegment(903, 281, 938, 271,
                70);
        rs2 = new RouteSegment(948, 270, 985, 265,
                70);
        rs3 = new RouteSegment(994, 265, 1031, 266,
                70);
        chicagoPittsburgh2.add(rs1);
        chicagoPittsburgh2.add(rs2);
        chicagoPittsburgh2.add(rs3);
        routeSegments.put(70, chicagoPittsburgh2);

        // Chicago to Toronto, id 71
        HashSet<RouteSegment> chicagoToronto = new HashSet<>();
        rs1 = new RouteSegment(879, 267, 907, 240,
                71);
        rs2 = new RouteSegment(910, 234, 944, 215,
                71);
        rs3 = new RouteSegment(952, 209, 989, 199,
                71);
        rs4 = new RouteSegment(995,198,1027,176, 71);
        chicagoToronto.add(rs1);
        chicagoToronto.add(rs2);
        chicagoToronto.add(rs3);
        chicagoToronto.add(rs4);
        routeSegments.put(71, chicagoToronto);

        // Sault St Marie to Montreal, id 72
        HashSet<RouteSegment> stMarieMontreal = new HashSet<>();
        rs1 = new RouteSegment(903, 122,
                933, 99, 72);
        stMarieMontreal.add(rs1);
        rs2 = new RouteSegment(940, 96,
                973, 80, 72);
        stMarieMontreal.add(rs2);
        rs3 = new RouteSegment(981, 77,
                1016, 66, 72);
        stMarieMontreal.add(rs3);
        rs4 = new RouteSegment(1025, 63,
                1063, 55, 72);
        stMarieMontreal.add(rs4);
        rs5 = new RouteSegment(1070, 55,
                1110, 54, 72);
        stMarieMontreal.add(rs5);
        routeSegments.put(72, stMarieMontreal);

        // Sault St Marie to Toronto, id 73
        HashSet<RouteSegment> stMarieToronto = new HashSet<>();
        rs1 = new RouteSegment(914, 140,
                956, 148, 73);
        stMarieToronto.add(rs1);
        rs2 = new RouteSegment(961, 148,
                1002, 157, 73);
        stMarieToronto.add(rs2);
        routeSegments.put(73, stMarieToronto);

        // Toronto to Montreal, id 74
        HashSet<RouteSegment> torontoMontreal = new HashSet<>();
        rs1 = new RouteSegment(1027, 148,
                1048, 117, 74);
        torontoMontreal.add(rs1);
        rs2 = new RouteSegment(1052, 110,
                1092, 88, 74);
        torontoMontreal.add(rs2);
        rs3 = new RouteSegment(1087, 83,
                1125, 70, 74);
        torontoMontreal.add(rs3);
        routeSegments.put(74, torontoMontreal);

        // Toronto to Pittsburgh, id 75
        HashSet<RouteSegment> torontoPittsburgh = new HashSet<>();
        rs1 = new RouteSegment(1041, 176,
                1045, 209, 75);
        torontoPittsburgh.add(rs1);
        rs2 = new RouteSegment(1045, 216,
                1045, 250, 75);
        torontoPittsburgh.add(rs2);
        routeSegments.put(75, torontoPittsburgh);

        // Montreal to Boston 1, id 76
        HashSet<RouteSegment> montrealBoston1 = new HashSet<>();
        rs1 = new RouteSegment(1147, 73, 1175, 96,
                76);
        rs2 = new RouteSegment(1184, 97, 1212, 122,
                76);
        montrealBoston1.add(rs1);
        montrealBoston1.add(rs2);
        routeSegments.put(76, montrealBoston1);

        // Montreal to Boston 2, id 77
        HashSet<RouteSegment> montrealBoston2 = new HashSet<>();
        rs1 = new RouteSegment(1156, 62, 1186, 84,
                77);
        rs2 = new RouteSegment(1193, 88, 1223, 110,
                77);
        montrealBoston2.add(rs1);
        montrealBoston2.add(rs2);
        routeSegments.put(77, montrealBoston2);

        // Montreal to New York, id 78
        HashSet<RouteSegment> montrealNewYork = new HashSet<>();
        rs1 = new RouteSegment(1131, 81,
                1139, 118, 78);
        montrealNewYork.add(rs1);
        rs2 = new RouteSegment(1141, 125,
                1146, 160, 78);
        montrealNewYork.add(rs2);
        rs3 = new RouteSegment(1147, 165,
                1152, 199, 78);
        montrealNewYork.add(rs3);
        routeSegments.put(78, montrealNewYork);


        // Boston to New York 1, id 79
        HashSet<RouteSegment> bostonDC1 = new HashSet<>();
        rs1 = new RouteSegment(1171, 203, 1192, 174,
                79);
        rs2 = new RouteSegment(1197, 167, 1218, 140,
                79);
        bostonDC1.add(rs1);
        bostonDC1.add(rs2);
        routeSegments.put(79, bostonDC1);

        // Boston to New York 2, id 80
        HashSet<RouteSegment> bostonDC2 = new HashSet<>();
        rs1 = new RouteSegment(1185, 210, 1204, 181,
                80);
        rs2 = new RouteSegment(1208, 174, 1229, 145,
                80);
        bostonDC2.add(rs1);
        bostonDC2.add(rs2);
        routeSegments.put(80, bostonDC2);

        // Pittsburgh to New York 1, id 81
        HashSet<RouteSegment> pittsNYC1 = new HashSet<>();
        rs1 = new RouteSegment(1064, 249, 1097, 232,
                81);
        rs2 = new RouteSegment(1105, 228, 1137, 210,
                81);
        pittsNYC1.add(rs1);
        pittsNYC1.add(rs2);
        routeSegments.put(81, pittsNYC1);

        // Pittsburgh to New York 2, id 82
        HashSet<RouteSegment> pittsNYC2 = new HashSet<>();
        rs1 = new RouteSegment(1073, 262, 1104, 245,
                82);
        rs2 = new RouteSegment(1114, 240, 1144, 222,
                82);
        pittsNYC2.add(rs1);
        pittsNYC2.add(rs2);
        routeSegments.put(82, pittsNYC2);

        // Pittsburgh to Washington, id 83
        HashSet<RouteSegment> pittsWashington = new HashSet<>();
        rs1 = new RouteSegment(1077, 279, 1112, 298,
                83);
        rs2 = new RouteSegment(1118, 300, 1155, 317,
                83);
        pittsWashington.add(rs1);
        pittsWashington.add(rs2);
        routeSegments.put(83, pittsWashington);

        // Pittsburgh to Raleigh, id 84
        HashSet<RouteSegment> pittsRaleigh = new HashSet<>();
        rs1 = new RouteSegment(1069, 298, 1074, 331,
                84);
        rs2 = new RouteSegment(1078, 336, 1086, 372,
                84);
        pittsRaleigh.add(rs1);
        pittsRaleigh.add(rs2);
        routeSegments.put(84, pittsRaleigh);

        // New York to Washington 1, id 85
        HashSet<RouteSegment> newYorkWashington1 = new HashSet<>();
        rs1 = new RouteSegment(1166, 304, 1163, 270,
                85);
        rs2 = new RouteSegment(1163, 262, 1160, 230,
                85);
        newYorkWashington1.add(rs1);
        newYorkWashington1.add(rs2);
        routeSegments.put(85, newYorkWashington1);

        // New York to Washington 2, id 86
        HashSet<RouteSegment> newYorkWashington2 = new HashSet<>();
        rs1 = new RouteSegment(1179, 303, 1178, 271,
                86);
        rs2 = new RouteSegment(1178, 263, 1175,229,
                86);
        newYorkWashington2.add(rs1);
        newYorkWashington2.add(rs2);
        routeSegments.put(86, newYorkWashington2);

        // Nashville to Pittsburgh, id 87
        HashSet<RouteSegment> nashvillePitt = new HashSet<>();
        rs1 = new RouteSegment(933, 418, 954, 382,
                87);
        rs2 = new RouteSegment(958, 378, 981, 348,
                87);
        rs3 = new RouteSegment(988, 345, 1023, 327,
                87);
        rs4 = new RouteSegment(1027, 322, 1049, 293,
                87);
        nashvillePitt.add(rs1);
        nashvillePitt.add(rs2);
        nashvillePitt.add(rs3);
        nashvillePitt.add(rs4);
        routeSegments.put(87, nashvillePitt);

        // Nashville to Raleigh, id 88
        HashSet<RouteSegment> nashvilleRaleigh = new HashSet<>();
        rs1 = new RouteSegment(959, 417, 993, 400,
                88);
        rs2 = new RouteSegment(999, 395, 1040, 388,
                88);
        rs3 = new RouteSegment(1045, 387, 1087, 388,
                88);
        nashvilleRaleigh.add(rs1);
        nashvilleRaleigh.add(rs2);
        nashvilleRaleigh.add(rs3);
        routeSegments.put(88, nashvilleRaleigh);

        // Nashville to Atlanta, id 89
        HashSet<RouteSegment> nashvilleAtlanta = new HashSet<>();
        rs1 = new RouteSegment(961, 435, 995, 458,
                89);
        nashvilleAtlanta.add(rs1);
        routeSegments.put(89, nashvilleAtlanta);

        // New Orleans to Atlanta 1, id 90
        HashSet<RouteSegment> newOrleansAtlanta1 = new HashSet<>();
        rs1 = new RouteSegment(892, 596, 907, 565,
                90);
        rs2 = new RouteSegment(909, 558, 931, 529,
                90);
        rs3 = new RouteSegment(937, 523, 962, 497,
                90);
        rs4 = new RouteSegment(966, 492, 996, 469,
                90);
        newOrleansAtlanta1.add(rs1);
        newOrleansAtlanta1.add(rs2);
        newOrleansAtlanta1.add(rs3);
        newOrleansAtlanta1.add(rs4);
        routeSegments.put(90, newOrleansAtlanta1);

        // New Orleans to Atlanta 2, id 91
        HashSet<RouteSegment> newOrleansAtlanta2 = new HashSet<>();
        rs1 = new RouteSegment(905, 607, 915, 576,
                91);
        rs2 = new RouteSegment(922, 569, 942, 540,
                91);
        rs3 = new RouteSegment(948, 534, 971, 508,
                91);
        rs4 = new RouteSegment(978, 502, 1007, 480,
                91);
        newOrleansAtlanta2.add(rs1);
        newOrleansAtlanta2.add(rs2);
        newOrleansAtlanta2.add(rs3);
        newOrleansAtlanta2.add(rs4);
        routeSegments.put(91, newOrleansAtlanta2);

        // New Orleans to Miami, id 92
        HashSet<RouteSegment> newOrleansMiami = new HashSet<>();
        rs1 = new RouteSegment(918, 615,
                951, 597, 92);
        newOrleansMiami.add(rs1);
        rs2 = new RouteSegment(960, 596,
                996, 584, 92);
        newOrleansMiami.add(rs2);
        rs3 = new RouteSegment(1005, 584,
                1044, 585, 92);
        newOrleansMiami.add(rs3);
        rs4 = new RouteSegment(1053, 588,
                1088, 604, 92);
        newOrleansMiami.add(rs4);
        rs5 = new RouteSegment(1094, 608,
                1124, 631, 92);
        newOrleansMiami.add(rs5);
        rs6 = new RouteSegment(1129, 634,
                1154, 665, 92);
        newOrleansMiami.add(rs6);
        routeSegments.put(92, newOrleansMiami);

        // Atlanta to Miami, id 93
        HashSet<RouteSegment> atlantaMiami = new HashSet<>();
        rs1 = new RouteSegment(1022, 487,
                1045, 517, 93);
        atlantaMiami.add(rs1);
        rs2 = new RouteSegment(1050, 520,
                1075, 550, 93);
        atlantaMiami.add(rs2);
        rs3 = new RouteSegment(1078, 553,
                1105, 583, 93);
        atlantaMiami.add(rs3);
        rs4 = new RouteSegment(1106, 586,
                1133, 614, 93);
        atlantaMiami.add(rs4);
        rs5 = new RouteSegment(1135, 620,
                1162, 646, 93);
        atlantaMiami.add(rs5);
        routeSegments.put(93, atlantaMiami);

        // Atlanta to Raleigh 1, id 94
        HashSet<RouteSegment> atlantaRaleigh1 = new HashSet<>();
        rs1 = new RouteSegment(1016, 451, 1045, 429,
                94);
        rs2 = new RouteSegment(1053, 423, 1081, 402,
                94);
        atlantaRaleigh1.add(rs1);
        atlantaRaleigh1.add(rs2);
        routeSegments.put(94, atlantaRaleigh1);

        // Atlanta to Raleigh 2, id 95
        HashSet<RouteSegment> atlantaRaleigh2 = new HashSet<>();
        rs1 = new RouteSegment(1027, 462, 1056, 440,
                95);
        rs2 = new RouteSegment(1063, 433, 1092, 414,
                95);
        atlantaRaleigh2.add(rs1);
        atlantaRaleigh2.add(rs2);
        routeSegments.put(95, atlantaRaleigh2);

        // Atlanta to Charleston, id 96
        HashSet<RouteSegment> atlantaCharleston = new HashSet<>();
        rs1 = new RouteSegment(1034, 480,
                1074, 480, 96);
        atlantaCharleston.add(rs1);
        rs2 = new RouteSegment(1080, 480,
                1121, 483, 96);
        atlantaCharleston.add(rs2);
        routeSegments.put(96, atlantaCharleston);

        // Charleston to Miami, id 97
        HashSet<RouteSegment> charlestonMiami = new HashSet<>();
        rs1 = new RouteSegment(1133, 486,
                1136, 521, 97);
        charlestonMiami.add(rs1);
        rs2 = new RouteSegment(1136, 530,
                1142, 564, 97);
        charlestonMiami.add(rs2);
        rs3 = new RouteSegment(1144, 571,
                1156, 605, 97);
        charlestonMiami.add(rs3);
        rs4 = new RouteSegment(1160, 611,
                1180, 642, 97);
        charlestonMiami.add(rs4);
        routeSegments.put(97, charlestonMiami);

        // Raleigh to Charleston, id 98
        HashSet<RouteSegment> raleighCharleston = new HashSet<>();
        rs1 = new RouteSegment(1109, 410, 1143, 431,
                98);
        rs2 = new RouteSegment(1156, 431, 1139, 494,
                98);
        raleighCharleston.add(rs1);
        raleighCharleston.add(rs2);
        routeSegments.put(98, raleighCharleston);

        // Raleigh to Washington 1, id 99
        HashSet<RouteSegment> raleighWashington1 = new HashSet<>();
        rs1 = new RouteSegment(1103, 384, 1125, 358,
                99);
        rs2 = new RouteSegment(1131, 351, 1156, 326,
                99);
        raleighWashington1.add(rs1);
        raleighWashington1.add(rs2);
        routeSegments.put(99, raleighWashington1);

        // Raleigh to Washington 2, id 100
        HashSet<RouteSegment> raleighWashington2 = new HashSet<>();
        rs1 = new RouteSegment(1115, 393, 1138, 366,
                100);
        rs2 = new RouteSegment(1145, 358, 1170, 336,
                100);
        raleighWashington2.add(rs1);
        raleighWashington2.add(rs2);
        routeSegments.put(100, raleighWashington2);
    }

}
