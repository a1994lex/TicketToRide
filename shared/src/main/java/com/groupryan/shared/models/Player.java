package com.groupryan.shared.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arctu on 2/17/2018.
 */

public class Player {
    String color;
    List<Route> routes;
    int points;
    List<DestCard> destCards;
    List<Card> trainCards;
    int trainPieces;
    String username;

    public Player(String color, List<DestCard> destCards, List<Card> trainCards, String username){
        this.color=color;
        this.routes=new ArrayList<>();
        this.points=0;
        this.destCards=destCards;
        this.trainCards=trainCards;
        this.trainPieces=45;
        this.username=username;
    }

    public String getUsername(){
        return username;
    }
    public void removeDestinationCard(Card c){
        destCards.remove(c);
    }
    public void removeTrainCard(Card c){
        destCards.remove(c);}

    public Stat makeStat(){
        return new Stat(username, points, trainPieces, trainCards.size(), destCards.size());
    }

    public void addDestCards(List<DestCard> cards){
        for (DestCard c:cards) {
            destCards.add(c);
        }
    }

    public void addTrainCards(List<TrainCard> cards){
        for (Card c:cards) {
        trainCards.add(c);
    }}

}