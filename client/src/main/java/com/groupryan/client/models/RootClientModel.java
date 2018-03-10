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
    private HashMap<String, Route> claimedRoutes = new HashMap<String, Route>();
    private HashMap<Integer, HashSet<RouteSegment>> routeSegments = new HashMap<>();
    private ClientGame clientGame = null;
    private boolean showRoutes = true;

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

    public static List<Route> getClaimedRoutes() {
        return single_instance._getClaimedRoutes();
    }

    private static RootClientModel single_instance = new RootClientModel();


    public static void addUser(User user) {
        // TODO: check if i am doing add user as intended
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
        single_instance._addRoute(username, route);
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
//        players = new HashMap<>();
//        initializePlayers();
        initializeRouteSegmentData();
    }

    private void initializePlayers() {
        HashMap<String, String> players = new HashMap<>();
        players.put(utils.GREEN, "jimbob");
        players.put(utils.YELLOW, "joanna");
        players.put(utils.RED, "q");
        addPlayers(players);
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

    private HashMap<String, Route> _getRoutesMap() {
        return claimedRoutes;
    }

    private List<Route> _getClaimedRoutes() {
        List<Route> claimRoutes = new ArrayList<>();
        for (Map.Entry<String, Route> entry : claimedRoutes.entrySet()) {
            claimRoutes.add(entry.getValue());
        }
        return claimRoutes;
    }

    private void _addRoute(String username, Route route) {
        claimedRoutes.put(username, route);
        setChanged();
        notifyObservers(route);
    }

    private void _updateStats(Stat stat) {
        clientGame.updateStat(stat);
        setChanged();
        notifyObservers();
    }

    // create a set of RouteSegment objects
    // create new RouteSegments representing each segment of the route
    // put in x and y coordinates of point A and B
    // put in route id
    // add the segments to the set
    // routeSegments.put(routeId, set of routeSegments)
    // each segment is about 40 units long and 10 units thick
    // x values increase from left to right
    // y values increase from top to bottom (annoying)
    private void initializeRouteSegmentData() {

        // Helena to Duluth, id 26
        HashSet<RouteSegment> helenaDuluth = new HashSet<>();
        RouteSegment rs1 = new RouteSegment(440, 220,
                480, 219, 26);
        helenaDuluth.add(rs1);
        RouteSegment rs2 = new RouteSegment(485, 219,
                526, 218, 26);
        helenaDuluth.add(rs2);
        RouteSegment rs3 = new RouteSegment(531, 218,
                572, 217, 26);
        helenaDuluth.add(rs3);
        RouteSegment rs4 = new RouteSegment(577, 217,
                618, 216, 26);
        helenaDuluth.add(rs4);
        RouteSegment rs5 = new RouteSegment(623, 215,
                664, 215, 26);
        helenaDuluth.add(rs5);
        RouteSegment rs6 = new RouteSegment(672, 215,
                713, 215, 26);
        helenaDuluth.add(rs6);
        routeSegments.put(26, helenaDuluth);

        // OKC to Little Rock, id 50
        HashSet<RouteSegment> okcLittleRock = new HashSet<>();
        RouteSegment rs7 = new RouteSegment(706, 481,
                746, 480, 50);
        okcLittleRock.add(rs7);
        RouteSegment rs8 = new RouteSegment(752, 480,
                792, 480, 50);
        okcLittleRock.add(rs8);
        routeSegments.put(50, okcLittleRock);

        // Atlanta to Charleston, id 86
        HashSet<RouteSegment> atlantaCharleston = new HashSet<>();
        RouteSegment rs9 = new RouteSegment(1034, 480,
                1074, 480, 86);
        atlantaCharleston.add(rs9);
        RouteSegment rs10 = new RouteSegment(1080, 480,
                1121, 483, 86);
        atlantaCharleston.add(rs10);
        routeSegments.put(86, atlantaCharleston);

        // Duluth to Toronto, id 42
        HashSet<RouteSegment> duluthToronto = new HashSet<>();
        RouteSegment rs11 = new RouteSegment(740, 212,
                780, 206, 42);
        duluthToronto.add(rs11);
        RouteSegment rs12 = new RouteSegment(786, 204,
                826, 198, 42);
        duluthToronto.add(rs12);
        RouteSegment rs13 = new RouteSegment(832, 196,
                872, 190, 42);
        duluthToronto.add(rs13);
        RouteSegment rs14 = new RouteSegment(878, 189,
                918, 183, 42);
        duluthToronto.add(rs14);
        RouteSegment rs15 = new RouteSegment(924, 182,
                964, 176, 42);
        duluthToronto.add(rs15);
        RouteSegment rs16 = new RouteSegment(970, 174,
                1010, 168, 42);
        duluthToronto.add(rs16);
        routeSegments.put(42, duluthToronto);

        // Atlanta to Miami, id 84
        HashSet<RouteSegment> atlantaMiami = new HashSet<>();
        RouteSegment rs17 = new RouteSegment(1022, 487,
                1045, 517, 84);
        atlantaMiami.add(rs17);
        RouteSegment rs18 = new RouteSegment(1050, 520,
                1075, 550, 84);
        atlantaMiami.add(rs18);
        RouteSegment rs19 = new RouteSegment(1078, 553,
                1105, 583, 84);
        atlantaMiami.add(rs19);
        RouteSegment rs20 = new RouteSegment(1106, 586,
                1133, 614, 84);
        atlantaMiami.add(rs20);
        RouteSegment rs21 = new RouteSegment(1135, 620,
                1162, 646, 84);
        atlantaMiami.add(rs21);
        routeSegments.put(84, atlantaMiami);

        // Seattle to Helena, id 9
        HashSet<RouteSegment> seattleHelena = new HashSet<>();
        RouteSegment rs22 = new RouteSegment(137, 166,
                174, 176, 9);
        seattleHelena.add(rs22);
        RouteSegment rs23 = new RouteSegment(180, 177,
                219, 182, 9);
        seattleHelena.add(rs23);
        RouteSegment rs24 = new RouteSegment(227, 185,
                265, 193, 9);
        seattleHelena.add(rs24);
        RouteSegment rs25 = new RouteSegment(272, 193,
                311, 200, 9);
        seattleHelena.add(rs25);
        RouteSegment rs26 = new RouteSegment(317, 202,
                356, 210, 9);
        seattleHelena.add(rs26);
        RouteSegment rs27 = new RouteSegment(363, 210,
                401, 219, 9);
        seattleHelena.add(rs27);
        routeSegments.put(9, seattleHelena);

        // LA to El Paso, id 15
        HashSet<RouteSegment> LAelPaso = new HashSet<>();
        RouteSegment rs28 = new RouteSegment(190, 569,
                219, 589, 15);
        LAelPaso.add(rs28);
        RouteSegment rs29 = new RouteSegment(230, 595,
                261, 608, 15);
        LAelPaso.add(rs29);
        RouteSegment rs30 = new RouteSegment(274, 610,
                308, 619, 15);
        LAelPaso.add(rs30);
        RouteSegment rs31 = new RouteSegment(317, 619,
                356, 626, 15);
        LAelPaso.add(rs31);
        RouteSegment rs32 = new RouteSegment(363, 626,
                401, 623, 15);
        LAelPaso.add(rs32);
        RouteSegment rs33 = new RouteSegment(407, 622,
                445, 614, 15);
        LAelPaso.add(rs33);
        routeSegments.put(15, LAelPaso);

        // El Paso to Houston, id 38
        HashSet<RouteSegment> elPasoHouston = new HashSet<>();
        RouteSegment rs34 = new RouteSegment(493, 621,
                523, 639, 38);
        elPasoHouston.add(rs34);
        RouteSegment rs35 = new RouteSegment(531, 639,
                570, 649, 38);
        elPasoHouston.add(rs35);
        RouteSegment rs36 = new RouteSegment(578, 652,
                613, 656, 38);
        elPasoHouston.add(rs36);
        RouteSegment rs37 = new RouteSegment(622, 656,
                661, 658, 38);
        elPasoHouston.add(rs37);
        RouteSegment rs38 = new RouteSegment(670, 658,
                705, 652, 38);
        elPasoHouston.add(rs38);
        RouteSegment rs39 = new RouteSegment(716, 649,
                751, 640, 38);
        elPasoHouston.add(rs39);
        routeSegments.put(38, elPasoHouston);

        // New Orleans to Miami, id 83
        HashSet<RouteSegment> newOrleansMiami = new HashSet<>();
        RouteSegment rs40 = new RouteSegment(918, 615,
                951, 597, 83);
        newOrleansMiami.add(rs40);
        RouteSegment rs41 = new RouteSegment(960, 596,
                996, 584, 83);
        newOrleansMiami.add(rs41);
        RouteSegment rs42 = new RouteSegment(1005, 584,
                1044, 585, 83);
        newOrleansMiami.add(rs42);
        RouteSegment rs43 = new RouteSegment(1053, 588,
                1088, 604, 83);
        newOrleansMiami.add(rs43);
        RouteSegment rs44 = new RouteSegment(1094, 608,
                1124, 631, 83);
        newOrleansMiami.add(rs44);
        RouteSegment rs45 = new RouteSegment(1129, 634,
                1154, 665, 83);
        newOrleansMiami.add(rs45);
        routeSegments.put(83, newOrleansMiami);

        // Portland to SLC, id 10
        HashSet<RouteSegment> portlandSLC = new HashSet<>();
        RouteSegment rs46 = new RouteSegment(109, 211,
                145, 218, 10);
        portlandSLC.add(rs46);
        RouteSegment rs47 = new RouteSegment(153, 219,
                190, 233, 10);
        portlandSLC.add(rs47);
        RouteSegment rs48 = new RouteSegment(198, 234,
                230, 254, 10);
        portlandSLC.add(rs48);
        RouteSegment rs49 = new RouteSegment(237, 258,
                265, 281, 10);
        portlandSLC.add(rs49);
        RouteSegment rs50 = new RouteSegment(272, 285,
                297, 312, 10);
        portlandSLC.add(rs50);
        RouteSegment rs51 = new RouteSegment(302, 317,
                319, 347, 10);
        portlandSLC.add(rs51);
        routeSegments.put(10, portlandSLC);

        // Calgary to Winnipeg, id 16
        HashSet<RouteSegment> calgaryWinnipeg = new HashSet<>();
        RouteSegment rs52 = new RouteSegment(309, 62,
                343, 47, 16);
        calgaryWinnipeg.add(rs52);
        RouteSegment rs53 = new RouteSegment(353, 47,
                390, 39, 16);
        calgaryWinnipeg.add(rs53);
        RouteSegment rs54 = new RouteSegment(398, 39,
                437, 37, 16);
        calgaryWinnipeg.add(rs54);
        RouteSegment rs55 = new RouteSegment(444, 35,
                483, 39, 16);
        calgaryWinnipeg.add(rs55);
        RouteSegment rs56 = new RouteSegment(490, 40,
                528, 49, 16);
        calgaryWinnipeg.add(rs56);
        RouteSegment rs57 = new RouteSegment(535, 52,
                571, 66, 16);
        calgaryWinnipeg.add(rs57);
        routeSegments.put(16, calgaryWinnipeg);

        // Charleston to Miami, id 87
        HashSet<RouteSegment> charlestonMiami = new HashSet<>();
        RouteSegment rs58 = new RouteSegment(1133, 486,
                1136, 521, 87);
        charlestonMiami.add(rs58);
        RouteSegment rs59 = new RouteSegment(1136, 530,
                1142, 564, 87);
        charlestonMiami.add(rs59);
        RouteSegment rs60 = new RouteSegment(1144, 571,
                1156, 605, 87);
        charlestonMiami.add(rs60);
        RouteSegment rs61 = new RouteSegment(1160, 611,
                1180, 642, 87);
        charlestonMiami.add(rs61);
        routeSegments.put(87, charlestonMiami);
    }

}
