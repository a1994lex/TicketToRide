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

    public static HashMap<String, Route> getClaimedRoutesMap() {
        return single_instance._getRoutesMap();
    }

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

    private HashMap<String, Route> _getRoutesMap() {
        return clientGame.getRoutesMap();
    }

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
        routes.add(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 1));
        routes.add(new Route(1, "SEATTLE", "PORTLAND", 1, "", 2));
        routes.add(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, "green", 3));
        routes.add(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, "pink", 4));
        routes.add(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, "yellow", 5));
        routes.add(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, "pink", 6));
        routes.add(new Route(3, "VANCOUVER", "CALGARY", 4, "", 7));
        routes.add(new Route(4, "SEATTLE", "CALGARY", 7, "", 8));
        routes.add(new Route(6, "SEATTLE", "HELENA", 15, "yellow", 9));
        routes.add(new Route(6, "PORTLAND", "SALT LAKE CITY", 15, "blue", 10));
        routes.add(new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, "orange", 11));
        routes.add(new Route(5, "SAN FRANCISCO", "SALT LAKE CITY", 10, "white", 12));
        routes.add(new Route(2, "LOS ANGELES", "LAS VEGAS", 2, "", 13));
        routes.add(new Route(3, "LOS ANGELES", "PHOENIX", 4, "", 14));
        routes.add(new Route(6, "LOS ANGELES", "EL PASO", 15, "black", 15));
        routes.add(new Route(6, "CALGARY", "WINNIPEG", 15, "white", 16));
        routes.add(new Route(4, "CALGARY", "HELENA", 7, "", 17));
        routes.add(new Route(3, "SALT LAKE CITY", "HELENA", 4, "pink", 18));
        routes.add(new Route(3, "SALT LAKE CITY", "DENVER", 4, "red", 19));
        routes.add(new Route(3, "SALT LAKE CITY", "DENVER", 4, "yellow", 20));
        routes.add(new Route(3, "LAS VEGAS", "SALT LAKE CITY", 4, "orange", 21));
        routes.add(new Route(5, "PHOENIX", "DENVER", 10, "white", 22));
        routes.add(new Route(3, "PHOENIX", "SANTA FE", 4, "", 23));
        routes.add(new Route(3, "PHOENIX", "EL PASO", 4, "", 24));
        routes.add(new Route(4, "HELENA", "WINNIPEG", 7, "blue", 25));
        routes.add(new Route(6, "HELENA", "DULUTH", 15, "orange", 26));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, "red", 27));
        routes.add(new Route(4, "HELENA", "DENVER", 7, "green", 28));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(4, "DENVER", "OMAHA", 7, "pink", 30));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "black", 31));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "orange", 32));
        routes.add(new Route(4, "DENVER", "OKLAHOMA CITY", 7, "red", 33));
        routes.add(new Route(3, "SANTA FE", "OKLAHOMA CITY", 4, "blue", 34));
        routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 35));
        routes.add(new Route(5, "EL PASO", "OKLAHOMA CITY", 10, "yellow", 36));
        routes.add(new Route(4, "EL PASO", "DALLAS", 7, "red", 37));
        routes.add(new Route(6, "EL PASO", "HOUSTON", 15, "green", 38));
        routes.add(new Route(4, "WINNIPEG", "DULUTH", 7, "black", 39));
        routes.add(new Route(6, "WINNIPEG", "SAULT ST. MARIE", 15, "", 40));
        routes.add(new Route(3, "DULUTH", "SAULT ST. MARIE", 4, "", 41));
        routes.add(new Route(6, "DULUTH", "TORONTO", 15, "pink", 42));
        routes.add(new Route(3, "DULUTH", "CHICAGO", 4, "red", 43));
        routes.add(new Route(2, "DULUTH", "OMAHA", 2, "", 44));
        routes.add(new Route(4, "OMAHA", "CHICAGO", 7, "blue", 45));
        routes.add(new Route(1, "OMAHA", "KANSAS CITY", 1, "", 46));
        routes.add(new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, "blue", 47));
        routes.add(new Route(2, "KANSAS CITY", "SAINT LOUIS", 2, "pink", 48));
        routes.add(new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 49));
        routes.add(new Route(2, "OKLAHOMA CITY", "LITTLE ROCK", 2, "", 50));
        routes.add(new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 51));
        routes.add(new Route(1, "DALLAS", "HOUSTON", 1, "", 52));
        routes.add(new Route(2, "DALLAS", "LITTLE ROCK", 2, "", 53));
        routes.add(new Route(2, "HOUSTON", "NEW ORLEANS", 2, "", 54));
        routes.add(new Route(3, "LITTLE ROCK", "NEW ORLEANS", 4, "green", 55));
        routes.add(new Route(3, "LITTLE ROCK", "NASHVILLE", 4, "white", 56));
        routes.add(new Route(2, "SAINT LOUIS", "LITTLE ROCK", 2, "", 57));
        routes.add(new Route(2, "SAINT LOUIS", "NASHVILLE", 2, "", 58));
        routes.add(new Route(5, "SAINT LOUIS", "PITTSBURGH", 10, "", 59));
        routes.add(new Route(2, "SAINT LOUIS", "CHICAGO", 2, "green", 60));
        routes.add(new Route(2, "SAINT LOUIS", "CHICAGO", 2, "white", 61));
        routes.add(new Route(3, "CHICAGO", "PITTSBURGH", 4, "orange", 62));
        routes.add(new Route(3, "CHICAGO", "PITTSBURGH", 4, "black", 63));
        routes.add(new Route(4, "CHICAGO", "TORONTO", 7, "white", 64));
        routes.add(new Route(5, "SAULT ST. MARIE", "MONTREAL", 10, "black", 65));
        routes.add(new Route(2, "SAULT ST. MARIE", "TORONTO", 2, "", 66));
        routes.add(new Route(3, "TORONTO", "MONTREAL", 4, "", 67));
        routes.add(new Route(2, "TORONTO", "PITTSBURGH", 2, "", 68));
        routes.add(new Route(2, "MONTREAL", "BOSTON", 2, "", 69));
        routes.add(new Route(3, "MONTREAL", "NEW YORK", 4, "blue", 70));
        routes.add(new Route(2, "BOSTON", "NEW YORK", 2, "yellow", 71));
        routes.add(new Route(2, "BOSTON", "NEW YORK", 2, "red", 72));
        routes.add(new Route(2, "PITTSBURGH", "NEW YORK", 2, "white", 73));
        routes.add(new Route(2, "PITTSBURGH", "NEW YORK", 2, "green", 74));
        routes.add(new Route(2, "NEW YORK", "WASHINGTON", 2, "orange", 75));
        routes.add(new Route(2, "NEW YORK", "WASHINGTON", 2, "black", 76));
        routes.add(new Route(2, "PITTSBURGH", "WASHINGTON", 2, "", 77));
        routes.add(new Route(4, "NASHVILLE", "PITTSBURGH", 7, "yellow", 78));
        routes.add(new Route(3, "NASHVILLE", "RALEIGH", 4, "black", 79));
        routes.add(new Route(1, "NASHVILLE", "ATLANTA", 1, "", 80));
        routes.add(new Route(4, "NEW ORLEANS", "ATLANTA", 7, "yellow", 81));
        routes.add(new Route(4, "NEW ORLEANS", "ATLANTA", 7, "orange", 82));
        routes.add(new Route(6, "NEW ORLEANS", "MIAMI", 15, "red", 83));
        routes.add(new Route(5, "ATLANTA", "MIAMI", 10, "blue", 84));
        routes.add(new Route(2, "ATLANTA", "RALEIGH", 2, "", 85));
        routes.add(new Route(2, "ATLANTA", "CHARLESTON", 2, "", 86));
        routes.add(new Route(4, "CHARLESTON", "MIAMI", 7, "pink", 87));
        routes.add(new Route(2, "RALEIGH", "CHARLESTON", 2, "", 88));
        routes.add(new Route(2, "PITTSBURGH", "RALEIGH", 2, "", 89));
        routes.add(new Route(2, "RALEIGH", "WASHINGTON", 2, "", 90));
        routes.add(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 91));
        routes.add(new Route(1, "SEATTLE", "PORTLAND", 1, "", 92));
        routes.add(new Route(2, "DULUTH", "OMAHA", 2, "", 93));
        routes.add(new Route(1, "OMAHA", "KANSAS CITY", 1, "", 94));
        routes.add(new Route(2, "KANSAS CITY", "OKLAHOMA CITY", 2, "", 95));
        routes.add(new Route(2, "OKLAHOMA CITY", "DALLAS", 2, "", 96));
        routes.add(new Route(1, "DALLAS", "HOUSTON", 1, "", 97));
        routes.add(new Route(2, "ATLANTA", "RALEIGH", 2, "", 98));
        routes.add(new Route(2, "RALEIGH", "WASHINGTON", 2, "", 99));
        routes.add(new Route(2, "MONTREAL", "BOSTON", 2, "", 100));
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

        // Vancouver to Seattle, id 1
        HashSet<RouteSegment> vancouverSeattle1 = new HashSet<>();
        RouteSegment rs1 = new RouteSegment(112, 102,
                111, 134, 1);
        vancouverSeattle1.add(rs1);
        routeSegments.put(1, vancouverSeattle1);

        RouteSegment rs2;
        RouteSegment rs3;
        RouteSegment rs4;
        RouteSegment rs5;
        RouteSegment rs6;

        // Vancouver to Seattle, id 91
        HashSet<RouteSegment> vancouverSeattle2 = new HashSet<>();
        rs1 = new RouteSegment(127, 99, 128,
                133, 91);
        vancouverSeattle2.add(rs1);
        routeSegments.put(91, vancouverSeattle2);

        // Seattle to Portland, id 2
        HashSet<RouteSegment> seattlePortland1 = new HashSet<>();
        rs1 = new RouteSegment(102, 159, 89,
                191, 2);
        seattlePortland1.add(rs1);
        routeSegments.put(2, seattlePortland1);

        // Seattle to Portland, id 92
        HashSet<RouteSegment> seattlePortland2 = new HashSet<>();
        rs1 = new RouteSegment(119, 165, 104,
                196, 92);
        seattlePortland2.add(rs1);
        routeSegments.put(92, seattlePortland2);

        // San Francisco to Los Angeles, id 5
        HashSet<RouteSegment> SFtoLA1 = new HashSet<>();
        rs1 = new RouteSegment(75, 456, 90, 489,
                5);
        rs2 = new RouteSegment(95, 493, 117, 522,
                5);
        rs3 = new RouteSegment(121, 529, 149, 552,
                5);
        SFtoLA1.add(rs1);
        SFtoLA1.add(rs2);
        SFtoLA1.add(rs3);
        routeSegments.put(5, SFtoLA1);

        // SF to LA, id 6
        HashSet<RouteSegment> SFtoLA2 = new HashSet<>();
        rs1 = new RouteSegment(87, 447, 104, 480,
                6);
        rs2 = new RouteSegment(108, 485, 130, 514,
                6);
        rs3 = new RouteSegment(135, 519, 161, 543,
                6);
        SFtoLA2.add(rs1);
        SFtoLA2.add(rs2);
        SFtoLA2.add(rs3);
        routeSegments.put(6, SFtoLA2);

        // SLC to Denver1, id 19
        HashSet<RouteSegment> SLCtoDenver1 = new HashSet<>();
        rs1 = new RouteSegment(345, 356, 383, 363,
                19);
        rs2 = new RouteSegment(391, 363, 430, 369,
                19);
        rs3 = new RouteSegment(437, 371, 471, 378,
                19);
        SLCtoDenver1.add(rs1);
        SLCtoDenver1.add(rs2);
        SLCtoDenver1.add(rs3);
        routeSegments.put(19, SLCtoDenver1);

        // SLC to Denver 2, id 20
        HashSet<RouteSegment> SLCtoDenver2 = new HashSet<>();
        rs1 = new RouteSegment(343, 371, 382, 377,
                20);
        rs2 = new RouteSegment(389, 378, 428, 384,
                20);
        rs3 = new RouteSegment(435, 385, 471, 392,
                20);
        SLCtoDenver2.add(rs1);
        SLCtoDenver2.add(rs2);
        SLCtoDenver2.add(rs3);
        routeSegments.put(20, SLCtoDenver2);

        // Denver to Kansas City 1, id 31
        HashSet<RouteSegment> denverKansasCity1 = new HashSet<>();
        rs1 = new RouteSegment(520, 396, 559, 399,
                31);
        rs2 = new RouteSegment(567, 400, 604, 396,
                31);
        rs3 = new RouteSegment(613, 396, 650, 389,
                31);
        rs4 = new RouteSegment(660, 386, 693, 374,
                31);
        denverKansasCity1.add(rs1);
        denverKansasCity1.add(rs2);
        denverKansasCity1.add(rs3);
        denverKansasCity1.add(rs4);
        routeSegments.put(31, denverKansasCity1);

        // Denver to Kansas City 2, id 32
        HashSet<RouteSegment> denverKansasCity2 = new HashSet<>();
        rs1 = new RouteSegment(520, 412, 559, 415,
                32);
        rs2 = new RouteSegment(567, 415, 604, 412,
                32);
        rs3 = new RouteSegment(613, 411, 650, 406,
                32);
        rs4 = new RouteSegment(660, 403, 693, 389,
                32);
        denverKansasCity2.add(rs1);
        denverKansasCity2.add(rs2);
        denverKansasCity2.add(rs3);
        denverKansasCity2.add(rs4);
        routeSegments.put(32, denverKansasCity2);

        // Duluth to Omaha 1, id 44
        HashSet<RouteSegment> duluthOmaha1 = new HashSet<>();
        rs1 = new RouteSegment(711, 229, 698, 259,
                44);
        rs2 = new RouteSegment(694, 266, 681, 299,
                44);
        duluthOmaha1.add(rs1);
        duluthOmaha1.add(rs2);
        routeSegments.put(44, duluthOmaha1);

        // Duluth to Omaha 2, id 93
        HashSet<RouteSegment> duluthOmaha2 = new HashSet<>();
        rs1 = new RouteSegment(726, 234, 715, 265,
                93);
        rs2 = new RouteSegment(711, 273, 697, 304,
                93);
        duluthOmaha2.add(rs1);
        duluthOmaha2.add(rs2);
        routeSegments.put(93, duluthOmaha2);

        // Omaha to Kansas City 1, id 46
        HashSet<RouteSegment> omahaKansasCity1 = new HashSet<>();
        rs1 = new RouteSegment(689, 337, 704, 367,
                46);
        omahaKansasCity1.add(rs1);
        routeSegments.put(46, omahaKansasCity1);

        // Omaha to Kansas City 2, id 94
        HashSet<RouteSegment> omahaKansasCity2 = new HashSet<>();
        rs1 = new RouteSegment(704, 329, 719, 359,
                94);
        omahaKansasCity2.add(rs1);
        routeSegments.put(94, omahaKansasCity2);

        // Kansas City to OKC 1, id 49
        HashSet<RouteSegment> kansasCityOKC1 = new HashSet<>();
        rs1 = new RouteSegment(709, 392, 698, 423,
                49);
        rs2 = new RouteSegment(696, 430, 687, 463,
                49);
        kansasCityOKC1.add(rs1);
        kansasCityOKC1.add(rs2);
        routeSegments.put(49, kansasCityOKC1);

        // Kansas City to OKC 2, id 95
        HashSet<RouteSegment> kansasCityOKC2 = new HashSet<>();
        rs1 = new RouteSegment(726, 395, 715, 428,
                95);
        rs2 = new RouteSegment(711, 434, 703, 467,
                95);
        kansasCityOKC2.add(rs1);
        kansasCityOKC2.add(rs2);
        routeSegments.put(95, kansasCityOKC2);

        // OKC to Dallas 1, id 51
        HashSet<RouteSegment> okcDallas1 = new HashSet<>();
        rs1 = new RouteSegment(693, 493, 697, 528,
                51);
        rs2 = new RouteSegment(698, 534, 703, 569,
                51);
        okcDallas1.add(rs1);
        okcDallas1.add(rs2);
        routeSegments.put(51, okcDallas1);

        // OKC to Dallas, id 96
        HashSet<RouteSegment> okcDallas2 = new HashSet<>();
        rs1 = new RouteSegment(708, 491, 712, 523,
                96);
        rs2 = new RouteSegment(715, 530, 719, 566,
                96);
        okcDallas2.add(rs1);
        okcDallas2.add(rs2);
        routeSegments.put(96, okcDallas2);

        // Dallas to Houston 1, id 52
        HashSet<RouteSegment> dallasHouston1 = new HashSet<>();
        rs1 = new RouteSegment(722, 595, 746, 619,
                52);
        dallasHouston1.add(rs1);
        routeSegments.put(52, dallasHouston1);

        // Dallas to Houston 2, id 97
        HashSet<RouteSegment> dallasHouston2 = new HashSet<>();
        rs1 = new RouteSegment(734, 585, 757, 610,
                97);
        dallasHouston2.add(rs1);
        routeSegments.put(97, dallasHouston2);

        // Kansas to St Louis 1, id 47
        HashSet<RouteSegment> kansasStLouis1 = new HashSet<>();
        rs1 = new RouteSegment(729, 369, 764, 367,
                47);
        rs2 = new RouteSegment(774, 369, 811, 366,
                47);
        kansasStLouis1.add(rs1);
        kansasStLouis1.add(rs2);
        routeSegments.put(47, kansasStLouis1);

        // Kansas to St Louis 2, id 48
        HashSet<RouteSegment> kansasStLouis2 = new HashSet<>();
        rs1 = new RouteSegment(727, 384, 766, 382,
                48);
        rs2 = new RouteSegment(772, 384, 811, 381,
                48);
        kansasStLouis2.add(rs1);
        kansasStLouis2.add(rs2);
        routeSegments.put(48, kansasStLouis2);

        // St Louis to Chicago 1, id 60
        HashSet<RouteSegment> STLChicago1 = new HashSet<>();
        rs1 = new RouteSegment(820, 359, 838, 330,
                60);
        rs2 = new RouteSegment(844, 323, 863, 296,
                60);
        STLChicago1.add(rs1);
        STLChicago1.add(rs2);
        routeSegments.put(60, STLChicago1);

        // St Louis to Chicago 2, id 61
        HashSet<RouteSegment> STLChicago2 = new HashSet<>();
        rs1 = new RouteSegment(833, 366, 853, 339,
                61);
        rs2 = new RouteSegment(857, 332, 878, 303,
                61);
        STLChicago2.add(rs1);
        STLChicago2.add(rs2);
        routeSegments.put(61, STLChicago2);

        // Chicago to Pittsburgh 1, id 62
        HashSet<RouteSegment> chicagoPittsburgh1 = new HashSet<>();
        rs1 = new RouteSegment(896, 265, 933, 255,
                62);
        rs2 = new RouteSegment(941, 255, 979, 248,
                62);
        rs3 = new RouteSegment(988, 247, 1026, 251,
                62);
        chicagoPittsburgh1.add(rs1);
        chicagoPittsburgh1.add(rs2);
        chicagoPittsburgh1.add(rs3);
        routeSegments.put(62, chicagoPittsburgh1);

        // Chicago to Pittsburgh 2, id 63
        HashSet<RouteSegment> chicagoPittsburgh2 = new HashSet<>();
        rs1 = new RouteSegment(903, 281, 938, 271,
                63);
        rs2 = new RouteSegment(948, 270, 985, 265,
                63);
        rs3 = new RouteSegment(994, 265, 1031, 266,
                63);
        chicagoPittsburgh2.add(rs1);
        chicagoPittsburgh2.add(rs2);
        chicagoPittsburgh2.add(rs3);
        routeSegments.put(63, chicagoPittsburgh2);

        // Pitts to NYC 1, id 73
        HashSet<RouteSegment> pittsNYC1 = new HashSet<>();
        rs1 = new RouteSegment(1064, 249, 1097, 232,
                73);
        rs2 = new RouteSegment(1105, 228, 1137, 210,
                73);
        pittsNYC1.add(rs1);
        pittsNYC1.add(rs2);
        routeSegments.put(73, pittsNYC1);

        // Pitts to NYC 2, id 74
        HashSet<RouteSegment> pittsNYC2 = new HashSet<>();
        rs1 = new RouteSegment(1073, 262, 1104, 245,
                74);
        rs2 = new RouteSegment(1114, 240, 1144, 222,
                74);
        pittsNYC2.add(rs1);
        pittsNYC2.add(rs2);
        routeSegments.put(74, pittsNYC2);

        // New Orleans to Atlanta 1, id 81
        HashSet<RouteSegment> newOrleansAtlanta1 = new HashSet<>();
        rs1 = new RouteSegment(892, 596, 907, 565,
                81);
        rs2 = new RouteSegment(909, 558, 931, 529,
                81);
        rs3 = new RouteSegment(937, 523, 962, 497,
                81);
        rs4 = new RouteSegment(966, 492, 996, 469,
                81);
        newOrleansAtlanta1.add(rs1);
        newOrleansAtlanta1.add(rs2);
        newOrleansAtlanta1.add(rs3);
        newOrleansAtlanta1.add(rs4);
        routeSegments.put(81, newOrleansAtlanta1);

        // New Orleans to Atlanta 2, id 82
        HashSet<RouteSegment> newOrleansAtlanta2 = new HashSet<>();
        rs1 = new RouteSegment(905, 607, 915, 576,
                82);
        rs2 = new RouteSegment(922, 569, 942, 540,
                82);
        rs3 = new RouteSegment(948, 534, 971, 508,
                82);
        rs4 = new RouteSegment(978, 502, 1007, 480,
                82);
        newOrleansAtlanta2.add(rs1);
        newOrleansAtlanta2.add(rs2);
        newOrleansAtlanta2.add(rs3);
        newOrleansAtlanta2.add(rs4);
        routeSegments.put(82, newOrleansAtlanta2);

        // Atlanta to Raleigh 1, id 85
        HashSet<RouteSegment> atlantaRaleigh1 = new HashSet<>();
        rs1 = new RouteSegment(1016, 451, 1045, 429,
                85);
        rs2 = new RouteSegment(1053, 423, 1081, 402,
                85);
        atlantaRaleigh1.add(rs1);
        atlantaRaleigh1.add(rs2);
        routeSegments.put(85, atlantaRaleigh1);

        // Atlanta to Raleigh 2, id 98
        HashSet<RouteSegment> atlantaRaleigh2 = new HashSet<>();
        rs1 = new RouteSegment(1027, 462, 1056, 440,
                98);
        rs2 = new RouteSegment(1063, 433, 1092, 414,
                98);
        atlantaRaleigh2.add(rs1);
        atlantaRaleigh2.add(rs2);
        routeSegments.put(98, atlantaRaleigh2);

        // Raleigh to DC 1, id 90
        HashSet<RouteSegment> raleighDC1 = new HashSet<>();
        rs1 = new RouteSegment(1103, 384, 1125, 358,
                90);
        rs2 = new RouteSegment(1131, 351, 1156, 326,
                90);
        raleighDC1.add(rs1);
        raleighDC1.add(rs2);
        routeSegments.put(90, raleighDC1);

        // Raleigh to DC 2, id 99
        HashSet<RouteSegment> raleighDC2 = new HashSet<>();
        rs1 = new RouteSegment(1115, 393, 1138, 366,
                99);
        rs2 = new RouteSegment(1145, 358, 1170, 336,
                99);
        raleighDC2.add(rs1);
        raleighDC2.add(rs2);
        routeSegments.put(99, raleighDC2);

        // DC to NYC 1, id 75
        HashSet<RouteSegment> dcNYC1 = new HashSet<>();
        rs1 = new RouteSegment(1166, 304, 1163, 270,
                75);
        rs2 = new RouteSegment(1163, 262, 1160, 230,
                75);
        dcNYC1.add(rs1);
        dcNYC1.add(rs2);
        routeSegments.put(75, dcNYC1);

        // DC to NYC 2, id 76
        HashSet<RouteSegment> dcNYC2 = new HashSet<>();
        rs1 = new RouteSegment(1179, 303, 1178, 271,
                76);
        rs2 = new RouteSegment(1178, 263, 1175,229,
                76);
        dcNYC2.add(rs1);
        dcNYC2.add(rs2);
        routeSegments.put(76, dcNYC2);

        // Boston to DC 1, id 71
        HashSet<RouteSegment> bostonDC1 = new HashSet<>();
        rs1 = new RouteSegment(1171, 203, 1192, 174,
                71);
        rs2 = new RouteSegment(1197, 167, 1218, 140,
                71);
        bostonDC1.add(rs1);
        bostonDC1.add(rs2);
        routeSegments.put(71, bostonDC1);

        // Boston to DC 2, id 72
        HashSet<RouteSegment> bostonDC2 = new HashSet<>();
        rs1 = new RouteSegment(1185, 210, 1204, 181,
                72);
        rs2 = new RouteSegment(1208, 174, 1229, 145,
                72);
        bostonDC2.add(rs1);
        bostonDC2.add(rs2);
        routeSegments.put(72, bostonDC2);

        // Boston to Montreal 1, id 69
        HashSet<RouteSegment> bostonMontreal1 = new HashSet<>();
        rs1 = new RouteSegment(1147, 73, 1175, 96,
                69);
        rs2 = new RouteSegment(1184, 97, 1212, 122,
                69);
        bostonMontreal1.add(rs1);
        bostonMontreal1.add(rs2);
        routeSegments.put(69, bostonMontreal1);

        // Boston to Montreal 2, id 100
        HashSet<RouteSegment> bostonMontreal2 = new HashSet<>();
        rs1 = new RouteSegment(1156, 62, 1186, 84,
                100);
        rs2 = new RouteSegment(1193, 88, 1223, 110,
                100);
        bostonMontreal2.add(rs1);
        bostonMontreal2.add(rs2);
        routeSegments.put(100, bostonMontreal2);

        // Helena to Duluth, id 26
        HashSet<RouteSegment> helenaDuluth = new HashSet<>();
        rs1 = new RouteSegment(440, 220,
                480, 219, 26);
        helenaDuluth.add(rs1);
        rs2 = new RouteSegment(485, 219,
                526, 218, 26);
        helenaDuluth.add(rs2);
        rs3 = new RouteSegment(531, 218,
                572, 217, 26);
        helenaDuluth.add(rs3);
        rs4 = new RouteSegment(577, 217,
                618, 216, 26);
        helenaDuluth.add(rs4);
        rs5 = new RouteSegment(623, 215,
                664, 215, 26);
        helenaDuluth.add(rs5);
        rs6 = new RouteSegment(672, 215,
                713, 215, 26);
        helenaDuluth.add(rs6);
        routeSegments.put(26, helenaDuluth);

        // OKC to Little Rock, id 50
        HashSet<RouteSegment> okcLittleRock = new HashSet<>();
        rs1 = new RouteSegment(706, 481,
                746, 480, 50);
        okcLittleRock.add(rs1);
        rs2 = new RouteSegment(752, 480,
                792, 480, 50);
        okcLittleRock.add(rs2);
        routeSegments.put(50, okcLittleRock);

        // Atlanta to Charleston, id 86
        HashSet<RouteSegment> atlantaCharleston = new HashSet<>();
        rs1 = new RouteSegment(1034, 480,
                1074, 480, 86);
        atlantaCharleston.add(rs1);
        rs2 = new RouteSegment(1080, 480,
                1121, 483, 86);
        atlantaCharleston.add(rs2);
        routeSegments.put(86, atlantaCharleston);

        // Duluth to Toronto, id 42
        HashSet<RouteSegment> duluthToronto = new HashSet<>();
        rs1 = new RouteSegment(740, 212,
                780, 206, 42);
        duluthToronto.add(rs1);
        rs2 = new RouteSegment(786, 204,
                826, 198, 42);
        duluthToronto.add(rs2);
        rs3 = new RouteSegment(832, 196,
                872, 190, 42);
        duluthToronto.add(rs3);
        rs4 = new RouteSegment(878, 189,
                918, 183, 42);
        duluthToronto.add(rs4);
        rs5 = new RouteSegment(924, 182,
                964, 176, 42);
        duluthToronto.add(rs5);
        rs6 = new RouteSegment(970, 174,
                1010, 168, 42);
        duluthToronto.add(rs6);
        routeSegments.put(42, duluthToronto);

        // Atlanta to Miami, id 84
        HashSet<RouteSegment> atlantaMiami = new HashSet<>();
        rs1 = new RouteSegment(1022, 487,
                1045, 517, 84);
        atlantaMiami.add(rs1);
        rs2 = new RouteSegment(1050, 520,
                1075, 550, 84);
        atlantaMiami.add(rs2);
        rs3 = new RouteSegment(1078, 553,
                1105, 583, 84);
        atlantaMiami.add(rs3);
        rs4 = new RouteSegment(1106, 586,
                1133, 614, 84);
        atlantaMiami.add(rs4);
        rs5 = new RouteSegment(1135, 620,
                1162, 646, 84);
        atlantaMiami.add(rs5);
        routeSegments.put(84, atlantaMiami);

        // Seattle to Helena, id 9
        HashSet<RouteSegment> seattleHelena = new HashSet<>();
        rs1 = new RouteSegment(137, 166,
                174, 176, 9);
        seattleHelena.add(rs1);
        rs2 = new RouteSegment(180, 177,
                219, 182, 9);
        seattleHelena.add(rs2);
        rs3 = new RouteSegment(227, 185,
                265, 193, 9);
        seattleHelena.add(rs3);
        rs4 = new RouteSegment(272, 193,
                311, 200, 9);
        seattleHelena.add(rs4);
        rs5 = new RouteSegment(317, 202,
                356, 210, 9);
        seattleHelena.add(rs5);
        rs6 = new RouteSegment(363, 210,
                401, 219, 9);
        seattleHelena.add(rs6);
        routeSegments.put(9, seattleHelena);

        // LA to El Paso, id 15
        HashSet<RouteSegment> LAelPaso = new HashSet<>();
        rs1 = new RouteSegment(190, 569,
                219, 589, 15);
        LAelPaso.add(rs1);
        rs2 = new RouteSegment(230, 595,
                261, 608, 15);
        LAelPaso.add(rs2);
        rs3 = new RouteSegment(274, 610,
                308, 619, 15);
        LAelPaso.add(rs3);
        rs4 = new RouteSegment(317, 619,
                356, 626, 15);
        LAelPaso.add(rs4);
        rs5 = new RouteSegment(363, 626,
                401, 623, 15);
        LAelPaso.add(rs5);
        rs6 = new RouteSegment(407, 622,
                445, 614, 15);
        LAelPaso.add(rs6);
        routeSegments.put(15, LAelPaso);

        // El Paso to Houston, id 38
        HashSet<RouteSegment> elPasoHouston = new HashSet<>();
        rs1 = new RouteSegment(493, 621,
                523, 639, 38);
        elPasoHouston.add(rs1);
        rs2 = new RouteSegment(531, 639,
                570, 649, 38);
        elPasoHouston.add(rs2);
        rs3 = new RouteSegment(578, 652,
                613, 656, 38);
        elPasoHouston.add(rs3);
        rs4 = new RouteSegment(622, 656,
                661, 658, 38);
        elPasoHouston.add(rs4);
        rs4 = new RouteSegment(670, 658,
                705, 652, 38);
        elPasoHouston.add(rs4);
        rs5 = new RouteSegment(716, 649,
                751, 640, 38);
        elPasoHouston.add(rs5);
        routeSegments.put(38, elPasoHouston);

        // New Orleans to Miami, id 83
        HashSet<RouteSegment> newOrleansMiami = new HashSet<>();
        rs1 = new RouteSegment(918, 615,
                951, 597, 83);
        newOrleansMiami.add(rs1);
        rs2 = new RouteSegment(960, 596,
                996, 584, 83);
        newOrleansMiami.add(rs2);
        rs3 = new RouteSegment(1005, 584,
                1044, 585, 83);
        newOrleansMiami.add(rs3);
        rs4 = new RouteSegment(1053, 588,
                1088, 604, 83);
        newOrleansMiami.add(rs4);
        rs5 = new RouteSegment(1094, 608,
                1124, 631, 83);
        newOrleansMiami.add(rs5);
        rs6 = new RouteSegment(1129, 634,
                1154, 665, 83);
        newOrleansMiami.add(rs6);
        routeSegments.put(83, newOrleansMiami);

        // Portland to SLC, id 10
        HashSet<RouteSegment> portlandSLC = new HashSet<>();
        rs1 = new RouteSegment(109, 211,
                145, 218, 10);
        portlandSLC.add(rs1);
        rs2 = new RouteSegment(153, 219,
                190, 233, 10);
        portlandSLC.add(rs2);
        rs3 = new RouteSegment(198, 234,
                230, 254, 10);
        portlandSLC.add(rs3);
        rs4 = new RouteSegment(237, 258,
                265, 281, 10);
        portlandSLC.add(rs4);
        rs5 = new RouteSegment(272, 285,
                297, 312, 10);
        portlandSLC.add(rs5);
        rs6 = new RouteSegment(302, 317,
                319, 347, 10);
        portlandSLC.add(rs6);
        routeSegments.put(10, portlandSLC);

        // Calgary to Winnipeg, id 16
        HashSet<RouteSegment> calgaryWinnipeg = new HashSet<>();
        rs1 = new RouteSegment(309, 62,
                343, 47, 16);
        calgaryWinnipeg.add(rs1);
        rs2 = new RouteSegment(353, 47,
                390, 39, 16);
        calgaryWinnipeg.add(rs2);
        rs3 = new RouteSegment(398, 39,
                437, 37, 16);
        calgaryWinnipeg.add(rs3);
        rs4 = new RouteSegment(444, 35,
                483, 39, 16);
        calgaryWinnipeg.add(rs4);
        rs5 = new RouteSegment(490, 40,
                528, 49, 16);
        calgaryWinnipeg.add(rs5);
        rs6 = new RouteSegment(535, 52,
                571, 66, 16);
        calgaryWinnipeg.add(rs6);
        routeSegments.put(16, calgaryWinnipeg);

        // Charleston to Miami, id 87
        HashSet<RouteSegment> charlestonMiami = new HashSet<>();
        rs1 = new RouteSegment(1133, 486,
                1136, 521, 87);
        charlestonMiami.add(rs1);
        rs2 = new RouteSegment(1136, 530,
                1142, 564, 87);
        charlestonMiami.add(rs2);
        rs3 = new RouteSegment(1144, 571,
                1156, 605, 87);
        charlestonMiami.add(rs3);
        rs4 = new RouteSegment(1160, 611,
                1180, 642, 87);
        charlestonMiami.add(rs4);
        routeSegments.put(87, charlestonMiami);

        // Winnipeg to Sault St Marie, id 40
        HashSet<RouteSegment> winnipegStMarie = new HashSet<>();
        rs1 = new RouteSegment(607, 76,
                648, 84, 40);
        winnipegStMarie.add(rs1);
        rs2 = new RouteSegment(653, 84,
                692, 92, 40);
        winnipegStMarie.add(rs2);
        rs3 = new RouteSegment(698, 92,
                738, 99, 40);
        winnipegStMarie.add(rs3);
        rs4 = new RouteSegment(745, 100,
                782, 108, 40);
        winnipegStMarie.add(rs4);
        rs5 = new RouteSegment(790, 110,
                829, 118, 40);
        winnipegStMarie.add(rs5);
        rs6 = new RouteSegment(835, 119,
                872, 125, 40);
        winnipegStMarie.add(rs6);
        routeSegments.put(40, winnipegStMarie);

        // Sault St Marie to Montreal, id 65
        HashSet<RouteSegment> stMarieMontreal = new HashSet<>();
        rs1 = new RouteSegment(903, 122,
                933, 99, 65);
        stMarieMontreal.add(rs1);
        rs2 = new RouteSegment(940, 96,
                973, 80, 65);
        stMarieMontreal.add(rs2);
        rs3 = new RouteSegment(981, 77,
                1016, 66, 65);
        stMarieMontreal.add(rs3);
        rs4 = new RouteSegment(1025, 63,
                1063, 55, 65);
        stMarieMontreal.add(rs4);
        rs5 = new RouteSegment(1070, 55,
                1110, 54, 65);
        stMarieMontreal.add(rs5);
        routeSegments.put(65, stMarieMontreal);

        // Phoenix to Denver, id 22
        HashSet<RouteSegment> phoenixDenver = new HashSet<>();
        rs1 = new RouteSegment(334, 552,
                349, 521, 22);
        phoenixDenver.add(rs1);
        rs2 = new RouteSegment(352, 514,
                370, 484, 22);
        phoenixDenver.add(rs2);
        rs3 = new RouteSegment(374, 478,
                397, 449, 22);
        phoenixDenver.add(rs3);
        rs4 = new RouteSegment(404, 444,
                430, 423, 22);
        phoenixDenver.add(rs4);
        rs5 = new RouteSegment(439, 418,
                474, 406, 22);
        phoenixDenver.add(rs5);
        routeSegments.put(22, phoenixDenver);

        // Seattle to Calgary, id 8
        HashSet<RouteSegment> seattleCalgary = new HashSet<>();
        rs1 = new RouteSegment(137, 147,
                178, 147, 8);
        seattleCalgary.add(rs1);
        rs2 = new RouteSegment(185, 148,
                223, 143, 8);
        seattleCalgary.add(rs2);
        rs3 = new RouteSegment(231, 139,
                264, 118, 8);
        seattleCalgary.add(rs3);
        rs4 = new RouteSegment(269, 112,
                286, 80, 8);
        seattleCalgary.add(rs4);
        routeSegments.put(8, seattleCalgary);

        // Helena to Omaha, id 27
        HashSet<RouteSegment> helenaOmaha = new HashSet<>();
        rs1 = new RouteSegment(452, 233,
                487, 247, 27);
        helenaOmaha.add(rs1);
        rs2 = new RouteSegment(496, 248,
                531, 262, 27);
        helenaOmaha.add(rs2);
        rs3 = new RouteSegment(537, 265,
                574, 277, 27);
        helenaOmaha.add(rs3);
        rs4 = new RouteSegment(581, 280,
                618, 293, 27);
        helenaOmaha.add(rs4);
        rs5 = new RouteSegment(623, 295,
                659, 310, 27);
        helenaOmaha.add(rs5);
        routeSegments.put(27, helenaOmaha);

        // Omaha to Chicago, id 45
        HashSet<RouteSegment> omahaChicago = new HashSet<>();
        rs1 = new RouteSegment(704, 317,
                734, 297, 45);
        omahaChicago.add(rs1);
        rs2 = new RouteSegment(741, 293,
                772, 274, 45);
        omahaChicago.add(rs2);
        rs3 = new RouteSegment(783, 270,
                822, 277, 45);
        omahaChicago.add(rs3);
        rs4 = new RouteSegment(829, 277,
                866, 282, 45);
        omahaChicago.add(rs4);
        routeSegments.put(45, omahaChicago);

        // St Louis to Pittsburg, id 59
        HashSet<RouteSegment> stLouisPittsburg = new HashSet<>();
        rs1 = new RouteSegment(842, 382,
                874, 366, 59);
        stLouisPittsburg.add(rs1);
        rs2 = new RouteSegment(883, 360,
                914, 344, 59);
        stLouisPittsburg.add(rs2);
        rs3 = new RouteSegment(923, 341,
                955, 323, 59);
        stLouisPittsburg.add(rs3);
        rs4 = new RouteSegment(963, 318,
                996, 302, 59);
        stLouisPittsburg.add(rs4);
        rs5 = new RouteSegment(1004, 297,
                1036, 281, 59);
        stLouisPittsburg.add(rs5);
        routeSegments.put(59, stLouisPittsburg);

        // San Francisco to Portland 1, id 3
        HashSet<RouteSegment> portlandSF1 = new HashSet<>();
        rs1 = new RouteSegment(64, 419, 53, 388,
                3);
        rs2 = new RouteSegment(52, 378, 49, 347,
                3);
        rs3 = new RouteSegment(49, 337, 50, 303,
                3);
        rs4 = new RouteSegment(50, 295, 58, 262,
                3);
        rs5 = new RouteSegment(61, 255, 76, 222,
                3);
        portlandSF1.add(rs1);
        portlandSF1.add(rs2);
        portlandSF1.add(rs3);
        portlandSF1.add(rs4);
        portlandSF1.add(rs5);
        routeSegments.put(3, portlandSF1);

        // Portland to San Francisco 2, id 4
        HashSet<RouteSegment> portlandSF2 = new HashSet<>();
        rs1 = new RouteSegment(80, 421, 69, 388,
                4);
        rs2 = new RouteSegment(68, 381, 65, 347,
                4);
        rs3 = new RouteSegment(65, 339, 67, 304,
                4);
        rs4 = new RouteSegment(67, 296, 75, 263,
                4);
        rs5 = new RouteSegment(76, 255, 93, 223,
                4);
        portlandSF2.add(rs1);
        portlandSF2.add(rs2);
        portlandSF2.add(rs3);
        portlandSF2.add(rs4);
        portlandSF2.add(rs5);
        routeSegments.put(4, portlandSF2);

        // San Francisco to SLC, id 11
        HashSet<RouteSegment> SFtoSLC1 = new HashSet<>();
        rs1 = new RouteSegment(94, 422, 131, 411,
                11);
        rs2 = new RouteSegment(137, 410, 174, 399,
                11);
        rs3 = new RouteSegment(180, 397, 217, 385,
                11);
        rs4 = new RouteSegment(223, 384, 259, 373,
                11);
        rs5 = new RouteSegment(267, 370, 302, 360,
                11);
        SFtoSLC1.add(rs1);
        SFtoSLC1.add(rs2);
        SFtoSLC1.add(rs3);
        SFtoSLC1.add(rs4);
        SFtoSLC1.add(rs5);
        routeSegments.put(11, SFtoSLC1);

        // SF to SLC 2, id 12
        HashSet<RouteSegment> SFtoSLC2 = new HashSet<>();
        rs1 = new RouteSegment(101, 437, 138, 426,
                12);
        rs2 = new RouteSegment(145, 423, 182, 412,
                12);
        rs3 = new RouteSegment(187, 411, 224, 399,
                12);
        rs4 = new RouteSegment(231, 397, 267, 386,
                12);
        rs5 = new RouteSegment(274, 384, 308, 374,
                12);
        SFtoSLC2.add(rs1);
        SFtoSLC2.add(rs2);
        SFtoSLC2.add(rs3);
        SFtoSLC2.add(rs4);
        SFtoSLC2.add(rs5);
        routeSegments.put(12, SFtoSLC2);

        // Denver to OKC, id 33
        HashSet<RouteSegment> denverOKC = new HashSet<>();
        rs1 = new RouteSegment(507, 421, 531, 445,
                33);
        rs2 = new RouteSegment(539, 449, 575, 465,
                33);
        rs3 = new RouteSegment(582, 466, 622, 473,
                33);
        rs4 = new RouteSegment(629, 473, 667, 474,
                33);
        denverOKC.add(rs1);
        denverOKC.add(rs2);
        denverOKC.add(rs3);
        denverOKC.add(rs4);
        routeSegments.put(33, denverOKC);

        // El Paso to OKC, id 36
        HashSet<RouteSegment> elPasoOKC = new HashSet<>();
        rs1 = new RouteSegment(497, 603, 534, 592,
                36);
        rs2 = new RouteSegment(539, 589, 575, 573,
                36);
        rs3 = new RouteSegment(582, 570, 613, 551,
                36);
        rs4 = new RouteSegment(620, 545, 648, 522,
                36);
        rs5 = new RouteSegment(653, 515, 675, 488,
                36);
        elPasoOKC.add(rs1);
        elPasoOKC.add(rs2);
        elPasoOKC.add(rs3);
        elPasoOKC.add(rs4);
        elPasoOKC.add(rs5);
        routeSegments.put(36, elPasoOKC);

    }

}
