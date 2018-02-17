package com.groupryan.server.facades;

import com.groupryan.shared.IServer;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

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
    public CommandResult startGame(Game game) {
        StartGameFacade sgf = new StartGameFacade();
        return sgf.start(game);
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
    public CommandResult discardDestinationCard(List<String> cardID, String username) {
        DestinationCardFacade dcf= new DestinationCardFacade();
        return dcf.discard();
    }

    public CommandResult updateChat(){
        ChatFacade cf= new ChatFacade();
        return cf.updateChat();

    }


    public CommandResult drawColorCard(){
        ColorCardFacade ccf= new ColorCardFacade();
        return ccf.drawCard();
    }
    public CommandResult updateFaceUp(){
        ColorCardFacade ccf=new ColorCardFacade();
        return ccf.updateFaceUp();

    }

    public CommandResult getCommands(User user) {
        GetCommandsFacade gcf = new GetCommandsFacade();
        return gcf.getCommandList(user);
    }
}
