package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.List;

/**
 * Created by arctu on 2/17/2018.
 */

public class DestinationCardFacade {

    public CommandResult discard(List<Integer> cardIDs, String username){
        CommandResult cr = new CommandResult();
        //do the discard
        for (int cardID:cardIDs) {
            addToDestCardDiscard(cardID, username);

            //add to destination card discard pile
            //remove card from player//does at same time as deck discard
        }
        cr.addClientCommand(CommandManager.getInstance().makeDiscardDestinationCardCommand(cardIDs, username));
        //CommandManager.getInstance().add(getStat(username));
//playa stat updates, needs to be sent to everyone
        String history = username + " discarded " + cardIDs.size() + " cards.";
        ServerGame serverGame = RootServerModel.getInstance().getServerGame(username);
        serverGame.addHistory(history);
        cr.addClientCommand(CommandManager.getInstance().addHistoryCommand(history, serverGame.getServerGameID(), username));

        //create the 2 necessary commands
        return cr;
    }

    public CommandResult drawDestinationCards(String username){
        CommandResult cr = new CommandResult();
        ServerGame sg=RootServerModel.getInstance().getServerGame(username);
        List<DestCard> cards =sg.drawDestinationCards();
        sg.getPlayer(username).addDestCards(cards);
        cr.addClientCommand(CommandManager.getInstance().makeDrawThreeCardsCommand(cards));
        //update the history by making a command saying that this username drew three cards
        String history = username + " drew three destination cards.";
        sg.addHistory(history);
        cr.addClientCommand(CommandManager.getInstance().addHistoryCommand(history, sg.getServerGameID(), username));
        //CommandManager.getInstance().add(getStat(username));
        CommandManager.getInstance().makeStatCommand(sg.getServerGameID(), null);
        return cr;
    }

    private void addToDestCardDiscard(int cardID, String username){
        ServerGame sg=RootServerModel.getInstance().getServerGame(username);
        sg.discard(utils.DESTINATION, cardID, username);

    }

    private Stat getStat(String username){
        return (RootServerModel.getInstance().getServerGame(username)).getStat(username);
    }
}


