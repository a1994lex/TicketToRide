package com.groupryan.server;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ClientCommandFactory;
import com.groupryan.shared.commands.IClientCommand;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CommandManager {

    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    ClientCommandFactory factory;

    // TODO: How do we store the commands that each user needs to get?

    public CommandManager() {
        this.factory = new ClientCommandFactory();
    }

    public List<ClientCommand> getCommands(int index) {
        return null;
    }

    public ClientCommand makeJoinGameCommand(Game game, User user, Color userColor) {
        ClientCommand command = factory.createJoinGameCommand(game, user, userColor);
        return command;
    }

    public ClientCommand makeCreateGameCommand(Game game) {
        ClientCommand command = factory.createCreateGameCommand(game);
        return command;
    }

    public ClientCommand makeStartGameCommand(Game game) {
        ClientCommand command = factory.createStartGameCommand(game);
        return command;
    }

    public ClientCommand makeLoginCommand(User user) {
        ClientCommand command = factory.createLoginCommand(user);
        return command;
    }

    public ClientCommand makeRegisterCommand(User user) {
        ClientCommand command = factory.createRegisterCommand(user);
        return command;

    }
}