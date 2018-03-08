package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanm on 3/7/2018.
 */

public class DestCardList {
    double one=-1;
    double two=-1;

    public static DestCardList mapToObject(LinkedTreeMap map){
        double Qone = (double)map.get("one");
        double Qtwo = (double)map.get("two");
        List<Integer> cards = new ArrayList<>();
        if(Qone!=-1)
            cards.add((int)Qone);
        if(Qtwo!=-1)
            cards.add((int)Qtwo);
        return new DestCardList(cards);
    }

    public DestCardList(List<Integer> cardIds){
        if(cardIds.size()>0){
            two=cardIds.get(0);
        }
        if(cardIds.size()>1){
            two=cardIds.get(1);
        }

    }

    public List<Integer> getList(){
        List<Integer> cards=new ArrayList<>();
        if(one!=-1)
            cards.add((int)one);
        cards.add((int)one);
        if(two!=-1)
            cards.add((int)two);
        return cards;
    }
}
