package com.groupryan.shared.models;

/**
 * Created by arctu on 2/17/2018.
 */

public class TrainCard extends Card {
    String color;
    int cardId;

    public TrainCard(String color, int cardID){
        this.color=color;
        cardId=cardID;
    }
}
