package com.groupryan.client.models;

import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by alex on 2/26/18.
 * - gameId: String
 * - history: List<String>
 * - stats: Map<String, Stat>
 * - players: List<String>
 * - chat: List<String>
 * - bankCards: List<Card>
 */

public class ClientGame extends Observable {
    String gameId;
    Player myPlayer;
    int DDeckSize=30;
    int TDeckSize=110;
    ArrayList<Route> availableRoutes;
    ArrayList<String> history;
    ArrayList<Chat> chat;
    ArrayList<TrainCard> bankCards;
    HashMap<String, Stat> stats;
    Map<String, String> playersColors;
    Integer currentTurn;
    Map<Integer, String> turnOrderMap;
    private List<Route> claimedRoutes;
    Boolean original=true;
    List<EndGameStat> endGameStats;
    private boolean showRoutes = true;
    String winner;

    public ClientGame(Game game, Player player) {
        this.history = new ArrayList<>();
        this.chat = new ArrayList<>();
        this.bankCards = new ArrayList<>();
        this.claimedRoutes = new ArrayList<Route>();

        availableRoutes =new ArrayList<>();
        this.gameId = game.getGameId();
        this.myPlayer = player;
        this.currentTurn = -1;
        this.stats = new HashMap<>();
        this.playersColors = game.getUsers();

    }

    public String getGameId() {
        return gameId;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public ArrayList<Chat> getChat() {
        return chat;
    }

    public ArrayList<TrainCard> getBankCards() {
        return bankCards;
    }

    public HashMap<String, Stat> getStats() {
        return stats;
    }

    public void updateChat(Chat mychat) {
        chat.add(mychat);
        setChanged();
        notifyObservers(utils.CHAT);
    }

    public void setBank(Bank bank) {
        bankCards = bank.convertToArray();
        setChanged();
        notifyObservers(utils.BANK);
    }

    public void updateHistory(String msg) {
        history.add(msg);
        setChanged();
        notifyObservers(utils.HISTORY);
    }

    public void updateStat(Stat stat) {
        stats.put(stat.getUsername(), stat);
        setChanged();
        notifyObservers(utils.STAT);
    }

    public Map<String, String> getPlayersColors() {
        return playersColors;
    }

    public void setPlayersColors(Map<String, String> playersColors) {
        this.playersColors = playersColors;
    }

    public void discardDestCards(DestCardList destCardList) {
        List<Integer> cardIDs = destCardList.getList();
        for (Integer cardID : cardIDs) {
            myPlayer.removeDestinationCard(cardID);
        }
        original=false;
        setChanged();
        notifyObservers(utils.DISCARD_DESTCARD);
    }

    public Boolean getOriginal(){
        return original;
    }

    public void updateDestCards(ArrayList<DestCard> cards){
        myPlayer.addDestCards(cards);
        setChanged();
        notifyObservers(utils.DRAW_THREE_CARDS);
    }

    public void updateTrainCards(ArrayList<TrainCard> cards){
        myPlayer.addTrainCards(cards);
        setChanged();
        notifyObservers(utils.DRAW_COLOR_CARD);
    }

    public void discardTrainCards(ArrayList<TrainCard> cards){
        myPlayer.removeTrainCards(cards);
        setChanged();
        notifyObservers(utils.DISCARD_TRAINCARD);
    }

    public void setCurrentTurn(int currentTurn){
        this.currentTurn = currentTurn;
        setChanged();
        notifyObservers(utils.NEW_TURN);
    }

    public Integer getCurrentTurn() {
        return currentTurn;
    }

    public Map<Integer, String> getTurnOrderMap() {
        return turnOrderMap;
    }

//    public void endTurn() {
//        this.currentTurn += 1;
//    }

    public boolean isMyTurn(){
        int a= 0-myPlayer.getTurn();
        int b= 0-currentTurn;
        if (a==b){
            return true;
        }
        return false;
    }

    public int getDDeckSize() {
        return DDeckSize;
    }

    public void setDDeckSize(int DDeckSize) {
        this.DDeckSize = DDeckSize;
        setChanged();
        notifyObservers(utils.BANK);
    }

    public int getTDeckSize() {
        return TDeckSize;
    }

    public void setTDeckSize(int TDeckSize) {
        this.TDeckSize = TDeckSize;
        setChanged();
        notifyObservers(utils.BANK);
    }

    public ArrayList<Route> getClaimedRoutesList() {
        ArrayList<Route> routesClaimed = new ArrayList<>();
        for (Route r : claimedRoutes) {
            routesClaimed.add(r);
        }
        return routesClaimed;
    }

    public ArrayList<Route> getAvailableRoutes() {
        return availableRoutes;
    }

    public void addClaimedRoute(String username, Route route) {
    //    if(RootClientModel.getCurrentGame().getMyPlayer().getUsername().equals(username)) {
            claimedRoutes.add(route);
     //   }
        for (int i = 0; i < availableRoutes.size(); i++) {
            if (availableRoutes.get(i).getId() == route.getId()) {
                availableRoutes.get(i).setAvailable(false);
            }
        }
        setChanged();
        notifyObservers(utils.REDRAW_ROUTES);
    }

    public Route getAnAvailableRoute(int routeId) {
        Route route = null;
        for (int i = 0; i < availableRoutes.size(); i++) {
            if (availableRoutes.get(i).getId() == routeId) {
                route = availableRoutes.get(i);
            }
        }
        return route;
    }

    public void setAvailableRoutes(ArrayList<Route> availableRoutes) {
        this.availableRoutes = availableRoutes;
    }
      
    public List<EndGameStat> getEndGameStats() {
        return endGameStats;
    }

    public void setEndGameStats(List<EndGameStat> endGameStats) {
        this.endGameStats = endGameStats;
        setChanged();
        notifyObservers(utils.GAME_OVER);
    }
      
//    public void addRoute( Route r){
//        routes.add(r);
//        setChanged();
//        //route or redraw routes?
//       // notifyObservers(utils.);
//    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setShowRoutes(boolean showRoutes) {
        this.showRoutes = showRoutes;
        if (showRoutes) {
            setChanged();
            notifyObservers(utils.REDRAW_ROUTES);
        }
    }
}
