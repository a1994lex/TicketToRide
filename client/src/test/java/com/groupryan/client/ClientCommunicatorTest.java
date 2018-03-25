package com.groupryan.client;

import com.groupryan.shared.commands.*;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by arctu on 2/8/2018.
 */
public class ClientCommunicatorTest extends TestCase {
    public void testSendCommand() throws Exception {
        ClientCommunicator cc= ClientCommunicator.getInstance();
        User user=new User("q", "q");

        ServerCommandFactory scf=new ServerCommandFactory();

        ServerCommand sc;
        CommandResult cr;

        // Login and Register tests
    sc=scf.createRegisterCommand(user);
    cr=(CommandResult) cc.sendCommand("register", sc);
       // sc=scf.createRegisterCommand(u);
       // cr=(CommandResult) cc.sendCommand("register", sc);
    Game g=new Game("gameID2","gameID2",3);

    sc=scf.createJoinGameCommand(g,user,utils.BLACK);
    cr=(CommandResult) cc.sendCommand("joinGame", sc);


        sc =scf.createStartGameCommand("gameID2");
        cr= (CommandResult) cc.sendCommand(utils.START_GAME, sc);


        int i=0;
    }


}