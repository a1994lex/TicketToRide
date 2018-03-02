package com.example.clientapp;

import com.groupryan.client.ServerProxy;
import com.groupryan.shared.models.Game;

import junit.framework.TestCase;

/**
 * Created by Daniel on 2/12/2018.
 */

public class RegisterAsyncTaskTest extends TestCase {

    public void test() throws Exception {
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
    }

    public void testRegister() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
    }

    public void testLogin() throws Exception {
        ServerProxy sp= ServerProxy.getInstance();
    }

}
