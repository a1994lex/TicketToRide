package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
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
        TrainCard tc=null;
        String history;
        Boolean river=false;
        //if position is -1 then it is the deck
        //so then return a new card and let it go back to be updated
        if(position==-1){
            tc= (TrainCard)sg.drawTrainCard();
            //set history
            history = userID + " drew from the bank.";
        }
        //else it is the position in the array that was clicked
        //so then you get a new card, replace it in the bank, and return it to everyone
        else {
            river=true;
            tc=sg.getBankList().get(position-1);
            history= userID + " drew a face up "+ tc.getColor() + " card.";

        }
        //add card to player serverside
        sg.getPlayer(userID).addTrainCard(tc);
        //send this card back only to the person who drew it
        cr.addClientCommand(CommandManager.getInstance().makeDrawColorCardCommand(tc));
        //update their stats
        CommandManager.getInstance().makeStatCommand(sg.getServerGameID(), sg.getStat(userID));
        //add history
        sg.addHistory(history);
        CommandManager.getInstance().addHistoryCommand(history, sg.getServerGameID(), null);
        if(river){
            Bank newBank=sg.updateFaceUp(position);
            CommandManager.getInstance().makeBankCommand(sg.getServerGameID(), newBank);
        }
        CommandManager.getInstance().makeTDeckCommand(sg.getServerGameID(), sg.getTDeckSize());
        return cr;
    }

    public CommandResult updateFaceUp(){
        //yah does nothin
        return null;
    }
}
