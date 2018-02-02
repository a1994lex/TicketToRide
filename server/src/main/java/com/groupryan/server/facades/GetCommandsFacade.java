package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;

import java.util.List;

/**
 * Created by arctu on 1/31/2018.
 */

public class GetCommandsFacade {
    int index;

    public CommandResult getCommandList(){

        //calls the command manager with the index to get the command list

        List<ClientCommand> commands = CommandManager.getInstance().getCommands(index);
        //converts it to a Commandresult
        return null;
    }



}
