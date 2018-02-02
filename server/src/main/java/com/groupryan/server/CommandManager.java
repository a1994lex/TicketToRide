package com.groupryan.server;

import com.groupryan.shared.commands.ClientCommand;

import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CommandManager {
    private static CommandManager instance;
    public static CommandManager getInstance(){
        if(instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public List<ClientCommand> getCommands(int index){
        return null;
    }
    public ClientCommand makeJoinGameCommand(){return null;}
    public ClientCommand makeCreateGameCommand(){return null;}
    public ClientCommand makeStartGameCommand(){return null;}
    public ClientCommand makeLoginCommand(){return null;}
    public ClientCommand makeRegisterCommand(){return null;}
}
