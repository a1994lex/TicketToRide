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
        return new ServerCommand("facades.MainFacade", "createGame",
                new String[]{Game.class.getTypeName()},
                new Object[]{game});
    }

    public ServerCommand createJoinGameCommand(Game game, User user, Color userColor) {
        return new ServerCommand("facades.MainFacade", "joinGame",
                new String[]{Game.class.getTypeName(), User.class.getTypeName(), Color.class.getTypeName()},
                new Object[]{game, user, userColor});
    }


    public ServerCommand createStartGameCommand(Game game) {
        return new ServerCommand("facades.MainFacade", "startGame",
                new String[]{Game.class.getTypeName()},
                new Object[]{game});

    }

    public ServerCommand createLoginCommand(User user) {
        return new ServerCommand("facades.MainFacade", "login",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }

    public ServerCommand createRegisterCommand(User user){
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "register",
                new String[]{User.class.getTypeName()},
                new Object[] {user});
    }
    public ServerCommand createCreateCommand(Game game){
        return new ServerCommand("com.groupryan.server.facades.MainFacade", "createGame",
                new String[]{Game.class.getTypeName()},
                new Object[] {game});

    }

        return new ServerCommand("com.groupryan.server.facades.MainFacade", "getCommands",

                new String[]{},
                new Object[]{});
    }

}
