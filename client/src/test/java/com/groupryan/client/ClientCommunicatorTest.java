package com.groupryan.client;

import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

import junit.framework.TestCase;

/**
 * Created by arctu on 2/8/2018.
 */
public class ClientCommunicatorTest extends TestCase {
    public void testSendCommand() throws Exception {
        ClientCommunicator cc= ClientCommunicator.getInstance();
        User user=new User("ryan", "apple");
        ServerCommandFactory scf=new ServerCommandFactory();

        ServerCommand sc=scf.createRegisterCommand(user);
        CommandResult cr=(CommandResult) cc.sendCommand("Register", sc);
        int i=0;

        sc =scf.createLoginCommand(user);
         cr= (CommandResult) cc.sendCommand("Login", sc);

        sc =scf.createLoginCommand(user);
        cr= (CommandResult) cc.sendCommand("Login", sc);
    int j=0;
    }

}