package com.groupryan.client.models;

import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by alex on 2/26/18.
 * - gameId: String
 * - history: List<String>
 * - stats: Map<String, Stat>
 * - players: List<String>
 * - chat: List<String>
 * - bankCards: List<Card>
 */

public class ClientGame extends Observable {
    String gameId;
    Player myPlayer;
    ArrayList<String> history;
    ArrayList<Chat> chat;
    ArrayList<TrainCard> bankCards;
    HashMap<String, Stat> stats;
    Map<String, String> playersColors;

    public ClientGame(Game game, Player player) {
        history = new ArrayList<>();
        chat = new ArrayList<>();
        bankCards = new ArrayList<>();

        this.gameId = game.getGameId();
        this.myPlayer = player;

        this.stats = new HashMap<>();
        this.playersColors = game.getUsers();
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

    public ArrayList<Chat> getChat() {
        return chat;
    }

    public ArrayList<TrainCard> getBankCards() {
        return bankCards;
    }

    public HashMap<String, Stat> getStats() {
        return stats;
    }

    public void updateChat(Chat mychat) {

        chat.add(mychat);
        setChanged();
        notifyObservers(utils.CHAT);
    }

    public void setBank(Bank bank) {
        bankCards = bank.convertToArray();
    }

    public void updateHistory(String msg) {
        history.add(msg);
        setChanged();
        notifyObservers(utils.HISTORY);
    }

    public void updateStat(Stat stat) {
        stats.put(stat.getUsername(), stat);
        setChanged();
        notifyObservers(utils.STAT);
    }

    public Map<String, String> getPlayersColors() {
        return playersColors;
    }

    public void setPlayersColors(Map<String, String> playersColors) {
        this.playersColors = playersColors;
    }

    public void discardDestCards(List<Integer> cardIDs) {
        for (Integer cardID : cardIDs) {
            myPlayer.removeDestinationCard(cardID);
        }
        setChanged();
        notifyObservers(utils.DISCARD_DESTCARD);
    }
}
