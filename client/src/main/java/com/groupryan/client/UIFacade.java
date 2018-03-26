package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.DestCardList;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIFacade {

    private static ServerCommandFactory serverCommandFactory = new ServerCommandFactory();

    private static UIFacade instance;

    public static UIFacade getInstance() {
        if (instance == null) {
            instance = new UIFacade();
        }
        return instance;
    }

    private UIFacade() {
    }

    public CommandResult createGame(String userColor, String gameName, int numberOfPlayers) {
        Game game = new Game(gameName, null, numberOfPlayers);
        User user = RootClientModel.getUser();
        Map<String, String> users = new HashMap<String, String>();
        users.put(user.getUsername(), userColor);
        game.setUsers(users);
        return ServerProxy.getInstance().createGame(game);

    }

    public LoginResult login(String username, String password) {
        User user = new User(username, password);
        LoginResult lr = ServerProxy.getInstance().login(user);
        if (lr.getGameList() != null) {
            RootClientModel.setGames(lr.getGameList());

        }
        return lr;
    }

    public CommandResult endTurn(){
        String un = RootClientModel.getInstance().getUser().getUsername();
        return ServerProxy.getInstance().endTurn(un);
    }

    public LoginResult register(String username, String password) {
        User user = new User(username, password);
        LoginResult lr = ServerProxy.getInstance().register(user);
        if (lr.getGameList() != null) {
            RootClientModel.setGames(lr.getGameList());
        }
        return lr;
    }

    public CommandResult joinGame(Game game, String userColor) {
        User user = RootClientModel.getUser();
        return ServerProxy.getInstance().joinGame(game, user, userColor);

    }

    public void startGame(String gameId) {

        ServerProxy.getInstance().startGame(gameId);
    }

    public CommandResult sendChat(String raw_msg) {
        String sender = RootClientModel.getUser().getUsername();
        String msg = raw_msg + "\t(" + sender + ")";
        String gid = RootClientModel.getCurrentGame().getGameId();
        String uid = RootClientModel.getUser().getUsername();
        return ServerProxy.getInstance().sendChat(gid, msg, uid);
    }

    public CommandResult drawTrainCard(Integer position, String userID){
        return ServerProxy.getInstance().drawColorCard(position, userID);
    }

    public CommandResult drawDestinationCards(String userID){
        return ServerProxy.getInstance().drawDestinationCards(userID);
    }

    public CommandResult discardDestinationCard(List<Integer> cardIDs, String username) {
        DestCardList destCardList = new DestCardList(cardIDs);
        return ServerProxy.getInstance().discardDestinationCard(destCardList, username);
    }
}
