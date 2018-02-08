package com.groupryan.client;

import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.IServerCommand;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

import java.util.ArrayList;
import java.util.List;

public class ServerProxy implements IServer {

    private List<ClientCommand> commands = new ArrayList<ClientCommand>();

    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    public static ServerProxy instance = new ServerProxy();

    private ServerProxy() {}

    public static ServerProxy getInstance() {
        if (instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }


    @Override
    public CommandResult createGame(Game game) {
        ServerCommand command = serverCommandFactory.createCreateCommand(game);
        return (CommandResult) ClientCommunicator.getInstance().sendCommand(CREATE_GAME, command);
    }

    @Override
    public CommandResult joinGame(String gameId, String userId) {
        ServerCommand command = serverCommandFactory.createJoinGameCommand(gameId, userId);
        return (CommandResult) ClientCommunicator.getInstance().sendCommand(JOIN_GAME, command);
    }

    @Override
    public CommandResult startGame(String gameId) {
        ServerCommand command = serverCommandFactory.createStartGameCommand(gameId);
        return (CommandResult) ClientCommunicator.getInstance().sendCommand(START_GAME, command);
    }

    @Override
    public LoginResult register(User user) {
        ServerCommand command= serverCommandFactory.createRegisterCommand(user);
        return (LoginResult) ClientCommunicator.getInstance().sendCommand(REGISTER, command);
    }

    @Override
    public LoginResult login(User user) {
        ServerCommand command = serverCommandFactory.createLoginCommand(user);

        return (LoginResult) ClientCommunicator.getInstance().sendCommand(LOGIN, command);
    }


    public CommandResult getCommands() {
        ServerCommand command = serverCommandFactory.createGetCommands();
        return ClientCommunicator.getInstance().sendGetCommands(GET_COMMANDS, command);
    }

    public void executeCommand(){

    }

    private static final String CREATE_GAME = "Create Game";
    private static final String JOIN_GAME = "Join Game";
    private static final String START_GAME = "Start Game";
    private static final String REGISTER = "Register";
    private static final String LOGIN = "Login";
    private static final String GET_COMMANDS = "Get Commands";
    private static final String GET_GAME_LIST = "Get Game List";
}
