package com.groupryan.server.facades;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by arctu on 2/24/2018.
 */
public class DestinationCardFacadeTest {

    @Test
    public void discard() throws Exception {
        StartGameFacade sgf=new StartGameFacade();
        sgf.start("gameID2");
        DestinationCardFacade dcf=new DestinationCardFacade();
        List<Integer> list =new ArrayList<>();
        list.add(27);
        list.add(23);
        dcf.discard(list,"jimbob");
    }



}