package com.groupryan.shared.commands;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommandFactory {

    public ServerCommandFactory(){

    }

    public ServerServerCommand createJoinGameCommand(String gameId, String userId){
        return new ServerServerCommand();
    }

    public ServerServerCommand createStartGameCommand(String gameId){
        return new ServerServerCommand();
    }

    public ServerServerCommand createLoginCommand(String username, String password){
        return new ServerServerCommand();
    }

    public ServerServerCommand createRegisterCommand(String username, String password){
        return new ServerServerCommand();
    }

}
