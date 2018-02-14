package com.groupryan.server;

import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ClientCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CommandManager {

    Map<User, List<ClientCommand>> usersCommands = new HashMap<>();
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null) {

            // TODO make usersCommands Map

            instance = new CommandManager();
        }
        return instance;
    }

    ClientCommandFactory factory;


    public CommandManager() {
        this.factory = new ClientCommandFactory();
    }

    public List<ClientCommand> getCommands(User user) {
        List<ClientCommand> commands = this.usersCommands.get(user);
        this.usersCommands.put(user, new ArrayList<>());
        return commands;
    }

    private void _addCommandToMap(ClientCommand command, Object o, String commandType) {
        for (Map.Entry<User, List<ClientCommand>> entry : this.usersCommands.entrySet()) {
            switch (commandType) {
                case utils.JOIN_GAME:
                    User u = (User) o;
                    if (!entry.getKey().equals(u)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(u, commands);
                    }
                    break;
                case utils.CREATE_GAME:
                    Game g = (Game) o;
                    String userId = (String) new ArrayList(g.getUsers().keySet()).get(0);
                    User user = RootServerModel.getUser(userId);
                    if (!entry.getKey().equals(user)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(user, commands);
                    }
                    break;
                case utils.START_GAME:
                    Game g2 = (Game) o;
                    String userId2 = (String) new ArrayList(g2.getUsers().keySet()).get(0);
                    User user2 = RootServerModel.getUser(userId2);
                    if (!entry.getKey().equals(user2)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(user2, commands);
                    }
                    break;
            }
        }
    }

    public ClientCommand makeJoinGameCommand(Game game, User user, String userColor) {
        ClientCommand command = factory.createJoinGameCommand(game, user, userColor);
        this._addCommandToMap(command, user, utils.JOIN_GAME);
        return command;
    }

    public ClientCommand makeCreateGameCommand(Game game) {
        ClientCommand command = factory.createCreateGameCommand(game);
        this._addCommandToMap(command, game, utils.CREATE_GAME);
        return command;
    }

    public ClientCommand makeStartGameCommand(Game game) {
        ClientCommand command = factory.createStartGameCommand(game);
        this._addCommandToMap(command, game, utils.START_GAME);
        return command;
    }

    public ClientCommand makeLoginCommand(User user) {
        ClientCommand command = factory.createLoginCommand(user);
        this.usersCommands.put(user, new ArrayList<>());
        return command;
    }

    public ClientCommand makeRegisterCommand(User user) {
        ClientCommand command = factory.createRegisterCommand(user);
        this.usersCommands.put(user, new ArrayList<>());
        return command;

    }

}