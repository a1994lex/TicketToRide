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


        ServerCommandFactory scf=new ServerCommandFactory();

        ServerCommand sc;
        CommandResult cr;

        // Login and Register tests
sc=scf.createRegisterCommand(user);
cr=(CommandResult) cc.sendCommand("register", sc);


        sc =scf.createStartGameCommand("gameID2");
        cr= (CommandResult) cc.sendCommand(utils.START_GAME, sc);


        sc =scf.createDrawThreeCardsCommand("jimbob");

         cr = (CommandResult) ClientCommunicator.getInstance().sendCommand(utils.DRAW_THREE_CARDS, sc);
        ServerProxy.getInstance().executeCommands(cr.getClientCommands());
        int i=0;
    }


}