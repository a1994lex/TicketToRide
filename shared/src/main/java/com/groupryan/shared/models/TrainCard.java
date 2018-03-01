package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by arctu on 2/17/2018.
 */

public class TrainCard extends Card {
    String color;
    int cardId;

    public static TrainCard mapToObject(LinkedTreeMap map){
        String color = (String)map.get("color");
        double cardId = (double)map.get("cardId");
        return new TrainCard(color, (int)cardId);
    }

    public TrainCard(String color, int cardID){
        this.color=color;
        cardId=cardID;
    }
}
