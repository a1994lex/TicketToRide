package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Route;

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

    public void claimRoute(String username, Route route) {
        RootClientModel.addClaimedRoute(username, route);
    }
}
