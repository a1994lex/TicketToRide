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
        ArrayList<Double> tc2 = (ArrayList<Double>) map.get("trainCards");
        ArrayList<Integer> tcList = new ArrayList<>();
        for (int i = 0; i < tc2.size(); i++) {
            int val = tc2.get(i).intValue();
            tcList.add(val);
        }
        return new TrainCardList(tcList);
    }

    public TrainCardList(List<Integer> trainCards){
        this.trainCards = trainCards;
    }

    public List<Integer> getTrainCards() {
        return trainCards;
    }

    public List<Double> getTrainCardsDoubles() {
        List<Double> trainCardsDoubles = new ArrayList<>();
        for (int i = 0; i < trainCards.size(); i++) {
            trainCardsDoubles.add(trainCards.get(i).doubleValue());
        }
        return trainCardsDoubles;
    }

    public void setTrainCards(List<Integer> trainCards) {
        this.trainCards = trainCards;
    }
}
