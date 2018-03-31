package com.groupryan.server.models;

import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LongestPathCalcTest {

    private LongestPathCalc lpCalc = new LongestPathCalc();

    private ServerGame serverGame1;
    private Player player1;
    private Player player2;


    @Before
    public void setUp() throws Exception {
        this.serverGame1 = new ServerGame("0", new Deck(new ArrayList<>()), new Deck(new ArrayList<>()));
        this.player1 = new Player("", new ArrayList<>(), new ArrayList<>(), "1", 1, false);
        this.player2 = new Player("", new ArrayList<>(), new ArrayList<>(), "2", 1, false);
    }

    @After
    public void tearDown() throws Exception {
        this.serverGame1 = null;
        this.player1 = null;
        this.player2 = null;
    }

    @Test
    public void OneToNoneRouteTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player1, longestPathPlayerList.get(0));
    }

    @Test
    public void OneToOneRouteTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player2, longestPathPlayerList.get(0));
    }

    @Test
    public void TwoShortConnectedToOneLongRouteTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.player1.addRoute(new Route(4, "OMAHA", "CHICAGO", 7, utils.BLUE, 48));
        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player1, longestPathPlayerList.get(0));
    }

    @Test
    public void TwoShortConnectedNonBiDirectionalToOneLongRouteTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.player1.addRoute(new Route(2, "DULUTH", "OMAHA", 2, "", 46));
        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player1, longestPathPlayerList.get(0));
    }

    @Test
    public void ManyToManyRoutesTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.player1.addRoute(new Route(4, "OMAHA", "CHICAGO", 7, utils.BLUE, 48));
        this.player1.addRoute(new Route(2, "SAINT LOUIS", "CHICAGO", 2, utils.GREEN, 67));
        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        this.player2.addRoute(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.YELLOW, 7));
        this.player2.addRoute(new Route(4, "CHARLESTON", "MIAMI", 7, utils.PINK, 97));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player1, longestPathPlayerList.get(0));
    }

    @Test
    public void TieLongestPathTest() throws Exception {
        this.player1.addRoute(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 29));
        this.player2.addRoute(new Route(5, "SAULT ST. MARIE", "MONTREAL", 10, utils.BLACK, 72));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(2, longestPathPlayerList.size());
        Set<Player> expectedSet = new HashSet<>(this.serverGame1.getPlayers());
        Set<Player> longestPathCalculatedSet = new HashSet<>(longestPathPlayerList);
        assertTrue(expectedSet.equals(longestPathCalculatedSet));
    }

    @Test
    public void MegaLongPathTest() throws Exception {
        this.player1.addRoute(new Route(6, "SEATTLE", "HELENA", 15, utils.YELLOW, 11));
        this.player1.addRoute(new Route(6, "HELENA", "DULUTH", 15, utils.ORANGE, 28));
        this.player1.addRoute(new Route(6, "DULUTH", "TORONTO", 15, utils.PINK, 44));
        this.player1.addRoute(new Route(2, "TORONTO", "PITTSBURGH", 2, "", 75));
        this.player1.addRoute(new Route(2, "PITTSBURGH", "RALEIGH", 2, "", 84));
        this.player1.addRoute(new Route(2, "RALEIGH", "CHARLESTON", 2, "", 98));
        this.player1.addRoute(new Route(2, "ATLANTA", "CHARLESTON", 2, "", 96));
        this.player2.addRoute(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, utils.GREEN, 5));
        this.player2.addRoute(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.YELLOW, 7));
        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        this.player2.addRoute(new Route(5, "EL PASO", "OKLAHOMA CITY", 10, utils.YELLOW, 38));
        this.player2.addRoute(new Route(2, "OKLAHOMA CITY", "LITTLE ROCK", 2, "", 55));
        this.player2.addRoute(new Route(3, "LITTLE ROCK", "NEW ORLEANS", 4, utils.GREEN, 62));
        this.player2.addRoute(new Route(6, "NEW ORLEANS", "MIAMI", 15, utils.RED, 92));
        this.serverGame1.addPlayer(player1);
        this.serverGame1.addPlayer(player2);
        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
        assertEquals(1, longestPathPlayerList.size());
        assertEquals(this.player2, longestPathPlayerList.get(0));
    }

//    @Test
//    public void circularPathTest() throws Exception {
//        this.player1.addRoute(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 1));
//        this.player1.addRoute(new Route(4, "SEATTLE", "CALGARY", 7, "", 10));
//        this.player1.addRoute(new Route(3, "VANCOUVER", "CALGARY", 4, "", 9));
//        this.player1.addRoute(new Route(1, "SEATTLE", "PORTLAND", 1, "", 3));
//        this.player2.addRoute(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
////        this.player2.addRoute(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.YELLOW, 7));
//        this.player2.addRoute(new Route(4, "CHARLESTON", "MIAMI", 7, utils.PINK, 97));
//        this.serverGame1.addPlayer(player1);
//        this.serverGame1.addPlayer(player2);
//        ArrayList<Player> longestPathPlayerList = lpCalc.getLongestPathInGame(this.serverGame1);
//        assertEquals(1, longestPathPlayerList.size());
//        assertEquals(this.player1, longestPathPlayerList.get(0));
//    }
}
