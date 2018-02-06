package com.groupryan.server;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.User;

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
    public ClientCommand makeJoinGameCommand(String gameId, String userId){return null;}
    public ClientCommand makeCreateGameCommand(String gameId){return null;}
    public ClientCommand makeStartGameCommand(String gameId){return null;}
    public ClientCommand makeLoginCommand(String result){
        //there are only 3 options for result
        //"valid";
        //"invalid password";
        //or "invalid username";
        return null;}
    public ClientCommand makeRegisterCommand(String result, User user){return null;}//adds user to client model
}
