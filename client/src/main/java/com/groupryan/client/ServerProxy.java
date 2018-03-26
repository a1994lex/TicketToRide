package com.groupryan.client;

import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.TrainCardList;
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
    public CommandResult startGame(String gameId) {
        ServerCommand command = serverCommandFactory.createStartGameCommand(gameId);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.START_GAME, command);
        executeCommands(commandResult.getClientCommands());
        return null;
    }

    @Override
    public LoginResult register(User user) {
        ServerCommand command = serverCommandFactory.createRegisterCommand(user);
        LoginResult registerResult = (LoginResult) ClientCommunicator.getInstance()
                .sendCommand(utils.REGISTER, command);
        executeCommands(registerResult.getClientCommands());
        return registerResult;
    }

    @Override
    public LoginResult login(User user) {
        ServerCommand command = serverCommandFactory.createLoginCommand(user);
        LoginResult loginResult = (LoginResult) ClientCommunicator.getInstance()
                .sendCommand(utils.LOGIN, command);
        executeCommands(loginResult.getClientCommands());
        return loginResult;
    }

    @Override
    public CommandResult discardDestinationCard(DestCardList destCardList, String username) {
        ServerCommand command = serverCommandFactory.createDiscardDestinationCardCommand(destCardList,
                                                                                        username);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.DISCARD_DESTCARD, command);
        return commandResult;
    }

    @Override
    public CommandResult sendChat(String gameId, String msg, String username) {
        ServerCommand command = serverCommandFactory.createSendChat(gameId, msg, username);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.GET_COMMANDS, command);
//        executeCommands(commandResult.getClientCommands());
        return commandResult;
    }

    @Override
    public CommandResult drawColorCard(Integer i, String username) {
        ServerCommand command = serverCommandFactory.createDrawColorCardCommand(i, username);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.DRAW_COLOR_CARD, command);
        return commandResult;
    }

    @Override
    public CommandResult updateFaceUp(String gameId) {
        return null;
    }

    @Override
    public CommandResult drawDestinationCards(String username) {
        ServerCommand command = serverCommandFactory.createDrawThreeCardsCommand(username);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.DRAW_THREE_CARDS, command);
        //executeCommands(commandResult.getClientCommands());
        return commandResult;
    }
    @Override
    public CommandResult endTurn(String username) {
        ServerCommand command = serverCommandFactory.createEndTurnCommand(username);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.NEW_TURN, command);
        return commandResult;
    }

    @Override
    public CommandResult getCommands(User user) {
        ServerCommand command = serverCommandFactory.createGetCommands(user);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                .sendCommand(utils.GET_COMMANDS, command);
        //executeCommands(commandResult.getClientCommands());
        return commandResult;
    }

    @Override
    public CommandResult claimRoute(String username, int routeId, TrainCardList trainCardIDs) {
        ServerCommand command = serverCommandFactory.createClaimRouteCommand(username, routeId,
                                                                            trainCardIDs);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                                        .sendCommand(utils.CLAIM_ROUTE, command);
        return commandResult;
    }

    @Override
    public CommandResult getGameCommands(String gameId, String playerId) {
        ServerCommand command = serverCommandFactory.createGetGameCommands(gameId, playerId);
        CommandResult commandResult = (CommandResult) ClientCommunicator.getInstance()
                                        .sendCommand(utils.GET_COMMANDS, command);
        System.out.print(commandResult.getClientCommands().size());
        return commandResult;
    }

    public void executeCommands(List<ClientCommand> commandList) {
        System.out.println(commandList);
        for (ClientCommand command : commandList) {
            command.execute();
        }
    }
}
