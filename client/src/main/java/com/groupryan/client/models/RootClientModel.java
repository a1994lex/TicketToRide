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
