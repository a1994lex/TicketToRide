package com.groupryan.shared.commands;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommandFactory {

    public ServerCommandFactory(){

    }

    public ServerCommand createJoinGameCommand(String gameId, String userId){
        return new ServerCommand();
    }

    public ServerCommand createStartGameCommand(String gameId){
        return new ServerCommand();
    }

    public ServerCommand createLoginCommand(String username, String password){
        return new ServerCommand();
    }

    public ServerCommand createRegisterCommand(String username, String password){
        return new ServerCommand();
    }

}
