package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by clairescout on 3/21/18.
 */

public class EndGameStat implements java.io.Serializable {
    private String username;
    private int totalPoints;
    private int claimedRoutePoints;
    private int longestPath = 0;
    private int reachedDestPoints = 0;
    private int unreachedDestNegativePoints = 0;

    public EndGameStat(String username) {
        this.username = username;
    }


    public static EndGameStat mapToObject(LinkedTreeMap map) {
        String username;
        double totalPoints;
        double claimedRoutePoints;
        double longestPath;
        double reachedDestPoints;
        double unreachedDestNegativePoints;
        username = (String) map.get("username");
        totalPoints = (double) map.get("totalPoints");
        claimedRoutePoints = (double) map.get("claimedRoutePoints");
        longestPath = (double) map.get("longestPath");
        reachedDestPoints = (double) map.get("reachedDestPoints");
        unreachedDestNegativePoints = (double) map.get("unreachedDestNegativePoints");
        return new EndGameStat(username, (int) totalPoints, (int) claimedRoutePoints, (int) longestPath,(int) reachedDestPoints, (int) unreachedDestNegativePoints );


    }

    public EndGameStat(String username, int totalPoints, int claimedRoutePoints, int longestPath, int reachedDestPoints, int unreachedDestNegativePoints) {
        this.username = username;
        this.totalPoints = totalPoints;
        this.claimedRoutePoints = claimedRoutePoints;
        this.longestPath = longestPath;
        this.reachedDestPoints = reachedDestPoints;
        this.unreachedDestNegativePoints = unreachedDestNegativePoints;
    }

    public void increaseTotalPoints(int value){
        totalPoints += value;
    }

    public void decreaseTotalPoints(int value){
        totalPoints -= value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getClaimedRoutePoints() {
        return claimedRoutePoints;
    }

    public void setClaimedRoutePoints(int claimedRoutePoints) {
        this.claimedRoutePoints = claimedRoutePoints;
    }

    public int getLongestPath() {
        return longestPath;
    }

    public void setLongestPath(int longestPath) {
        this.longestPath = longestPath;
    }

    public int getReachedDestPoints() {
        return reachedDestPoints;
    }

    public void increaseReachedDestPoints(int destPoints) {
        this.reachedDestPoints += destPoints;
        this.totalPoints += destPoints;
    }

    public int getUnreachedDestNegativePoints() {
        return unreachedDestNegativePoints;
    }

    public void increaseUnreachedDestNegativePoints(int unreachedDestNegativePoints) {
        this.unreachedDestNegativePoints += unreachedDestNegativePoints;
        this.totalPoints -= unreachedDestNegativePoints;
    }


}
