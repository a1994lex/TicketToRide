package com.groupryan.client;

import com.groupryan.shared.models.Game;

import junit.framework.TestCase;

/**
 * Created by arctu on 2/12/2018.
 */
public class ServerProxyTest extends TestCase {

    public void testCreateGame() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
        Game g1=new Game("YOLO", "123", 2);
        sp.createGame(g1);
        int j=0;
    }

    public void testJoinGame() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
    }

    public void testStartGame() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
        sp.startGame("gameID2");
    }

    public void testRegister() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
    }

    public void testLogin() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
    }

}