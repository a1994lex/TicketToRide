package com.groupryan.shared.commands;

import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Playa;
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

    public ClientCommand createJoinGameCommand(Game game, User user, String userColor) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "joinGame",
                new String[]{Game.class.getTypeName(), User.class.getTypeName(), String.class.getTypeName()},
                new Object[]{game, user, userColor});
    }

    public ClientCommand createStartGameCommand(Game game, Playa p) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "startGame",
                new String[]{Game.class.getTypeName(), Playa.class.getTypeName()},
                new Object[]{game, p});
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
    public ClientCommand createDiscardDestinationCardCommand(List<Integer> cardIDs, String username) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "discardDestCard",
                new String[]{List.class.getTypeName(), String.class.getTypeName()},
                new Object[]{cardIDs, username});
        //todo idk if this is correct syntax
    }

    public ClientCommand createDrawThreeCardsCommand(List<DestCard> cards){
        return new ClientCommand("com.groupryan.client.ClientFacade", "drawThreeCards",
                new String[]{List.class.getTypeName()},
                new Object[]{cards});
    }

}