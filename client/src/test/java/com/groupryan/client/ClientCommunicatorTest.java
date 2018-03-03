package com.groupryan.client;

import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import junit.framework.TestCase;

/**
 * Created by arctu on 2/8/2018.
 */
public class ClientCommunicatorTest extends TestCase {
    public void testSendCommand() throws Exception {
        ClientCommunicator cc= ClientCommunicator.getInstance();
        User user=new User("ryan", "apple");
        User u =new User("a","a");

        ServerCommandFactory scf=new ServerCommandFactory();

        ServerCommand sc;
        CommandResult cr;

        // Login and Register tests
sc=scf.createRegisterCommand(user);
cr=(CommandResult) cc.sendCommand("register", sc);
        sc=scf.createRegisterCommand(u);
        cr=(CommandResult) cc.sendCommand("register", sc);
    Game g=new Game("q","q",2);
        sc=scf.createCreateGameCommand(g);
        cr=(CommandResult) cc.sendCommand("createGame", sc);

sc=scf.createJoinGameCommand(g,user,utils.BLACK);
cr=(CommandResult) cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g,u,utils.RED);
        cr=(CommandResult) cc.sendCommand("joinGame", sc);


        sc =scf.createStartGameCommand("q");
        cr= (CommandResult) cc.sendCommand(utils.START_GAME, sc);


        int i=0;
    }


}