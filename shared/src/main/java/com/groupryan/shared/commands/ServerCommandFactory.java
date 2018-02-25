package com.groupryan.shared.commands;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;


/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommandFactory {

    public ServerCommandFactory() {

    }

    /*public ServerCommand(String className, String methodName,
                         Class<?>[] paramTypes, Object[] paramValues)*/
    public ServerCommand createCreateGameCommand(Game game) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "createGame",
                new String[]{Game.class.getTypeName()},
                new Object[]{game});
    }

    public ServerCommand createJoinGameCommand(Game game, User user, String userColor) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "joinGame",
                new String[]{Game.class.getTypeName(), User.class.getTypeName(), String.class.getTypeName()},
                new Object[]{game, user, userColor});
    }


    public ServerCommand createStartGameCommand(String gameId) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "startGame",
                new String[]{String.class.getTypeName()},
                new Object[]{gameId});

    }

    public ServerCommand createLoginCommand(User user) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "login",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }

    public ServerCommand createRegisterCommand(User user) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "register",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }

    public ServerCommand createGetCommands(User user) {
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "getCommands",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }
    public ServerCommand createDrawThreeCardsCommand(String username){
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "drawDestinationCards",
                new String[]{String.class.getTypeName()},
                new Object[]{username});
    }

}
