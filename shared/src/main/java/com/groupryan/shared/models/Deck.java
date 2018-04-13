package com.groupryan.shared.models;

import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by arctu on 2/24/2018.
 */

public class Deck implements java.io.Serializable{
    List<Card> discardPile;
    List<Card> deck;

    public Deck(List<Card> deck){
        this.deck=deck;
        discardPile=new ArrayList<>();
    }
    public int cardsLeft(){
        return deck.size();
    }
    public List<Card> draw(int amount){
        if(deck.size()<amount){
            Collections.shuffle(discardPile);
            while(discardPile.size()>0) {
                deck.add(discardPile.remove(0));
            }
        }
        List<Card> newCards=new ArrayList<>();
        for(int i=0; i<amount; i++){
            if(deck.size()<1)
                break;
            newCards.add(deck.get(0));
            deck.remove(0);
        }
        return newCards;
    }
    public void shuffle(){
        Collections.shuffle(deck);
    }
    public void discard(Card card){
        discardPile.add(card);
    }

}

