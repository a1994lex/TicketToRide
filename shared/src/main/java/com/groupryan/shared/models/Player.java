package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by arctu on 2/17/2018.
 */

public class Player {
    String color;
    List<Route> routes;
    int points;
    List<DestCard> destCards;
    List<TrainCard> trainCards;
    int trainPieces;
    String username;
    int turn;

    public static Player mapToObject(LinkedTreeMap map) {
        String username = (String) map.get("username");
        String color = (String) map.get("color");
        double turn=(double)map.get("turn");
//        double points = (double) map.get("points");
//        // TODO: fix the route map to object
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
        return new Player(color, destCards, trainCards, username, (int)turn);


    }

    public Player(String color, List<DestCard> destCards, List<TrainCard> trainCards, String username, int turn) {
        this.color = color;
        this.routes = new ArrayList<>();
        this.points = 0;
        this.destCards = destCards;
        this.trainCards = trainCards;
        this.trainPieces = 45;
        this.username = username;
        this.turn=turn;
    }

    public Player(String username, String color) { // for testing only
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void removeDestinationCard(Card c) {
        destCards.remove(c);
    }

    public void removeTrainCard(Card c) {
        destCards.remove(c);
    }

    public Stat makeStat() {
        return new Stat(username, points, trainPieces, trainCards.size(), destCards.size());
    }

    public void addDestCards(List<DestCard> cards) {
        for (DestCard c : cards) {
            destCards.add(c);
        }
    }

    public void addTrainCards(List<TrainCard> cards) {
        for (TrainCard c : cards) {
            trainCards.add(c);
        }
    }

}