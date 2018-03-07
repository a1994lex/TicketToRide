package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class DestCardList {

    List<Integer> destCardList = new ArrayList<>();

    public static DestCardList mapToObject(LinkedTreeMap map) {
        List<Integer> destCardList = new ArrayList<>();
        ArrayList<LinkedTreeMap> destCardInts = (ArrayList<LinkedTreeMap>) map.get("destCardList");
        for (LinkedTreeMap cardMap : destCardInts) {
//            destCardList.add(/** something with cardMap but get the Integer from it**/);
        }

        return new DestCardList(destCardList);
    }

    public DestCardList(List<Integer> destCardList) {
        this.destCardList = destCardList;
    }

    public List<Integer> getDestCardList() {
        return destCardList;
    }
}
