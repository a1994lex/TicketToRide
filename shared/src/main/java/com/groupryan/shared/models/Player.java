package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arctu on 2/17/2018.
 */

public class Player implements  java.io.Serializable{
    String color;
    List<Route> routes;
    List<Route> availableRoutes;
    int points;
    List<DestCard> destCards;
    List<TrainCard> trainCards;
    int trainPieces;
    String username;
    int turn;
    Boolean endGame;

    public static Player mapToObject(LinkedTreeMap map) {
        String username = (String) map.get("username");
        String color = (String) map.get("color");
        double turn = (double) map.get("turn");
//        double points = (double) map.get("points");
//        double trainpieces = (double) map.get("trainPieces");
        List<DestCard> destCards = new ArrayList<>();
        ArrayList<LinkedTreeMap> dcards = (ArrayList<LinkedTreeMap>) map.get("destCards");
        for (LinkedTreeMap cardMap : dcards) {
            destCards.add(DestCard.mapToObject(cardMap));
        }
        List<TrainCard> trainCards = new ArrayList<>();
        ArrayList<LinkedTreeMap> tCards = (ArrayList<LinkedTreeMap>) map.get("trainCards");
        for (LinkedTreeMap tMap : tCards) {
            trainCards.add(TrainCard.mapToObject(tMap));
        }
        Boolean endGame = (Boolean) map.get("endGame");
        return new Player(color, destCards, trainCards, username, (int) turn, endGame);


    }

    public Player(String color, List<DestCard> destCards, List<TrainCard> trainCards, String username, int turn, Boolean endGame) {
        this.color = color;
        this.routes = new ArrayList<>();
        this.points = 0;
        this.destCards = destCards;
        this.trainCards = trainCards;
        this.trainPieces = 45;
        this.username = username;
        this.turn = turn;
        this.endGame = endGame;
        this.availableRoutes = new ArrayList<>();
    }

    public Player(String username, String color) { // for testing only
        this.username = username;
        this.color = color;
    }

    public int getTurn(){
        return this.turn;
    }

    public String getUsername() {
        return username;
    }

    public void removeDestinationCard(Integer cardId) {
        destCards.removeIf(card -> card.getCardId() == cardId);
    }

    public void removeTrainCard(Card c) {
        //trainCards.removeIf(card -> card.getID() == c.getID());card needs getID funciton
        trainCards.remove(c);
    }

    public Stat makeStat() {
        return new Stat(username, turn, points, trainPieces, trainCards.size(), destCards.size());
    }

    public void addDestCards(List<DestCard> cards) {
        for (DestCard c : cards) {
            destCards.add(c);
        }
    }

    public void addTrainCard(TrainCard tc){
        trainCards.add(tc);
    }

    public void addTrainCards(List<TrainCard> cards) {
        for (TrainCard c : cards) {
            trainCards.add(c);
        }
    }

    public void removeTrainCards(ArrayList<TrainCard> cards){
        for(TrainCard c: cards){
           // removeTrainCard(card);
            trainCards.removeIf(card -> card.getID() == c.getID());
        }
    }

    public void removeTrains(int cost) {
        this.trainPieces = trainPieces - cost;
    }

    public void addPoints(int amount){
        points+=amount;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }

    public void setAvailableRoutes(Map<Integer, Route> routeMap) {
        for (Map.Entry<Integer, Route> entry : routeMap.entrySet()) {
            this.availableRoutes.add(entry.getValue());
        }
    }

    public void addAvailableRoute(Route route) {
        availableRoutes.add(route);
    }

    public void removeAvailableRoute(Route route) {
        for (int i = 0; i < availableRoutes.size(); i++) {
            if (availableRoutes.get(i).getId() == route.getId()) {
                availableRoutes.remove(i);
                break;
            }
        }
    }

    public void removeDoubleRoute(Route route) {
        for (int i = 0; i < availableRoutes.size(); i++) {
            if (availableRoutes.get(i).getCityOne().equals(route.getCityOne())
                    && availableRoutes.get(i).getCityTwo().equals(route.getCityTwo())) {
                availableRoutes.remove(i);
                break;
            }
        }
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDestCards(List<DestCard> destCards) {
        this.destCards = destCards;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public int getTrainPieces() {
        return trainPieces;
    }

    public void setTrainPieces(int trainPieces) {
        this.trainPieces = trainPieces;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEndGame() {
        return endGame;
    }

    public void setEndGame(Boolean endGame) {
        this.endGame = endGame;
    }

    public List<DestCard> getDestCards() {
        return this.destCards;
    }

    public Map<String, Integer> getTrainCardsMap() {

        //TODO move this somewhere else
        Map<String, Integer> trainCardsMap = new HashMap<>();
        trainCardsMap.put(utils.RED, 0);
        trainCardsMap.put(utils.ORANGE, 0);
        trainCardsMap.put(utils.YELLOW, 0);
        trainCardsMap.put(utils.GREEN, 0);
        trainCardsMap.put(utils.BLUE, 0);
        trainCardsMap.put(utils.PINK, 0);
        trainCardsMap.put(utils.WHITE, 0);
        trainCardsMap.put(utils.BLACK, 0);
        trainCardsMap.put(utils.LOCOMOTIVE, 0);

        for (TrainCard tCard : this.trainCards) {
            trainCardsMap.put(tCard.getColor(), trainCardsMap.get(tCard.getColor()) + 1);
        }
        return trainCardsMap;
    }

    public void addRoute(Route r){
        routes.add(r);
    }
}