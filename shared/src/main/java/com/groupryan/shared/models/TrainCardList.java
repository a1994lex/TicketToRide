package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 3/26/2018.
 */

public class TrainCardList {
    List<Integer> trainCards = new ArrayList<>();

    public static TrainCardList mapToObject(LinkedTreeMap map) {
        ArrayList<Integer> tc2 = (ArrayList<Integer>) map.get("trainCards");
        return new TrainCardList(tc2);
    }

    public TrainCardList(List<Integer> trainCards){
        this.trainCards = trainCards;
    }

    public List<Integer> getTrainCardList() {
        return trainCards;
    }

    public void setTrainCards(List<Integer> trainCards) {
        this.trainCards = trainCards;
    }
}
