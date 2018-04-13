package com.groupryan.server.models;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Deck;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by arctu on 2/24/2018.
 */

public class ServerGame implements java.io.Serializable{
    private String serverGameID;
    private Deck trainCards;
    private Deck destinationCards;
    private List<String> history;
    private Map<String, Player> playaMap;
    private Map<String, Stat> stats;
    private Queue<Player> turnOrder;
    private List<TrainCard> bank;
    private int ready;

    // playamap after  stats also null
    public ServerGame(String serverGameID, Deck trainCards, Deck destinationCards) {
        this.serverGameID = serverGameID;
        this.trainCards = trainCards;
        this.trainCards.shuffle();
        this.destinationCards = destinationCards;
        this.destinationCards.shuffle();
        history = new ArrayList<>();
        playaMap = new HashMap<>();
        stats = new HashMap<>();
        turnOrder = new LinkedList<>();
        bank = new ArrayList<>();
        setRiver();//river is a poker term referring to the cards on the table to see
//        makeFakeHistory();
    }

    // This method is to start counting when a player chooses their destination cards in the beginning
    public void startReady(int i){
        this.ready=0-i;
    }

    // This method updates each time a player draws their first destination cards,
    // when the game is ready to begin, it returns true
    public Boolean updateReady(){
        this.ready++;
        if(this.ready >= 0){
            this.ready++;
            return true;
        }
        return false;
    }

    private void setRiver() {
        List<Card> cards=trainCards.draw(5);
        int locos=0;
        for (Card c:cards) {
            bank.add((TrainCard)c);
            if(((TrainCard) c).getColor().equals(utils.LOCOMOTIVE))
                locos++;
        }
        if(locos>2){
            while(bank.size()>0){
                trainCards.discard(bank.remove( 0));
            }
            setRiver();
        }
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (Player p : playaMap.values()) {
            players.add(p);
        }
        return players;
    }

    public void addHistory(String note) {
        history.add(note);
    }

    public void addPlayer(Player p) {
        playaMap.put(p.getUsername(), p);
        turnOrder.add(p);
    }

    public Player getPlayer(String username) {
        return playaMap.get(username);
    }

    public Stat getStat(String username) {
        Stat s = (playaMap.get(username)).makeStat();
        stats.put(username, s);
        return s;
    }

    public int getNextTurn(){
        Player nextPlayer = this.turnOrder.remove(); // push top player off the queue.
        int turnNum = nextPlayer.getTurn();
        this.turnOrder.add(nextPlayer);
        return turnNum;
    }

    public List<String> getAllHistory() {
        return history;
    }

    public void removeTrainCardsFromPlayer(List<TrainCard> discardable) {
        for (TrainCard tc:discardable) {
            trainCards.discard(tc);
        }
    }

    public void removeAvailableRoute(String username, Route route) {
        for (Map.Entry<String, Player> entry : playaMap.entrySet()) {
            entry.getValue().removeAvailableRoute(route);
            if (playaMap.size() >= 4 && username.equals(entry.getValue().getUsername())) {
                entry.getValue().removeDoubleRoute(route);
            }
            else if (playaMap.size() < 4) {
                entry.getValue().removeDoubleRoute(route);
            }
        }
    }

    public void removeDestinationCardsFromPlayer(String username, List<Integer> cardID) {
        //needs to do something
        //not    now voided by discard function
    }

    public List<DestCard> drawDestinationCards() {
        //reshuffle if <3
        List<Card> list = destinationCards.draw(3);
        List<DestCard> returnList = new ArrayList<>();
        for (Card c : list) {
            returnList.add((DestCard) c);
        }
        return returnList;
    }

    public Card drawTrainCard() {
        List<Card> cards=trainCards.draw(1);
        if(cards==null)
            return null;
        return cards.get(0);
    }

    public void discard(String deckType, int cardID, String username) {
        if (deckType.equals(utils.DESTINATION)) {
            //todo maybe check to see if it is already in the discard pile ebcause if you double click this could cause problems
            Card c = RootServerModel.getInstance().getCard(deckType, cardID);
            destinationCards.discard(c);
            playaMap.get(username).removeDestinationCard(cardID);
        } else {
            Card c = RootServerModel.getInstance().getCard(deckType, cardID);
            trainCards.discard(c);
            playaMap.get(username).removeTrainCard(c);
        }
    }

    public int getDDeckSize() {
        return destinationCards.cardsLeft();
    }

    public int getTDeckSize() {
        return trainCards.cardsLeft();
    }

    public String getServerGameID() {
        return serverGameID;
    }

    public Map<String, Player> getPlayaMap() {
        return playaMap;
    }

    public void makeFakeHistory() {
        String hi = "first fake history";
        String hello = "second fake history";
        String indeed = "thirdFakeHistory";
        history.add(hi);
        history.add(hello);
        history.add(indeed);
        Map.Entry<String, Player> entry = playaMap.entrySet().iterator().next();
        CommandManager.getInstance().addHistoryCommand(hi, getServerGameID(), null);
        CommandManager.getInstance().addHistoryCommand(hello, getServerGameID(), null);
        CommandManager.getInstance().addHistoryCommand(indeed, getServerGameID(), entry.getKey());
    }

    public List<TrainCard> getBankList(){
      return bank;
    }

    public Bank updateFaceUp(int position){
        List<Card> drawnCards=trainCards.draw(1);
        if(drawnCards.size()==0){
            bank.set(position, new TrainCard("none", -2));
            return new Bank(bank);
        }
        TrainCard tc=(TrainCard)drawnCards.get(0);
        bank.set(position, tc);
        bank=locoCheck(bank);
        return new Bank(bank);
    }

    private List<TrainCard> locoCheck(List<TrainCard> b){
        int loco=0;
        for (TrainCard t:b) {
            if(t.getColor().equals(utils.LOCOMOTIVE)){
                loco++;
            }
        }
        if(loco>2){
            List<TrainCard> cosos=new ArrayList<>();
            for(TrainCard t:b){
                trainCards.discard(t);
                cosos.add((TrainCard)trainCards.draw(1).get(0));
            }
            b=cosos;
        }
        else{
            return b;
        }
        return locoCheck(b);
    }


    public Bank getBank(){
        return new Bank(bank);
    }
}
