package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clairescout on 3/21/18.
 */

public class EndGameFacade {

    HashMap<String, EndGameStat> usernameToStat = new HashMap<>();
    String winner; //how do i want to pass back the winner?

    public EndGameFacade(){}

    public void end(String gameId){
        ServerGame serverGame = RootServerModel.getInstance().getServerGameByGameId(gameId);
        longestPath();
        List<Player> players = serverGame.getPlayers();
        for (Player p : players){
            EndGameStat endGameStat = new EndGameStat(p.getUsername());
            endGameStat.setClaimedRoutePoints(p.getPoints());
            endGameStat.setTotalPoints(p.getPoints());
            usernameToStat.put(p.getUsername(), endGameStat);
            calculateDestinations(p);
        }
        calculateWinner();
        //CommandManager makeFinalStatsCommand(...);
        CommandManager.getInstance().makeGameOverCommand(winner, gameId);

    }

    public void setUsernameToStat(HashMap<String, EndGameStat> usernameToStat) {
        this.usernameToStat = usernameToStat;
    }

    public HashMap<String, EndGameStat> getUsernameToStat() {
        return usernameToStat;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    private void longestPath(){

    }

    public void calculateWinner(){
        //gets the person with the higheest total score and sets them as winner

        ArrayList<EndGameStat> finalstats = new ArrayList<>();
        for(EndGameStat egs : usernameToStat.values()){
            finalstats.add(egs);
        }
        EndGameStat winningStat = Collections.max(finalstats, new EndGameFacade.WinnerComparing());
        winner = winningStat.getUsername();

    }

    public void calculateDestinations(Player player){
        dfsPrep(player.getRoutes());

        for(DestCard destinationCard : player.getDestCards()){
            //startRoute gets starting route for dfs. if they don't have it, returns null, then takes necessary points
            Route startRoute = getStart(destinationCard.getCityOne(), player.getRoutes());
            if ( startRoute == null) {
                usernameToStat.get(player.getUsername()).increaseUnreachedDestNegativePoints(destinationCard.getValue());
            }

            else{
                String startCity;
                //this if statement is to check if the first or second city of the route is the start of the destination to pass correct city into dfs
                if (startRoute.getCityOne().equals(destinationCard.getCityOne())){
                    startCity = startRoute.getCityTwo();
                }
                else{
                    startCity = startRoute.getCityOne();
                }

                //dfs return true if they have finished the route and increases their points, false if they haven't and decreases points
                if(dfs(startCity, dfsPrep(player.getRoutes()), startRoute, destinationCard.getCityTwo(), player.getRoutes() )){
                    usernameToStat.get(player.getUsername()).increaseReachedDestinationPoints(destinationCard.getValue());
                }
                else{
                    usernameToStat.get(player.getUsername()).increaseUnreachedDestNegativePoints(destinationCard.getValue());
                }
            }
        }
    }


    private HashMap<Integer, Boolean> dfsPrep(List<Route> routes){
        //creates a map from route id to boolean so that routes aren't visited multiple times

        HashMap<Integer, Boolean> idToVisited = new HashMap<Integer, Boolean>();
        for( Route r : routes){
            idToVisited.put(r.getId(), false);
        }

        return idToVisited;
    }

    private boolean dfs(String city, HashMap<Integer, Boolean> idToVisited, Route route, String finalDestination, List<Route> routes){
        //depth first search to find if destination card is complete

        idToVisited.put(route.getId(), true);
        for (Route r: routes){
            if(r.getCityOne().equals(city) || r.getCityTwo().equals(city)){
                if(r.getCityOne().equals(finalDestination) || r.getCityTwo().equals(finalDestination)){
                    return true;
                }
                if(!idToVisited.get(r.getId())){
                    String city2;
                    if(r.getCityOne().equals(city)){ city2 = r.getCityTwo(); }
                    else { city2 = r.getCityOne();}
                    return dfs(city2, idToVisited, r, finalDestination, routes);
                }
            }
        }

        return false;
    }

    private Route getStart(String city, List<Route> routes){
        //gets starting route for a given destination card
        for (Route r : routes) {
            if (r.getCityOne().equals(city) || r.getCityTwo().equals(city)) {
                return r;
            }
        }
        return null;
    }


    public class WinnerComparing implements Comparator<EndGameStat> {

        @Override
        public int compare(EndGameStat e1, EndGameStat e2) {
            if (e1.getTotalPoints() > e2.getTotalPoints()){
                return 1;
            }
            if (e1.getTotalPoints() == e2.getTotalPoints()){
                return 0;
            }
            return -1;
        }
    }



    /*

     */

}
