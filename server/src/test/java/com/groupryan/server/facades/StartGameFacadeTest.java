package com.groupryan.server.facades;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arctu on 2/24/2018.
 */
public class StartGameFacadeTest {
    @Test
    public void start() throws Exception {
        StartGameFacade sgf=new StartGameFacade();
        sgf.start("gameID2");
        int j=0;
    }

}