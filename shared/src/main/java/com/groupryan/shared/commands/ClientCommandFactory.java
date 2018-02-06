package com.groupryan.shared.commands;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ClientCommandFactory {

    public ClientCommandFactory(){

    }


    public ClientCommand createCreateGameCommand(String userId){
        return new ClientCommand("ui.ClientFacade", "executeCreateGameCommand",
                new Class<?>[] {},
                new Object[] {});
        /*
        new Class<?>[] {String.class},
        new Object[] {userId}
        */
    }

    public ClientCommand createJoinGameCommand(String gameId, String userId){
        return new ClientCommand("ui.ClientFacade", "executeJoinGameCommand",
                new Class<?>[] {},
                new Object[] {});
        /*
        new Class<?>[] {String.class, String.class},
        new Object[] {gameId, userId}
        */
    }

    public ClientCommand createStartGameCommand(String gameId){
        return new ClientCommand("ui.ClientFacade", "executeStartGameCommand",
                new Class<?>[] {},
                new Object[] {});
        /*
        new Class<?>[] {String.class},
        new Object[] {gameId}
        */
    }

    public ClientCommand createLoginCommand(String username, String password){
        return new ClientCommand("ui.ClientFacade", "executeLoginCommand",
                new Class<?>[] {},
                new Object[] {});
        /*
        new Class<?>[] {String.class, String.class},
        new Object[] {username, password}
        */
    }

    public ClientCommand createRegisterCommand(String username, String password){
        return new ClientCommand("ui.ClientFacade", "executeRegisterCommand",
                new Class<?>[] {},
                new Object[] {});
        /*
        new Class<?>[] {String.class, String.class},
        new Object[] {username, password}
        */
    }
}