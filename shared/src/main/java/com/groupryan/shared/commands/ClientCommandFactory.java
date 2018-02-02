package com.groupryan.shared.commands;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ClientCommandFactory {

    public ClientCommandFactory(){

    }


    public ClientCommand createCreateGameCommand(String userId){
        return new ClientCommand();
    }

    public ClientCommand createJoinGameCommand(String gameId, String userId){
        return new ClientCommand();
    }

    public ClientCommand createStartGameCommand(String gameId){
        return new ClientCommand();
    }

    public ClientCommand createLoginCommand(String username, String password){
        return new ClientCommand();
    }

    public ClientCommand createRegisterCommand(String username, String password){
        return new ClientCommand();
    }
}
