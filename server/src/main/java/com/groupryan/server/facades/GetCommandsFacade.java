package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.commands.ClientCommand;

import java.util.List;

/**
 * Created by arctu on 1/31/2018.
 */

public class GetCommandsFacade {
    int index;

    List<ClientCommand> commands = CommandManager.getInstance().getCommands(index);

}
