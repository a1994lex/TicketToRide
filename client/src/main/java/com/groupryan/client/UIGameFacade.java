package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.TrainCardList;
import com.groupryan.shared.results.CommandResult;

import java.util.List;

/**
 * Created by Daniel on 3/5/2018.
 */

public class UIGameFacade {
    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    private static UIGameFacade instance;

    public static UIGameFacade getInstance() {
        if (instance == null) {
            instance = new UIGameFacade();
        }
        return instance;
    }

    private UIGameFacade() {}

    public CommandResult placeRoute(List<Integer> trainCards, String username, int routeId) {
        TrainCardList trainCardIds = new TrainCardList(trainCards);
        return ServerProxy.getInstance().claimRoute(username, routeId, trainCardIds);
    }

//    public void claimRoute(String username, Route route) {
//        RootClientModel.addClaimedRoute(username, route);
//    }
}
