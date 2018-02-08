package com.groupryan.client;

import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.User;

import junit.framework.TestCase;

/**
 * Created by alex on 2/6/18.
 */
public class ClientSocketCommunicatorTest extends TestCase {
    ServerCommand command;
    @Override
    public void setUp() throws Exception {
        User user = new User("testuname", "test");
        ServerCommandFactory fac = new ServerCommandFactory();
        command = fac.createLoginCommand(user);
        System.out.print(command);
    }

    public void testWrite() throws Exception {
        ClientSocketCommunicator.getInstance().write(command);
        boolean completed = true;
        assert(completed);
    }


}