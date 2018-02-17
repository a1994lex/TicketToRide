package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arctu on 2/17/2018.
 */

public class Playa {
    String color;
    List<Route> routes;
    int points;
    List<DestCard> destCards;
    List<TrainCard> trainCards;
    int trainPieces;
    String username;

    public Playa(String color  , List<Route> routes, List<DestCard> destCards, List<TrainCard> trainCards, String username){
        this.color=color;
        this.routes=routes;
        this.points=0;
        this.destCards=destCards;//should it be new or should it have the 3 alreadyin it?
        this.trainCards=trainCards;//same question
        this.trainPieces=45;
        this.username=username;
    }

}