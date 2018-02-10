package com.groupryan.server.facades;

import com.groupryan.shared.IServer;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;

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
    public CommandResult joinGame(Game game, User user, Color userColor) {
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

    public CommandResult getCommands() {
        GetCommandsFacade gcf = new GetCommandsFacade();
        gcf.getCommandList();
        //takes the above and then returns it
        return null;
    }
}
