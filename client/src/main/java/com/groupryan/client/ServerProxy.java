package com.groupryan.client;

import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.util.List;

public class ServerProxy implements IServer {

    //private List<ClientCommand> commands = new ArrayList<ClientCommand>();

    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    public static ServerProxy instance = new ServerProxy();

    private ServerProxy() {
    }

    public static ServerProxy getInstance() {
        if (instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    @Override
    public CommandResult createGame(Game game) {
        ServerCommand command = serverCommandFactory.createCreateGameCommand(game);
        return (CommandResult) ClientCommunicator.getInstance().sendCommand(utils.CREATE_GAME, command);

    }

    @Override
    public CommandResult joinGame(Game game, User user, String userColor) {
        ServerCommand command = serverCommandFactory.createJoinGameCommand(game, user, userColor);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.JOIN_GAME, command);
        return commandResult;
    }

    @Override
    public CommandResult startGame(Game game) {
        ServerCommand command = serverCommandFactory.createStartGameCommand(game);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance().sendCommand(utils.START_GAME, command);
        executeCommands(commandResult.getClientCommands());
        return null;
    }

    @Override
    public LoginResult register(User user) {
        ServerCommand command = serverCommandFactory.createRegisterCommand(user);
        LoginResult registerResult = (LoginResult) ClientCommunicator.getInstance().sendCommand(utils.REGISTER, command);
        executeCommands(registerResult.getClientCommands());
        return registerResult;
    }

    @Override
    public LoginResult login(User user) {
        ServerCommand command = serverCommandFactory.createLoginCommand(user);
        LoginResult loginResult = (LoginResult) ClientCommunicator.getInstance().sendCommand(utils.LOGIN, command);
        executeCommands(loginResult.getClientCommands());
        return loginResult;
    }

    public CommandResult getCommands(User user) {
        ServerCommand command = serverCommandFactory.createGetCommands(user);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance().sendCommand(utils.GET_COMMANDS, command);
        executeCommands(commandResult.getClientCommands());
        return commandResult;
    }


    public void executeCommands(List<ClientCommand> commandList) {
        System.out.println(commandList);
        for (ClientCommand command : commandList) {
            command.execute();
        }
    }
}
