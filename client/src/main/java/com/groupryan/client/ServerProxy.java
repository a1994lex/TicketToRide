package com.groupryan.client;

import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ICommand;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

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
        ServerCommand command =
        return ClientCommunicator.getInstance().sendCommand(CREATE_GAME, command);
    }

    @Override
    public CommandResult joinGame(String gameId, String userId) {
        ServerCommand command =
        return ClientCommunicator.getInstance().sendCommand(JOIN_GAME, command);
    }

    @Override
    public CommandResult startGame(String gameId) {
        ServerCommand command =
        return ClientCommunicator.getInstance().sendCommand(START_GAME, command);
    }

    @Override
    public CommandResult register(User user) {
        ServerCommand command =
        return ClientCommunicator.getInstance().sendCommand(REGISTER, command);
    }

    @Override
    public CommandResult login(User user) {
        ServerCommand command =
        return ClientCommunicator.getInstance().sendCommand(LOGIN, command);
    }

    @Override
    public List<ICommand> getCommands() {
        return null;
    }

    private static final String CREATE_GAME = "CreateGame";
    private static final String JOIN_GAME = "JoinGame";
    private static final String START_GAME = "StartGame";
    private static final String REGISTER = "Register";
    private static final String LOGIN = "Login";
    private static final String LOGIN_REGISTER_RESULT = "LoginRegisterResult";
    private static final String GAME_LIST_RESULT = "GameListResult";
}
