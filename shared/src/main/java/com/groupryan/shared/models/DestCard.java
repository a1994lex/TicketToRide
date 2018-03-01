package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

/**
 * Created by arctu on 2/17/2018.
 */

public class DestCard extends Card {


    public static DestCard mapToObject(LinkedTreeMap map){
        double value;
        String cityOne;
        String cityTwo;
        double cardID;
        value = (double)map.get("value");
        cityOne = (String)map.get("cityOne");
        cityTwo = (String)map.get("cityTwo");
        cardID = (double)map.get("cardID");
        return new DestCard((int)value, cityOne, cityTwo, (int)cardID);
    }


    int value;
    String cityOne;
    String cityTwo;
    int cardId;
    public DestCard(int value, String one, String two, int cardId){
        this.value=value;
        cityOne=one;
        cityTwo=two;
        this.cardId=cardId;
    }

}
