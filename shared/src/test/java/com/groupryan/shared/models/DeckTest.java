package com.groupryan.shared.models;

import java.util.ArrayList;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by arctu on 2/24/2018.
 */
public class DeckTest {
    List<Card> deck= new ArrayList<>();
    Deck d=new Deck(deck);
    @org.junit.Before
    public void setUp() throws Exception {
        deck.add(new TrainCard("red", 1));
        deck.add(new TrainCard("red", 2));
        deck.add(new TrainCard("red", 3));
        deck.add(new TrainCard("red", 4));
        deck.add(new TrainCard("red", 5));

    }

    @org.junit.Test
    public void cardsLeft() throws Exception {
        assertEquals(5, d.cardsLeft());
    }

    @org.junit.Test
    public void draw() throws Exception {
        List<Card> drew=d.draw(3);
        int j=0;
    }

    @org.junit.Test
    public void shuffle() throws Exception {
        d.shuffle();
        int j=0;
    }


}