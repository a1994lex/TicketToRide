package com.groupryan.server.facades;

import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clairescout on 3/22/18.
 */

public class EndGameFacadeTest {

    private EndGameFacade endGameFacade = new EndGameFacade();

    @Before
    public void setUp(){
        HashMap<String,EndGameStat> usersToStats = new HashMap<>();
        EndGameStat eg1 = new EndGameStat("haley");
        EndGameStat eg2 = new EndGameStat("claire");
        EndGameStat eg3 = new EndGameStat("grace");
        EndGameStat eg4 = new EndGameStat("kate");
        usersToStats.put("haley", eg1);
        usersToStats.put("claire", eg2);
        usersToStats.put("grace", eg3);
        usersToStats.put("kate", eg4);
        endGameFacade.setUsernameToStat(usersToStats);
    }

    @Test
    public void testCalculateWinner(){
        int total = 20;
        for(EndGameStat egs : endGameFacade.getUsernameToStat().values()){
            egs.setTotalPoints(total);
            total += 20;
        }
        endGameFacade.calculateWinner();
        Assert.assertEquals("claire", endGameFacade.getWinner());

    }

    @Test
    public void testDestinationsNoRoutes(){
        HashMap<String,EndGameStat> usersToStats = new HashMap<>();
        EndGameStat eg2 = new EndGameStat("claire");
        usersToStats.put("claire", eg2);
        endGameFacade.setUsernameToStat(usersToStats);
        List<DestCard> destCards = new ArrayList<DestCard>();
        destCards.add(new DestCard(16, "LOS ANGELES", "CHICAGO", 23));
        List<TrainCard> trainCards = new ArrayList<>();
        Player player = new Player(utils.BLACK, destCards, trainCards, "claire", 1 , false );
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(16, egs.getUnreachedDestNegativePoints());
        Assert.assertEquals(-16, egs.getTotalPoints());

    }

    @Test
    public void testCalculateDestinations(){
        //tests a finished route
        HashMap<String,EndGameStat> usersToStats = new HashMap<>();
        EndGameStat eg2 = new EndGameStat("claire");
        usersToStats.put("claire", eg2);
        endGameFacade.setUsernameToStat(usersToStats);
        List<DestCard> destCards = new ArrayList<DestCard>();
        destCards.add(new DestCard(10, "DULUTH", "EL PASO", 9));
        List<TrainCard> trainCards = new ArrayList<>();
        Player player = new Player(utils.BLACK, destCards, trainCards, "claire", 1 , false );
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(6, "HELENA", "DULUTH", 15, utils.ORANGE, 26));
        routes.add(new Route(4, "HELENA", "DENVER", 7, utils.GREEN, 28));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 35));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 27));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, utils.BLACK, 31));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, utils.ORANGE, 32));
        player.setRoutes(routes);
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(10, egs.getReachedDestPoints());
        Assert.assertEquals(10, egs.getTotalPoints());

    }

    @Test
    public void testCalculateDestinationsAgain(){
        HashMap<String,EndGameStat> usersToStats = new HashMap<>();
        EndGameStat eg2 = new EndGameStat("claire");
        usersToStats.put("claire", eg2);
        endGameFacade.setUsernameToStat(usersToStats);
        List<DestCard> destCards = new ArrayList<DestCard>();
        DestCard d = new DestCard(11, "WINNIPEG", "LITTLE ROCK", 1);
        destCards.add(d);
        d = new DestCard(7, "CALGARY", "SALT LAKE CITY", 2);
        destCards.add(d);
        d = new DestCard(22, "SEATTLE", "NEW YORK", 12);
        destCards.add(d);
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(6, "HELENA", "DULUTH", 15, utils.ORANGE, 26));
        routes.add(new Route(4, "HELENA", "DENVER", 7, utils.GREEN, 28));
        routes.add(new Route(1, "VANCOUVER", "SEATTLE", 1, "", 1));
        routes.add(new Route(3, "VANCOUVER", "CALGARY", 4, "", 9));
        routes.add(new Route(6, "CALGARY", "WINNIPEG", 15, utils.WHITE, 18));
        routes.add(new Route(6, "WINNIPEG", "SAULT ST. MARIE", 15, "", 42));
        routes.add(new Route(5, "SAULT ST. MARIE", "MONTREAL", 10, utils.BLACK, 72));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 35));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 27));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, utils.BLACK, 31));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, utils.ORANGE, 32));

        routes.add(new Route(3, "MONTREAL", "NEW YORK", 4, utils.BLUE, 78));
        routes.add(new Route(2, "SAULT ST. MARIE", "TORONTO", 2, "", 73));
        routes.add(new Route(4, "HELENA", "WINNIPEG", 7, utils.BLUE, 27));
        routes.add(new Route(1, "SEATTLE", "PORTLAND", 1, "", 3));
        routes.add(new Route(5, "PORTLAND", "SAN FRANCISCO", 10, utils.GREEN, 5));
        routes.add(new Route(3, "SAN FRANCISCO", "LOS ANGELES", 4, utils.YELLOW, 7));
        routes.add(new Route(6, "LOS ANGELES", "EL PASO", 15, utils.BLACK, 17));
        //routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 37));
        //routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 31));
        routes.add(new Route(3, "SALT LAKE CITY", "DENVER", 4, utils.RED, 21));
        List<TrainCard> trainCards = new ArrayList<>();
        Player player = new Player(utils.BLACK, destCards, trainCards, "claire", 1 , false );
        player.setRoutes(routes);
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(29, egs.getReachedDestPoints());
        Assert.assertEquals(11, egs.getUnreachedDestNegativePoints());




    }

    @Test
    public void testCalculateDestUnfinishedRoute(){
        HashMap<String,EndGameStat> usersToStats = new HashMap<>();
        EndGameStat eg2 = new EndGameStat("claire");
        usersToStats.put("claire", eg2);
        endGameFacade.setUsernameToStat(usersToStats);
        List<DestCard> destCards = new ArrayList<DestCard>();
        destCards.add(new DestCard(10, "DULUTH", "EL PASO", 9));
        List<TrainCard> trainCards = new ArrayList<>();
        Player player = new Player(utils.BLACK, destCards, trainCards, "claire", 1, false  );
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(6, "HELENA", "DULUTH", 15, utils.ORANGE, 26));
        routes.add(new Route(4, "HELENA", "DENVER", 7, utils.GREEN, 28));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, utils.RED, 27));
        player.setRoutes(routes);
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(10, egs.getUnreachedDestNegativePoints());
        Assert.assertEquals(-10, egs.getTotalPoints());
    }

    @Test
    public void testTie(){
        int total = 20;
        for(EndGameStat egs : endGameFacade.getUsernameToStat().values()){
            egs.setTotalPoints(total);
        }
        endGameFacade.calculateWinner();
        Assert.assertEquals("haley and kate and grace and claire", endGameFacade.getWinner());
    }

}
