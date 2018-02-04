package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class JoinGameFacade {
    String userId;
    String gameId;
    String result;

    public CommandResult joinGame(String gameId, String userId){
        result=RootServerModel.getInstance()._addUserToGame(gameId, userId);
        CommandResult cm=new CommandResult();
        cm.addClientCommand(createReturnCommand(gameId, userId));
        cm.setResultType(result);
        //if not joined set = to false
        //this should have the id too, allws the player to join the game
        //adds them to the game,
        //returns the command

        return cm;}
    private ClientCommand createReturnCommand(String gameId, String userId){
        return CommandManager.getInstance().makeJoinGameCommand(gameId, userId);}
}
