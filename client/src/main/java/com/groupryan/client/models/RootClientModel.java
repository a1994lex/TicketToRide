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
    private HashMap<String, String> players; // username, Color
    private HashMap<String, Route> claimedRoutes = new HashMap<String, Route>();
    private HashMap<Integer, HashSet<RouteSegment>> routeSegments = new HashMap<>();
    private ClientGame clientGame = null;
    private boolean showRoutes = false;

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

    private void initializeRouteSegmentData() {
        // Helena to Duluth, id 26
        HashSet<RouteSegment> helenaDuluth = new HashSet<>();
        RouteSegment rs1 = new RouteSegment(440, 220,
                480, 219, 26, utils.BLACK);
        helenaDuluth.add(rs1);
        RouteSegment rs2 = new RouteSegment(485, 219,
                526, 218, 26, utils.BLACK);
        helenaDuluth.add(rs2);
        RouteSegment rs3 = new RouteSegment(531, 218,
                572, 217, 26, utils.BLACK);
        helenaDuluth.add(rs3);
        RouteSegment rs4 = new RouteSegment(577, 217,
                618, 216, 26, utils.BLACK);
        helenaDuluth.add(rs4);
        RouteSegment rs5 = new RouteSegment(623, 215,
                664, 215, 26, utils.BLACK);
        helenaDuluth.add(rs5);
        RouteSegment rs6 = new RouteSegment(672, 215,
                713, 215, 26, utils.BLACK);
        helenaDuluth.add(rs6);
        routeSegments.put(26, helenaDuluth);
        // OKC to Little Rock, id 50
        HashSet<RouteSegment> okcLittleRock = new HashSet<>();
        RouteSegment rs7 = new RouteSegment(706, 481,
                746, 480, 50, utils.BLACK);
        okcLittleRock.add(rs7);
        RouteSegment rs8 = new RouteSegment(752, 480,
                792, 480, 50, utils.BLACK);
        okcLittleRock.add(rs8);
        routeSegments.put(50, okcLittleRock);
        // Atlanta to Charleston
        HashSet<RouteSegment> atlantaCharleston = new HashSet<>();
        RouteSegment rs9 = new RouteSegment(1034, 480,
                1074, 480, 86, utils.BLACK);
        atlantaCharleston.add(rs9);
        RouteSegment rs10 = new RouteSegment(1080, 480,
                1121, 483, 86, utils.BLACK);
        atlantaCharleston.add(rs10);
        routeSegments.put(86, atlantaCharleston);

    }
}
