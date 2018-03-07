package com.groupryan.server;

import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ClientCommandFactory;
import com.groupryan.shared.models.Bank;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CommandManager {


    private Map<String, List<ClientCommand>> usersCommands = new HashMap<>();
    private Map<String, Map<String, List<ClientCommand>>> gamePlayerCommands = new HashMap();
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private ClientCommandFactory factory;

    public CommandManager() {
        this.factory = new ClientCommandFactory();
    }

    // ------------------------------ Polled method -----------------------------

    public List<ClientCommand> getCommands(User user) {
        List<ClientCommand> commands = this.usersCommands.get(user.getUsername());
        this.usersCommands.put(user.getUsername(), new ArrayList<>());
        return commands;
    }

    public List<ClientCommand> getGameCommands(String gameId, String playerId) {
        assert((gameId != null) && (playerId != null));
        if (!this.gamePlayerCommands.containsKey(gameId)) {
            // Creates an entry for game in the gamePlayerCommands Map using all the users from the game
           createGameCommandMapEntry(gameId);
        }
        int size = this.gamePlayerCommands.get(gameId).get(playerId).size();
        System.out.println("command size in server: "+ size);
        List<ClientCommand> playerCommands = new ArrayList<>(this.gamePlayerCommands.get(gameId).get(playerId));
        this.gamePlayerCommands.get(gameId).get(playerId).clear(); // Clears the players list of commands
        return playerCommands;
    }

    private void _addCommandToGameMap(ClientCommand command, String gameId, String exceptionPlayer) {
        assert (this.gamePlayerCommands.containsKey(gameId));
        if (exceptionPlayer != null)
        // exceptionPlayer is null if all players need to receive command,
        // I'm not sure if it will ever not be null, but this capability is here
        {
            assert (this.gamePlayerCommands.get(gameId).containsKey(exceptionPlayer));
        }
        for (String playerId : this.gamePlayerCommands.get(gameId).keySet()) {
            /* only adds a command to the list if the player is not the exceptionPlayer.
             exceptionPlayer is another word for the player who is currently having their turn, and
             they don't need this specific command handed to them.*/

            if (!playerId.equals(exceptionPlayer)) {
                this.gamePlayerCommands.get(gameId).get(playerId).add(command); //add the command to list
            }
        }
    }

    private void createGameCommandMapEntry(String gameId){
        HashMap<String, List<ClientCommand>> userCmdMap = new HashMap<>();
        for (String user : RootServerModel.getInstance().getGame(gameId).getUsers().keySet()) {
            userCmdMap.put(user, new ArrayList<>());
        }
        this.gamePlayerCommands.put(gameId, userCmdMap);
    }

    // ------------------------------ Phase 1 Commands -----------------------------

    // LoginCommand goes to caller only
    public ClientCommand makeLoginCommand(User user) {
        ClientCommand command = factory.createLoginCommand(user);
        this.usersCommands.put(user.getUsername(), new ArrayList<>());
        return command;
    }

    // RegisterCommand goes to caller only
    public ClientCommand makeRegisterCommand(User user) {
        ClientCommand command = factory.createRegisterCommand(user);
        this.usersCommands.put(user.getUsername(), new ArrayList<>());
        return command;
    }

    // CreateGameCommand goes to all users
    public ClientCommand makeCreateGameCommand(Game game) {
        ClientCommand command = factory.createCreateGameCommand(game);
        this._addCreateGameCommand(command);
        return command;
    }

    private void _addCreateGameCommand(ClientCommand command) {
        for (Map.Entry<String, List<ClientCommand>> entry : this.usersCommands.entrySet()) {
            entry.getValue().add(command);
        }
    }

    // JoinGameCommand goes to all users
    public ClientCommand makeJoinGameCommand(Game game, User user, String userColor) {
        ClientCommand command = factory.createJoinGameCommand(game, user, userColor);
        addUsertoGameMap(game.getGameId(), user.getUsername());
        this._addJoinGameCommand(command);
        return command;
    }

    private void _addJoinGameCommand(ClientCommand command) {
        for (Map.Entry<String, List<ClientCommand>> entry : this.usersCommands.entrySet()) {
            entry.getValue().add(command);
        }
    }

    private void addUsertoGameMap(String gid, String uid){
        if (gamePlayerCommands.containsKey(gid) && !gamePlayerCommands.get(gid).containsKey(uid)) {
            gamePlayerCommands.get(gid).put(uid, new ArrayList<>());
        }
    }

    // ------------------------------ Game Commands -----------------------------

    // StartGameCommand goes to all users
    public ClientCommand makeStartGameCommand(Game game, Player p) {
        ClientCommand command = factory.createStartGameCommand(game, p);
        String gameId = game.getGameId();
        if (!this.gamePlayerCommands.containsKey(gameId)) {
            // Creates an entry for game in the gamePlayerCommands Map using all the users from the game
            createGameCommandMapEntry(gameId);
        }
        this.gamePlayerCommands.get(gameId).get(p.getUsername()).add(command);
        return command;
    }

    public void addChatCommand(String chat_msg, String gameId, String playerId) {
        ClientCommand command = factory.createChatCommand(chat_msg, playerId);
        this._addCommandToGameMap(command, gameId, null);
    }

    public void addHistoryCommand(String history, String gameId, String playerId) {
        ClientCommand command = factory.createHistoryCommand(history);
        this._addCommandToGameMap(command, gameId, playerId);
    }

    // DiscardDestinationCardCommand goes to caller only
    public ClientCommand makeDiscardDestinationCardCommand(List<Integer> cardIDs, String username) {
        ClientCommand command = factory.createDiscardDestinationCardCommand(cardIDs, username);
        return command;
    }

    // DiscardDestinationCardCommand goes to caller only
    public ClientCommand makeDrawThreeCardsCommand(List<DestCard> cards) {
        ClientCommand command = factory.createDrawThreeCardsCommand(cards);
        return command;
    }

    // DrawColorCardCommand goes to caller only
    public ClientCommand makeDrawColorCardCommand() {
        // TODO create commandFactory method
        ClientCommand command = null;
        return command;
    }

    // UpdateFaceUpCommand goes to all playas in game
    public ClientCommand makeUpdateFaceUpCommand() {
        // TODO create commandFactory method
        ClientCommand command = null;
        // TODO put in each player of game list
        return command;
    }

    public void makeStatCommand(String gameId, Stat stat){
        ClientCommand command=factory.createStatCommand(stat);
        _addCommandToGameMap(command, gameId,null);
    }

    public void makeBankCommand(String gameId, Bank bank){
        ClientCommand command=factory.createBankCommand(bank);
        _addCommandToGameMap(command, gameId,null);
    }

    // ------------------------------ Methods for Testing  -----------------------------

    public int numberOfUsers() {
        return usersCommands.size();
    }

}