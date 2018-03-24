package com.groupryan.shared.commands;

import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.DestCardReturnObject;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.EndGameStatReturnObject;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
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

    public ClientCommand createStartGameCommand(Game game, Player p) {
        return new ClientCommand("com.groupryan.client.ClientFacade", "startGame",
                new String[]{Game.class.getTypeName(), Player.class.getTypeName()},
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
    public ClientCommand createDiscardDestinationCardCommand(DestCardList destCardList, String username) {
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "discardDestCard",
                new String[]{DestCardList.class.getTypeName(), String.class.getTypeName()},
                new Object[]{destCardList, username});
    }

    public ClientCommand createDrawThreeCardsCommand(List<DestCard> cards){
        DestCardReturnObject cardss= new DestCardReturnObject(cards);
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "drawThreeCards",
                new String[]{DestCardReturnObject.class.getTypeName()},
                new Object[]{cardss});
    }

    public ClientCommand createDrawColorCardCommand(TrainCard tc){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "drawColorCard",
                new String[]{TrainCard.class.getTypeName()},
                new Object[]{tc});
    }

    public ClientCommand createChatCommand(String msg, String username){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "updateChat",
                new String[]{String.class.getTypeName(), String.class.getTypeName()},
                new Object[]{msg, username});
    }

    public ClientCommand createHistoryCommand(String msg){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "updateHistory",
                new String[]{String.class.getTypeName()},
                new Object[]{msg});
    }

    public ClientCommand createStatCommand(Stat stat){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "updateStat",
                new String[]{Stat.class.getTypeName()},
                new Object[]{stat});
    }

    public ClientCommand createBankCommand(Bank bank){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "setBank",
                new String[]{Bank.class.getTypeName()},
                new Object[]{bank});
    }

    public ClientCommand createDDeckCommand(Integer size){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "setDDeckSize",
                new String[]{Integer.class.getTypeName()},
                new Object[]{size});
    }

    public ClientCommand createTDeckCommand(Integer size){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "setTDeckSize",
                new String[]{Integer.class.getTypeName()},
                new Object[]{size});
    }

    public ClientCommand createGameOverCommand(String winner, List<EndGameStat> endGameStats) {
        EndGameStatReturnObject endStats = new EndGameStatReturnObject(endGameStats);
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "gameOver",
                new String[]{String.class.getTypeName(), EndGameStatReturnObject.class.getTypeName()},
                new Object[]{winner, endStats});
    }

    public ClientCommand createNextTurnCommand(int turn){
        return new ClientCommand("com.groupryan.client.ClientGameFacade", "changeTurn",
                new String[]{Integer.class.getTypeName()},
                new Object[]{turn});
    }
}