package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */

public class JoinGameFacade {

    public CommandResult joinGame(Game game, User user, Color userColor) {
        String result = RootServerModel.getInstance().addUserToGame(game, user, userColor);
        CommandResult cm = new CommandResult();
        cm.addClientCommand(createReturnCommand(game, user, userColor));
        cm.setResultType(result);

        return cm;
    }

    private ClientCommand createReturnCommand(Game game, User user, Color userColor) {
        return CommandManager.getInstance().makeJoinGameCommand(game, user, userColor);
    }
}
