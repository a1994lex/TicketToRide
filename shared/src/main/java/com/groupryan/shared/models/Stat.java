package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by arctu on 2/24/2018.
 */

public class Stat {

    public static Stat mapToObject(LinkedTreeMap map) {
        String username;
        double turn;
        double points;
        double trains;
        double trainCards;
        double destinationCards;
        username = (String) map.get("username");
        points = (double) map.get("points");
        trains = (double) map.get("trains");
        turn = (double) map.get("turn");
        trainCards = (double) map.get("trainCards");
        destinationCards = (double) map.get("destinationCards");
        return new Stat(username, (int) turn, (int) points, (int) trains, (int) trainCards, (int) destinationCards);
    }

    String username;
    int points;
    int turn;
    int trains;
    int trainCards;
    int destinationCards;

    public Stat(String username, int turn, int points, int trains, int trainCards, int destinationCards) {
        this.username = username;
        this.points = points;
        this.turn = turn; // turn
        this.trains = trains;
        this.trainCards = trainCards;
        this.destinationCards = destinationCards;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public int getTrains() {
        return trains;
    }

    public int getTrainCards() {
        return trainCards;
    }

    public int getTurn() {
        return turn;
    }

    public int getDestinationCards() {
        return destinationCards;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTrains(int trains) {
        this.trains = trains;
    }

    public void setTrainCards(int trainCards) {
        this.trainCards = trainCards;
    }

    public void setDestinationCards(int destinationCards) {
        this.destinationCards = destinationCards;
    }
}
