package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanm on 3/24/2018.
 */

public class ClaimRouteFacade {

    public CommandResult claimRoute(String username, int routeId, List<Integer> trainCardIDs){
        CommandResult cr = new CommandResult();
        RootServerModel root =RootServerModel.getInstance();
        ServerGame serverGame = root.getServerGame(username);
        Player p=serverGame.getPlayer(username);
        Route r=root.getRoute(routeId);
        List<TrainCard> discardable= new ArrayList<>();
       for(int i: trainCardIDs){
            Card c = root.getCard(utils.TRAIN, i);
            p.removeTrainCard(c);
            discardable.add((TrainCard) c);
        }
        //discardTrainCards from player
        p.removeTrains(r.getLength());
        //remove the number of trains
        p.addPoints(r.getWorth());
        //addPoints to player
        p.addRoute(r);
        //add route to players list of claimed routes
        r.setColor(p.getColor());
        //make route match players color....
        //
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


}











