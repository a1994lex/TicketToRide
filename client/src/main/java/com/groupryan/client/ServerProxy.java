package com.groupryan.client;

import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

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


    /* I realize that the server facades are going to need to return command results, but the server
        doesn't really need to return that to the UI facade, so I set the return value to null.
        What do you guys think? Because the server proxy is just going to call the client facade
        and execute the commands. It doesn't need to return anything to the UI facade, right?
     */

    @Override
    public CommandResult createGame(Game game) {
        ServerCommand command = serverCommandFactory.createCreateGameCommand(game);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance().sendCommand(CREATE_GAME, command);
        //ClientFacade clientFacade = new ClientFacade().executeCreateGameCommand(commandResult);
        return null;
    }

    @Override
    public CommandResult joinGame(Game game, User user, Color userColor) {
        ServerCommand command = serverCommandFactory.createJoinGameCommand(game, user, userColor);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(JOIN_GAME, command);
        // ClientFacade clientFacade = new ClientFacade().executeJoinGameCommand(commandResult);
        return null;
    }

    @Override
    public CommandResult startGame(Game game) {
        ServerCommand command = serverCommandFactory.createStartGameCommand(game);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance().sendCommand(START_GAME, command);
        // ClientFacade clientFacade = new ClientFacade().executeStartGameCommand(commandResult);
        return null;
    }

    @Override
    public LoginResult register(User user) {
        ServerCommand command = serverCommandFactory.createRegisterCommand(user);
        LoginResult registerResult = (LoginResult) ClientCommunicator.getInstance().sendCommand(REGISTER, command);
        if (true) {  // if register succeeds
            Poller poller = new Poller();
            poller.poll();
        }
        //  ClientFacade clientFacade = new ClientFacade().executeRegisterCommand(registerResult);
        return registerResult;
    }

    @Override
    public LoginResult login(User user) {
        ServerCommand command = serverCommandFactory.createLoginCommand(user);
        LoginResult loginResult = (LoginResult) ClientCommunicator.getInstance().sendCommand(LOGIN, command);
        //   ClientFacade clientFacade = new ClientFacade().executeLoginCommand(loginResult);
        if (true) {  // if login succeeds
            Poller poller = new Poller();
            poller.poll();
        }
        return loginResult;
    }

    public CommandResult getCommands() {
        ServerCommand command = serverCommandFactory.createGetCommands();
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance().sendCommand(GET_COMMANDS, command);
        return commandResult;
    }


    private static final String CREATE_GAME = "createGame";
    private static final String JOIN_GAME = "joinGame";
    private static final String START_GAME = "startGame";
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    private static final String GET_COMMANDS = "getCommands";
    private static final String GET_GAME_LIST = "getGameList";
}
