package com.groupryan.shared.commands;

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

    public ServerCommand createLoginCommand(String username, String password){
        return new ServerCommand("facades.MainFacade", "login",
                new Class<?>[] {String.class, String.class},
                new Object[] {username, password});
    }

    public ServerCommand createRegisterCommand(String username, String password){
        return new ServerCommand("facades.MainFacade", "register",
                new Class<?>[]{String.class, String.class},
                new Object[] {username, password});
    }

}
