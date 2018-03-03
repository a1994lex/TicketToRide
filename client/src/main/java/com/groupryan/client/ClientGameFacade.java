package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;

import java.util.ArrayList;

/**
 * Created by clairescout on 2/27/18.
 */

public class ClientGameFacade {

    public ClientGameFacade(){}

    public void updateHistory(String event){
        RootClientModel.getCurrentGame().updateHistory(event);
    }

    public void updateChat(String msg){ RootClientModel.getCurrentGame().updateChat(msg);}
}
