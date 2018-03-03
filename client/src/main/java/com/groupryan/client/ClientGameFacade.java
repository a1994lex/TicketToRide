package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;

/**
 * Created by clairescout on 2/27/18.
 */

public class ClientGameFacade {

    public ClientGameFacade(){}

    public void updateHistory(String event){
        RootClientModel.getInstance().getCurrentGame().updateHistory(event);
    }

    public void updateStat(Stat stat){
        RootClientModel.getInstance().getCurrentGame().updateStat(stat);
    }

    public void updateChat(String msg){ RootClientModel.getCurrentGame().updateChat(msg);}
}
