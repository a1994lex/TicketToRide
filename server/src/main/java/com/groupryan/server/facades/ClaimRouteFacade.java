package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.TrainCardList;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanm on 3/24/2018.
 */

public class ClaimRouteFacade {

    public CommandResult claimRoute(String username, int routeId, TrainCardList trainCardIDs){
        CommandResult cr = new CommandResult();
        RootServerModel root =RootServerModel.getInstance();
        ServerGame serverGame = root.getServerGame(username);
        Player p=serverGame.getPlayer(username);
        Route r=root.getRoute(routeId);
        List<TrainCard> discardable= new ArrayList<>();
        List<Integer> trainCards = trainCardIDs.getTrainCards();
//        List<Double> tc = trainCardIDs.getTrainCardsDoubles();
       for(int i = 0; i < trainCards.size(); i++){
            Card c = root.getCard(utils.TRAIN, trainCards.get(i));
            p.removeTrainCard(c);
            discardable.add((TrainCard) c);
        }
        //discardTrainCards from player
        p.removeTrains(r.getLength());
        //remove the number of trains
        p.addPoints(r.getWorth());
        //addPoints to player
        r.setColor(p.getColor());
        //make route match players color....
        p.addRoute(r);
        //add route to players list of claimed routes
        serverGame.removeTrainCardsFromPlayer(discardable);
        cr.addClientCommand(CommandManager.getInstance().makeDiscardTrainCardsCommand(discardable));
        //send discard command to PLayer
        CommandManager.getInstance().makeClaimRouteCommand(r, username, serverGame.getServerGameID());
        //show route on all

        //ifEndGame || end turn
        String history = username + " claimed "+r.getCityOne()+" to "+r.getCityTwo() +".";
        serverGame.addHistory(history);
        CommandManager.getInstance().addHistoryCommand(history, serverGame.getServerGameID(), null);
        CommandManager.getInstance().makeStatCommand(serverGame.getServerGameID(), serverGame.getStat(username));
        return cr;
    }

    private double[] convertToDouble(List<Integer> trainCards) {
        double[] usedTrainCards = new double[trainCards.size()];
        for (int i = 0; i < trainCards.size(); i++) {
            usedTrainCards[i] = trainCards.get(i).doubleValue();
        }
        return usedTrainCards;
    }
}












