package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.DatabaseHolder;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.IServer;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.TrainCardList;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */


public class MainFacade implements IServer {

    private ServerCommandFactory scf = new ServerCommandFactory();


    @Override
    public CommandResult createGame(Game game) {
        CreateGameFacade cgf = new CreateGameFacade();
        return cgf.createGame(game);

    }

    @Override
    public CommandResult joinGame(Game game, User user, String userColor) {
        ServerCommand serverCommand = scf.createJoinGameCommand(game, user, userColor);
        DatabaseHolder.getInstance().addGameToUser(game.getGameId(), user);
        DatabaseHolder.getInstance().addCommand(game.getGameId(), serverCommand);
        JoinGameFacade jgf = new JoinGameFacade();
        return jgf.joinGame(game, user, userColor);
    }

    @Override
    public CommandResult startGame(String gameId) {
        ServerCommand serverCommand = scf.createStartGameCommand(gameId);
        DatabaseHolder.getInstance().addCommand(gameId, serverCommand);

        StartGameFacade sgf = new StartGameFacade();
        return sgf.start(gameId);
    }

    @Override
    public LoginResult register(User user) {
        RegisterFacade rf = new RegisterFacade();
        return rf.register(user);
    }

    @Override
    public LoginResult login(User user) {
        LoginFacade lf = new LoginFacade();
        return lf.login(user);
    }

    @Override
    public CommandResult discardDestinationCard(DestCardList destCardList, String username) {
        ServerCommand serverCommand = scf.createDiscardDestinationCardCommand(destCardList, username);
        ServerGame serverGame = RootServerModel.getInstance().getServerGame(username);
        DatabaseHolder.getInstance().addCommand(serverGame.getServerGameID(), serverCommand);

        DestinationCardFacade dcf = new DestinationCardFacade();
        List<Integer> cardIDs = destCardList.getList();
        CommandResult cr=  dcf.discard(cardIDs, username);

        if(serverGame.updateReady()){
            for (ClientCommand cc: endTurn(username).getClientCommands()){
                cr.addClientCommand(cc);
            }
        }
        return cr;
    }

    public void changeTurn(ServerGame game){
        CommandManager.getInstance().addNextTurnCommand(game.getServerGameID(), game.getNextTurn());
    }

    @Override
    public CommandResult endTurn(String username){
        ServerGame serverGame = RootServerModel.getInstance().getServerGame(username);

        ServerCommand serverCommand = scf.createEndTurnCommand(username);
        DatabaseHolder.getInstance().addCommand(serverGame.getServerGameID(), serverCommand);

        Player player = serverGame.getPlayer(username);
        if(player.getEndGame()){
            EndGameFacade endGameFacade = new EndGameFacade();
            endGameFacade.end(serverGame.getServerGameID());
        }
        else if(player.getTrainPieces() < 3){
            player.setEndGame(true);
        }
        changeTurn(serverGame);
        CommandResult cm = new CommandResult();
        cm.setResultType(utils.VALID);
        cm.setClientCommands(CommandManager.getInstance().getGameCommands(serverGame.getServerGameID(), username));
        return cm;
    }

    @Override
    public CommandResult drawDestinationCards(String username) {
        ServerGame serverGame = RootServerModel.getInstance().getServerGame(username);
        ServerCommand serverCommand = scf.createDrawThreeCardsCommand(username);
        DatabaseHolder.getInstance().addCommand(serverGame.getServerGameID(), serverCommand);
        DestinationCardFacade dcf = new DestinationCardFacade();
        return dcf.drawDestinationCards(username);
    }

    @Override
    public CommandResult sendChat(String gameId, String msg, String username) {
        CommandManager.getInstance().addChatCommand(msg, gameId, username);
        CommandResult cm = new CommandResult();
        cm.setClientCommands(CommandManager.getInstance().getGameCommands(gameId, username));
        cm.setResultType(utils.VALID);
        return cm;
    }

    @Override
    public CommandResult drawColorCard(Integer position, String username) {
        ServerGame sg = RootServerModel.getInstance().getServerGame(username);
        ServerCommand serverCommand = scf.createDrawColorCardCommand(position, username);
        DatabaseHolder.getInstance().addCommand(sg.getServerGameID(), serverCommand);


        ColorCardFacade ccf = new ColorCardFacade();
        return ccf.drawCard(position, username);
    }

    @Override
    public CommandResult updateFaceUp(String gameId) {
        ColorCardFacade ccf = new ColorCardFacade();
        return ccf.updateFaceUp();

    }

    @Override
    public CommandResult getCommands(User user) {
        GetCommandsFacade gcf = new GetCommandsFacade();
        return gcf.getCommandList(user);
    }

    @Override
    public CommandResult claimRoute(String username, Integer routeId, TrainCardList trainCardIDs) {

        ServerGame sg = RootServerModel.getInstance().getServerGame(username);
        ServerCommand serverCommand = scf.createClaimRouteCommand(username, routeId, trainCardIDs);
        DatabaseHolder.getInstance().addCommand(sg.getServerGameID(), serverCommand);

        int id = (int) routeId;
        ClaimRouteFacade crf = new ClaimRouteFacade();
        CommandResult cr = crf.claimRoute(username, id, trainCardIDs);
        for (ClientCommand cc: endTurn(username).getClientCommands()){
            cr.addClientCommand(cc);
        }
        return cr;
    }

    @Override
    public CommandResult getGameCommands(String gameId, String playerId) {
        GetCommandsFacade gcf = new GetCommandsFacade();
        return gcf.getGameCommands(gameId, playerId);
    }
}
