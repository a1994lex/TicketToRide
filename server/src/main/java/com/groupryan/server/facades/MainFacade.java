package com.groupryan.server.facades;

import com.groupryan.shared.IServer;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

/**
 * Created by bengu3 on 1/31/18.
 */


public class MainFacade implements IServer {

    @Override
    public CommandResult createGame(Game game) {
        CreateGameFacade cgf=new CreateGameFacade();
        return null;
    }

    @Override
    public CommandResult joinGame(String gameId, String userId) {
        JoinGameFacade jgf=new JoinGameFacade();
        return null;
    }

    @Override
    public CommandResult startGame(String gameId) {
        StartGameFacade sgf=new StartGameFacade();
        return null;
    }

    @Override
    public CommandResult register(User user) {
        RegisterFacade rf=new RegisterFacade();
        return null;
    }

    @Override
    public CommandResult login(User user) {
        LoginFacade lf= new LoginFacade();
        return null;
    }

    @Override
    public CommandResult getCommands() {
        GetCommandsFacade gdf= new GetCommandsFacade();
        return null;
    }
}
