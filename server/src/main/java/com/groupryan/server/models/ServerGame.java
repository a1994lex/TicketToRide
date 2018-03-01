package com.groupryan.server.models;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arctu on 2/24/2018.
 */

public class ServerGame {
    String serverGameID;
    Deck trainCards;
    Deck destinationCards;
    List<String> history;
    Map<String, Player> playaMap;
    Map<String, Stat> stats;
// playamap after  stats also null
    public ServerGame( String serverGameID, Deck trainCards, Deck destinationCards){
        this.serverGameID=serverGameID;
        this.trainCards=trainCards;
        this.trainCards.shuffle();
        this.destinationCards=destinationCards;
        this.destinationCards.shuffle();
        history=new ArrayList<>();
        playaMap=new HashMap<>();
        stats=new HashMap<>();
//        makeFakeHistory();
    }
    public void addHistory(String note){
        history.add(note);
    }
    public void addPlayer(Player p){
        playaMap.put(p.getUsername(), p);
    }
    public Player getPlayer(String username){return playaMap.get(username);}

    public Stat getStat(String username){
        Stat s=(playaMap.get(username)).makeStat();
        stats.put(username, s);
        return s;
    }

    public List<String> getAllHistory(){
        return history;
    }
    public void removeTrainCardsFromPlayer(String username, List<Integer> cardID){
        //voided by the discard funciton
        //not necessary
    }
    public void removeDestinationCardsFromPlayer(String username, List<Integer> cardID){
        //needs to do something
        //not    now voided by discard function
    }
    public List<DestCard> drawDestinationCards(){
        List<Card> list =destinationCards.draw(3);
        List<DestCard> returnList=new ArrayList<>();
        for (Card c:list) {
            returnList.add((DestCard)c);
        }
        return returnList;
    }
    public Card drawTrainCard(){
        return (trainCards.draw(1)).get(0);
    }

    public void discard(String deckType, int cardID, String username){
        if(deckType.equals(utils.DESTINATION)){
            Card c=RootServerModel.getInstance().getCard(deckType, cardID);
            destinationCards.discard(c);
            playaMap.get(username).removeDestinationCard(c);
        }
        else{
            Card c=RootServerModel.getInstance().getCard(deckType, cardID);
            trainCards.discard(c);
            playaMap.get(username).removeTrainCard(c);
        }
    }

    public String getServerGameID() {
        return serverGameID;
    }

    public void makeFakeHistory(){
        String hi = "first fake history";
        String hello = "second fake history";
        String indeed = "thirdFakeHistory";
        history.add(hi);
        history.add(hello);
        history.add(indeed);
        Map.Entry<String, Player> entry = playaMap.entrySet().iterator().next();
        CommandManager.getInstance().addHistoryCommand(hi, getServerGameID(), entry.getKey());
        CommandManager.getInstance().addHistoryCommand(hello, getServerGameID(), entry.getKey());
        CommandManager.getInstance().addHistoryCommand(indeed, getServerGameID(), entry.getKey());
    }
}
