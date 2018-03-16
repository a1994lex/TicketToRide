package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arctu on 3/15/2018.
 */

public class DestCardReturnObject {
    List<DestCard> cards=new ArrayList<>();

    public static DestCardReturnObject mapToObject(LinkedTreeMap map){
        List<DestCard> cardss = new ArrayList<>();
        ArrayList<LinkedTreeMap> tCards = (ArrayList<LinkedTreeMap>) map.get("cards");
        for (LinkedTreeMap tMap : tCards) {
            cardss.add(DestCard.mapToObject(tMap));
        }

        return new DestCardReturnObject(cardss);
    }

    public DestCardReturnObject(List<DestCard> cards){
        this.cards=cards;
    }

    public ArrayList<DestCard> convertToArray(){
        ArrayList<DestCard> tc=new ArrayList<>();
        tc.add(cards.get(0));
        tc.add(cards.get(1));
        tc.add(cards.get(2));
        return tc;
    }
}
