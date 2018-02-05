package com.groupryan.shared.commands;

import com.groupryan.shared.models.Game;

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

    public ServerCommand createGetCommandsCommand() {
        return new ServerCommand();
    }

    public ServerCommand createCreateGameCommand(Game game) { // parameters may need to be changed
        return new ServerCommand();
    }

}
