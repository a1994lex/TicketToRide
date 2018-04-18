package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 4/11/18.
 */

public class ClientFacingGame {
    String gameId;
    Player myPlayer;
    ArrayList<Route> availableRoutes;
    ArrayList<String> history;
    ArrayList<Chat> chat;
    ArrayList<TrainCard> bankCards;
    HashMap<String, Stat> stats;
    Map<String, String> playersColors;
    Integer currentTurn;
    List<Route> claimedRoutes;
    Boolean original=true;
    public ClientFacingGame(String gameId, Player myPlayer){
        this.gameId = gameId;
        this.myPlayer = myPlayer;
        this.history = new ArrayList<>();
        this.chat = new ArrayList<>();
        this.bankCards = new ArrayList<>();
        this.claimedRoutes = new ArrayList<>();
        availableRoutes =new ArrayList<>();
        this.stats = new HashMap<>();
    }
    private ClientFacingGame(String gameId, Player myPlayer, ArrayList<Route> availableRoutes,
                       ArrayList<String> history, ArrayList<TrainCard> bankCards,
                       HashMap<String, Stat> stats, Map<String, String> playersColors,
                       Integer currentTurn, List<Route> claimedRoutes, Boolean original){
        this.gameId = gameId;
        this.myPlayer = myPlayer;
        this.availableRoutes = availableRoutes;
        this.history = history;
        this.chat = new ArrayList<>();
        this.bankCards = bankCards;
        this.stats = stats;
        this.playersColors = playersColors;
        this.currentTurn = currentTurn;
        this.claimedRoutes = claimedRoutes;
        this.original = original;
    }
    public static ClientFacingGame mapToObject(LinkedTreeMap map){
        String gameId = (String)map.get("gameId");
        Player myPlayer = Player.mapToObject((LinkedTreeMap)map.get("myPlayer"));
        ArrayList<Route> availableRoutes = new ArrayList<>();
        ArrayList<Route> claimedRoutes = new ArrayList<>();
        HashMap<String, Stat> stats = new HashMap<>();
        ArrayList<TrainCard> bankCards = new ArrayList<>();
        ArrayList<LinkedTreeMap> tARoutes = (ArrayList<LinkedTreeMap>) map.get("availableRoutes");
        ArrayList<LinkedTreeMap> tCRoutes = (ArrayList<LinkedTreeMap>) map.get("claimedRoutes");
        ArrayList<LinkedTreeMap> tCards = (ArrayList<LinkedTreeMap>) map.get("bankCards");
        Map<String, LinkedTreeMap> tStats = (Map<String, LinkedTreeMap>) map.get("stats");
        for (LinkedTreeMap tARoute: tARoutes){
            availableRoutes.add(Route.mapToObject(tARoute));
        }
        for (LinkedTreeMap tMap : tCards) {
            bankCards.add(TrainCard.mapToObject(tMap));
        }
        for (Map.Entry<String, LinkedTreeMap> entry : tStats.entrySet()){
            stats.put(entry.getKey(), Stat.mapToObject(entry.getValue()));
        }
        for (LinkedTreeMap tCRoute: tCRoutes){
            claimedRoutes.add(Route.mapToObject(tCRoute));
        }

        Map<String, String> playersColors = (Map<String, String>) map.get("playersColors");
        ArrayList<String> history = (ArrayList<String>) map.get("history");
        double currentTurn = (double) map.get("currentTurn");
        Boolean original = (Boolean) map.get("original");
        return new ClientFacingGame(gameId, myPlayer, availableRoutes, history, bankCards, stats, playersColors,
                (int)currentTurn, claimedRoutes, original);
    }

    public void setAvailableRoutes(ArrayList<Route> availableRoutes) {
        this.availableRoutes = availableRoutes;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public void setChat(ArrayList<Chat> chat) {
        this.chat = chat;
    }

    public void setBankCards(ArrayList<TrainCard> bankCards) {
        this.bankCards = bankCards;
    }

    public void setStats(HashMap<String, Stat> stats) {
        this.stats = stats;
    }

    public void setPlayersColors(Map<String, String> playersColors) {
        this.playersColors = playersColors;
    }

    public void setCurrentTurn(Integer currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setClaimedRoutes(List<Route> claimedRoutes) {
        this.claimedRoutes = claimedRoutes;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }


    public String getGameId() {
        return gameId;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public ArrayList<Route> getAvailableRoutes() {
        return availableRoutes;
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

    public Map<String, String> getPlayersColors() {
        return playersColors;
    }

    public Integer getCurrentTurn() {
        return currentTurn;
    }

    public List<Route> getClaimedRoutes() {
        return claimedRoutes;
    }

    public Boolean getOriginal() {
        return original;
    }


}
