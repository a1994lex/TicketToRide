package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;

import java.util.ArrayList;

/**
 * Created by clairescout on 2/27/18.
 */

public class ClientGameFacade {

    public ClientGameFacade(){}

    public void updateHistory(String event){
        RootClientModel.getCurrentGame().updateHistory(event);
    }

    public void updateStat(Stat stat){
        RootClientModel.getCurrentGame().updateStat(stat);
    }

    public void updateChat(String msg, String username){
        RootClientModel.getCurrentGame().updateChat(msg, username);}
}
