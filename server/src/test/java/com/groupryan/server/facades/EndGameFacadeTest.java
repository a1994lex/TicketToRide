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
        routes.add(new Route(6, "HELENA", "DULUTH", 15, "orange", 26));
        routes.add(new Route(4, "HELENA", "DENVER", 7, "green", 28));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(2, "SANTA FE", "EL PASO", 2, "", 35));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, "red", 27));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "black", 31));
        routes.add(new Route(4, "DENVER", "KANSAS CITY", 7, "orange", 32));
        player.setRoutes(routes);
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(10, egs.getReachedDestPoints());
        Assert.assertEquals(10, egs.getTotalPoints());

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
        routes.add(new Route(6, "HELENA", "DULUTH", 15, "orange", 26));
        routes.add(new Route(4, "HELENA", "DENVER", 7, "green", 28));
        routes.add(new Route(2, "DENVER", "SANTA FE", 2, "", 29));
        routes.add(new Route(5, "HELENA", "OMAHA", 10, "red", 27));
        player.setRoutes(routes);
        endGameFacade.calculateDestinations(player);
        usersToStats = endGameFacade.getUsernameToStat();
        EndGameStat egs = usersToStats.get("claire");
        Assert.assertEquals(10, egs.getUnreachedDestNegativePoints());
        Assert.assertEquals(-10, egs.getTotalPoints());
    }

}
