package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.shared.IServer;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */


public class MainFacade implements IServer {

    public CommandResult delegate(/*whatever we pass in.. probs the request*/) {
        return null;
    }

    @Override
    public CommandResult createGame(Game game) {
        CreateGameFacade cgf = new CreateGameFacade();
        return cgf.createGame(game);
    }

    @Override
    public CommandResult joinGame(Game game, User user, String userColor) {
        JoinGameFacade jgf = new JoinGameFacade();
        return jgf.joinGame(game, user, userColor);
    }

    @Override
    public CommandResult startGame(String gameId) {
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
    public CommandResult discardDestinationCard(List<Integer> cardIDs, String username) {
        DestinationCardFacade dcf= new DestinationCardFacade();
        return dcf.discard(cardIDs, username);
    }

    @Override
    public CommandResult drawDestinationCards(String username){
        DestinationCardFacade dcf=new DestinationCardFacade();
        return dcf.drawDestinationCards(username);
    }

    @Override
    public CommandResult sendChat(String gameId, String msg, String username){
        CommandManager.getInstance().addChatCommand(msg,gameId,username);
        CommandResult cm = new CommandResult();
        cm.setClientCommands(CommandManager.getInstance().getGameCommands(gameId, username));
        cm.setResultType(utils.VALID);
        return cm;
    }

    @Override
    public CommandResult drawColorCard(String gameId){
        ColorCardFacade ccf= new ColorCardFacade();
        return ccf.drawCard();
    }

    @Override
    public CommandResult updateFaceUp(String gameId){
        ColorCardFacade ccf=new ColorCardFacade();
        return ccf.updateFaceUp();

    }

    @Override
    public CommandResult getCommands(User user) {
        GetCommandsFacade gcf = new GetCommandsFacade();
        return gcf.getCommandList(user);
    }

    @Override
    public CommandResult getGameCommands(String gameId, String playerId) {
        GetCommandsFacade gcf = new GetCommandsFacade();
        return gcf.getGameCommands(gameId, playerId);
    }
}
