package com.groupryan.shared.commands;

import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ClientCommandFactory {

    public ClientCommandFactory() {

    }


    public ClientCommand createCreateGameCommand(Game game) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "createGame",
                new String[]{Game.class.getTypeName()},
                new Object[]{game});
    }

    public ClientCommand createJoinGameCommand(Game game, User user, Color userColor) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "joinGame",
                new String[]{Game.class.getTypeName(), User.class.getTypeName(), Color.class.getTypeName()},
                new Object[]{game, user, userColor});
    }

    public ClientCommand createStartGameCommand(Game game) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "startGame",
                new String[]{Game.class.getTypeName()},
                new Object[]{game});
    }

    public ClientCommand createLoginCommand(User user) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "login",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }

    public ClientCommand createRegisterCommand(User user) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "register",
                new String[]{User.class.getTypeName()},
                new Object[]{user});
    }
}