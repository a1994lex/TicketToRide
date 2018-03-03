package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by arctu on 2/24/2018.
 */

public class Stat {

    public static Stat mapToObject(LinkedTreeMap map){
        String username;
        int points;
        int trains;
        int trainCards;
        int destinationCards;
        username = (String)map.get("username");
        points = (Integer)map.get("points");
        trains = (Integer)map.get("trains");
        trainCards = (Integer)map.get("trainCards");
        destinationCards = (Integer)map.get("destinationCards");
        return new Stat(username, points, trains, trainCards, destinationCards);
    }
    String username;
    int points;
    int trains;
    int trainCards;
    int destinationCards;

    public Stat(String username, int points, int trains, int trainCards, int destinationCards){
        this.username=username;
        this.points=points;
        this.trains=trains;
        this.trainCards=trainCards;
        this.destinationCards=destinationCards;
    }

    public String getUsername(){
        return username;
    }
}
