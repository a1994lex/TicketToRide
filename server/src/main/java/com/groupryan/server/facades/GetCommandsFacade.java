package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

/**
 * Created by arctu on 1/31/2018.
 */

public class GetCommandsFacade {

    public CommandResult getCommandList(User user) {
        CommandResult cr = new CommandResult();
        cr.setClientCommands(CommandManager.getInstance().getCommands(user));
        cr.setResultType(utils.VALID);
        return cr;
    }
}
