package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Stat;

import java.util.ArrayList;
import java.util.List;

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
        RootClientModel.getCurrentGame().updateChat(new Chat(msg, username));}

    public void setBank(Bank bank){
        RootClientModel.getCurrentGame().setBank(bank);
    }

    public void setTDeckSize(Integer size){
        RootClientModel.getCurrentGame().setTDeckSize(size);
    }

    public void setDDeckSize(Integer size){
        RootClientModel.getCurrentGame().setDDeckSize(size);
    }

    public void discardDestCard(DestCardList destCardList, String username) {
        assert (RootClientModel.getCurrentGame().getMyPlayer().getUsername().equals(username));
        RootClientModel.getCurrentGame().discardDestCards(destCardList);
    }

    public void drawThreeCards(List<DestCard> cards) {

    }
}
