package com.groupryan.client.models;

import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by alex on 2/26/18.
 - gameId: String
 - history: List<String>
 - stats: Map<String, Stat>
 - players: List<String>
 - chat: List<String>
 - bankCards: List<Card>

 */

public class ClientGame extends Observable {
    String gameId;
    Player myPlayer;
    ArrayList<String> history;
    ArrayList<String> chat;
    ArrayList<TrainCard> bankCards;
    HashMap<String, Stat> stats;
    HashMap<String, String> playersColors;

    public ClientGame(Game game, Player player)
    {
        history = new ArrayList<>();
        chat = new ArrayList<>();
        bankCards = new ArrayList<>();

        this.gameId = game.getGameId();
        this.myPlayer = player;

        this.stats = new HashMap<>();
        this.playersColors = (HashMap<String, String>)game.getUsers();
    }

    public String getGameId() {
        return gameId;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public ArrayList<String> getChat() {
        return chat;
    }

    public ArrayList<TrainCard> getBankCards() {
        return bankCards;
    }

    public HashMap<String, Stat> getStats() {
        return stats;
    }

    public void updateChat(String msg){
        chat.add(msg);
        setChanged();
        notifyObservers(utils.CHAT);
    }

}
