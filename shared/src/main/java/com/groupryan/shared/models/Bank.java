package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arctu on 3/3/2018.
 */

public class Bank {
    List<TrainCard> bank=new ArrayList<>();

    public static Bank mapToObject(LinkedTreeMap map){
        List<TrainCard> cards = new ArrayList<>();
        ArrayList<LinkedTreeMap> tCards = (ArrayList<LinkedTreeMap>) map.get("bank");
        for (LinkedTreeMap tMap : tCards) {
            cards.add(TrainCard.mapToObject(tMap));
        }

        return new Bank(cards);
    }

    public Bank(List<TrainCard> cards){
       bank=cards;
    }

    public ArrayList<TrainCard> convertToArray(){
        ArrayList<TrainCard> tc=new ArrayList<>();
        tc.add(bank.get(0));
        tc.add(bank.get(1));
        tc.add(bank.get(2));
        tc.add(bank.get(3));
        tc.add(bank.get(4));
        return tc;
    }
}
