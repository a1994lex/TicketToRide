package com.groupryan.server.facades;

import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by arctu on 2/17/2018.
 */

public class ColorCardFacade {

    public CommandResult drawCard(int position, String userID){
        CommandResult cr = new CommandResult();
        RootServerModel root = RootServerModel.getInstance();
        ServerGame sg=root.getServerGame(userID);
        if(position==-1){
            TrainCard tc= (TrainCard)sg.drawTrainCard();
        }
        else {
            TrainCard selected=sg.getBankList().get(position-1);
            Bank newBank=sg.updateFaceUp(position);
        }
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
