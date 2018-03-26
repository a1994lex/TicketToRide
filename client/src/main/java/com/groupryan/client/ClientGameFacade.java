package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.DestCardReturnObject;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.EndGameStatReturnObject;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.TrainCardReturnObject;
import com.groupryan.shared.results.CommandResult;

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

    public void discardColorCards(TrainCardReturnObject list){
        ArrayList<TrainCard> cards = list.convertToArray();
        RootClientModel.getCurrentGame().discardTrainCards(cards);
    }

    public void drawColorCard(TrainCard tc){
        RootClientModel.getCurrentGame().getMyPlayer().addTrainCard(tc);
    }

    public void drawThreeCards(DestCardReturnObject cards) {
        ArrayList<DestCard> cardss=cards.convertToArray();
        RootClientModel.getCurrentGame().updateDestCards(cardss);
    }

    public void claimRoute(Route r, String username){
        if(RootClientModel.getCurrentGame().getMyPlayer().getUsername().equals(username)){
            RootClientModel.getCurrentGame().addClaimedRoute(username, r);
        }
        //TODO idk
    }

    public void gameOver(String winner, EndGameStatReturnObject endStats) {
        changeTurn(-1);
        List<EndGameStat> endGameStats = endStats.getEndGameStats();
        RootClientModel.getCurrentGame().setWinner(winner);
        RootClientModel.getCurrentGame().setEndGameStats(endGameStats);
    }

    public void changeTurn(Integer turnNum){
        RootClientModel.getCurrentGame().setCurrentTurn(turnNum);
    }
}
