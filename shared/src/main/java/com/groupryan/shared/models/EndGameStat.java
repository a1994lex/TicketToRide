package com.groupryan.shared.models;

/**
 * Created by clairescout on 3/21/18.
 */

public class EndGameStat {
    private String username;
    private int totalPoints;
    private int claimedRoutePoints;
    private int longestPath;
    private int reachedDestinationPoints = 0;
    private int unreachedDestNegativePoints = 0;

    public EndGameStat(String username) {
        this.username = username;
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

    public int getReachedDestinationPoints() {
        return reachedDestinationPoints;
    }

    public void increaseReachedDestinationPoints(int destPoints) {
        this.reachedDestinationPoints += destPoints;
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
