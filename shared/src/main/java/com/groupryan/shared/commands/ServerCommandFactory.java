package com.groupryan.shared.commands;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommandFactory {

    public ServerCommandFactory(){

    }

    /*public ServerCommand(String className, String methodName,
                         Class<?>[] paramTypes, Object[] paramValues)*/

    public ServerCommand createJoinGameCommand(String gameId, String userId){

        return new ServerCommand("facades.MainFacade", "joinGame",
            new Class<?>[] {String.class, String.class},
            new Object[] {gameId, userId} );
    }

    public ServerCommand createStartGameCommand(String gameId){

        return new ServerCommand("facades.MainFacade", "startGame",
                new Class<?>[] {String.class},
                new Object[] {gameId});
    }

    public ServerCommand createLoginCommand(User user){
        return new ServerCommand("facades.MainFacade", "login",
                new Class<?>[] {User.class},
                new Object[] {user});
    }

    public ServerCommand createRegisterCommand(User user){
        return new ServerCommand("facades.MainFacade", "register",
                new Class<?>[]{User.class},
                new Object[] {user});
    }
    public ServerCommand createCreateCommand(Game game){
        return new ServerCommand("facades.MainFacade", "createGame",
                new Class<?>[]{Game.class},
                new Object[] {game});
    }
    public ServerCommand createGetCommands(){

        return new ServerCommand("facades.MainFacade", "getCommands",
                new Class<?>[]{},
                new Object[] {});
    }

}
