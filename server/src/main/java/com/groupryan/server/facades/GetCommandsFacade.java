package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by arctu on 1/31/2018.
 */

public class GetCommandsFacade {
    private int index;

    public CommandResult getCommandList(){

        //calls the command manager with the index to get the command list
        CommandResult cr=new CommandResult();
        cr.setClientCommands(CommandManager.getInstance().getCommands(index));
        //converts it to a Commandresult
        cr.setResultType("Commands");
        return cr;
    }



}
