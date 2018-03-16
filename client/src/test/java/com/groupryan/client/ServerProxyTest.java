package com.groupryan.client;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;

import junit.framework.TestCase;

/**
 * Created by arctu on 2/12/2018.
 */
public class ServerProxyTest extends TestCase {

    public void testCreateGame() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
        CommandResult cr= sp.drawColorCard(-1, "q");
        int i=0;
    }


}