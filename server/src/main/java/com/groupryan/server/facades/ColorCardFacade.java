package com.groupryan.server.facades;

import com.groupryan.shared.results.CommandResult;

/**
 * Created by arctu on 2/17/2018.
 */

public class ColorCardFacade {

    public CommandResult drawCard(int position, String userID){
        //if position is -1 then it is the deck
        //so then return a new card and let it go back to be updated
        //else it is the position in the array that was clicked
        //so then you get a new card, replace it in the bank, and return it to everyone


        return null;
    }

    public CommandResult updateFaceUp(){


        return null;
    }
}
