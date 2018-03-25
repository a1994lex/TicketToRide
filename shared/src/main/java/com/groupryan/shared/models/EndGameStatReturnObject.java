package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clairescout on 3/24/18.
 */

public class EndGameStatReturnObject {

    List<EndGameStat> endGameStats = new ArrayList<>();

    public static EndGameStatReturnObject mapToObject(LinkedTreeMap map) {
        List<EndGameStat> endGameStats = new ArrayList<>();
        ArrayList<LinkedTreeMap> tEndGameStats = (ArrayList<LinkedTreeMap>) map.get("endGameStats");
        for(LinkedTreeMap tMap : tEndGameStats ) {
            endGameStats.add(EndGameStat.mapToObject(tMap));
        }
        return new EndGameStatReturnObject(endGameStats);
    }

    public EndGameStatReturnObject(List<EndGameStat> endGameStats){
        this.endGameStats = endGameStats;
    }

    public List<EndGameStat> getEndGameStats() {
        return endGameStats;
    }

    public void setEndGameStats(List<EndGameStat> endGameStats) {
        this.endGameStats = endGameStats;
    }
}
