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
        /*er u1=new User("rya", "1");
        User u2=new User("ry", "2");
        User u3=new User("r", "3");
        User u4=new User("ra", "4");
        User u5=new User("ya", "5");*/

        ServerCommandFactory scf=new ServerCommandFactory();

        ServerCommand sc;
        CommandResult cr;

        // Login and Register tests
        sc =scf.createRegisterCommand(user);
        cr= (CommandResult) cc.sendCommand(utils.REGISTER, sc);
        int i=0;
       /* sc=scf.createRegisterCommand(u1);
        cr=(CommandResult) cc.sendCommand("Register", sc);

        sc =scf.createLoginCommand(user);
        cr= (CommandResult) cc.sendCommand("Login", sc);
        sc=scf.createLoginCommand(u1);
         cr=(CommandResult) cc.sendCommand("Login", sc);


    sc=scf.createGetCommands();
    cr= (CommandResult) cc.sendCommand("getCommands", sc);

        Game g1=new Game("YOLO", "123", 2);
        Game g2=new Game("YOL", "12", 3);
        Game g3=new Game("YOO", "13", 4);
        Game g4=new Game("YLO", "23", 5);


        sc=scf.createCreateCommand(g1);
        cr=(CommandResult) cc.sendCommand("createGame", sc);
        sc=scf.createCreateCommand(g2);
        cr=(CommandResult) cc.sendCommand("createGame", sc);
        sc=scf.createCreateCommand(g2);
        cr=(CommandResult) cc.sendCommand("createGame", sc);
        sc=scf.createCreateCommand(g3);
        cr=(CommandResult) cc.sendCommand("createGame", sc);
        sc=scf.createCreateCommand(g4);
        cr=(CommandResult) cc.sendCommand("createGame", sc);
/////games created
        sc=scf.createJoinGameCommand(g1,user, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);
/*
        sc=scf.createJoinGameCommand(g2,user, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);
        sc=scf.createJoinGameCommand(g2,u4, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);





        sc=scf.createJoinGameCommand(g3,user, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g4,user, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);
///////first user joins all games

        sc=scf.createJoinGameCommand(g1,u1, Color.GREEN);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g2,u1, Color.GREEN);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g3,u1, Color.GREEN);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);
///2nd user joins games
        sc=scf.createJoinGameCommand(g1,u2, Color.RED);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g2,u2, Color.RED);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);

        sc=scf.createJoinGameCommand(g3,u3, Color.BLACK);
        cr=(CommandResult)cc.sendCommand("joinGame", sc);*

        sc=scf.createStartGameCommand(g1);
        cr=(CommandResult)cc.sendCommand("startGame", sc);

        sc =scf.createRegisterCommand(u5);
        cr= (CommandResult) cc.sendCommand("Register", sc);


        sc=scf.createGetCommands();
        cr= (CommandResult) cc.sendCommand("getCommands", sc);*/

    int j=0;
    }


}