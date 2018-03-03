package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by arctu on 2/24/2018.
 */

public class Stat {

    public static Stat mapToObject(LinkedTreeMap map){
        String username;
        double points;
        double trains;
        double trainCards;
        double destinationCards;
        username = (String)map.get("username");
        points = (double)map.get("points");
        trains = (double)map.get("trains");
        trainCards = (double)map.get("trainCards");
        destinationCards = (double)map.get("destinationCards");
        return new Stat(username, (int) points, (int)trains,(int) trainCards, (int)destinationCards);
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
