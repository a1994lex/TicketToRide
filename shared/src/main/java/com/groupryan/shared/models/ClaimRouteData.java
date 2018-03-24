package com.groupryan.shared.models;

import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 3/24/2018.
 */

public class ClaimRouteData {
    private List<TrainCard> trainCards;
    private int routeId;
    private String username;

    public ClaimRouteData(List<TrainCard> trainCards, int routeId, String username) {
        this.trainCards = trainCards;
        this.routeId = routeId;
        this.username = username;
    }

    public int getRouteId() {
        return routeId;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

}
