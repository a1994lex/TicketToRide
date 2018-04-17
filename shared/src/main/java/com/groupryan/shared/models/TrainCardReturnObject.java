package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by ryanm on 3/24/2018.
 */

public class TrainCardReturnObject implements java.io.Serializable {
    List<TrainCard> cards=new ArrayList<>();

    public static TrainCardReturnObject mapToObject(LinkedTreeMap map){
        List<TrainCard> cardss = new ArrayList<>();
        ArrayList<LinkedTreeMap> tCards = (ArrayList<LinkedTreeMap>) map.get("cards");
        for (LinkedTreeMap tMap : tCards) {
            cardss.add(TrainCard.mapToObject(tMap));
        }

        return new TrainCardReturnObject(cardss);
    }

    public TrainCardReturnObject(List<TrainCard> cards){
        this.cards=cards;
    }

    public ArrayList<TrainCard> convertToArray(){
        ArrayList<TrainCard> tc=new ArrayList<>();
        for(int i=0; i<cards.size(); i++){
            tc.add(cards.get(i));
        }
        return tc;
    }
}
