package com.groupryan.server;

import com.groupryan.server.models.RootServerModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ClientCommandFactory;
import com.groupryan.shared.models.Card;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
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


    Map<User, List<ClientCommand>> usersCommands = new HashMap<>();
    Map<String, Map<String, List<ClientCommand>>> gamePlayerCommands = new HashMap();
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null) {

            // TODO make usersCommands Map

            instance = new CommandManager();
        }
        return instance;
    }

    ClientCommandFactory factory;


    public CommandManager() {
        this.factory = new ClientCommandFactory();
    }

    public List<ClientCommand> getCommands(User user) {
        List<ClientCommand> commands = this.usersCommands.get(user);
        this.usersCommands.put(user, new ArrayList<>());
        return commands;
    }
    public List<ClientCommand> getGameCommands(String gameId, String playerId){
        if (!this.gamePlayerCommands.containsKey(gameId)){
            // Creates an entry for game in the gamePlayerCommands Map using all the users from the game
            HashMap<String, List<ClientCommand>> userCmdMap = new HashMap<>();
            for (String user:RootServerModel.getInstance().getGame(gameId).getUsers().keySet()){
                userCmdMap.put(user, new ArrayList<>());
            }
            this.gamePlayerCommands.put(gameId, userCmdMap);
        }
        List<ClientCommand> playerCommands = this.gamePlayerCommands.get(gameId).get(playerId);
        this.gamePlayerCommands.get(gameId).get(playerId).clear(); // Clears the players list of commands
        return playerCommands;
    }

    private void _addCommandToGameMap(ClientCommand command, String gameId, String exceptionPlayer)
    {
        assert (this.gamePlayerCommands.containsKey(gameId));
        if (exceptionPlayer != null)
            // exceptionPlayer is null if all players need to receive command,
            // I'm not sure if it will ever not be null, but this capability is here
        {
            assert (this.gamePlayerCommands.get(gameId).containsKey(exceptionPlayer));
        }
        for (String playerId: this.gamePlayerCommands.get(gameId).keySet())
        {
            /* only adds a command to the list if the player is not the exceptionPlayer.
             exceptionPlayer is another word for the player who is currently having their turn, and
             they don't need this specific command handed to them.*/

            if (!playerId.equals(exceptionPlayer))
            {
                this.gamePlayerCommands.get(gameId).get(playerId).add(command); //add the command to list
            }
        }
    }

    private void _addCommandToMap(ClientCommand command, Object o, String commandType) {
        for (Map.Entry<User, List<ClientCommand>> entry : this.usersCommands.entrySet()) {
            switch (commandType) {
                case utils.JOIN_GAME:
                    User u = (User) o;
                    if (!entry.getKey().equals(u)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(u, commands);
                    }
                    break;
                case utils.CREATE_GAME:
                    Game g = (Game) o;
                    String userId = (String) new ArrayList(g.getUsers().keySet()).get(0);
                    User user = RootServerModel.getUser(userId);
                    if (!entry.getKey().equals(user)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(user, commands);
                    }
                    break;
                case utils.START_GAME:
                    Game g2 = (Game) o;
                    String userId2 = (String) new ArrayList(g2.getUsers().keySet()).get(0);
                    User user2 = RootServerModel.getUser(userId2);
                    if (!entry.getKey().equals(user2)) {
                        List<ClientCommand> commands = entry.getValue();
                        commands.add(command);
                        this.usersCommands.put(user2, commands);
                    }
                    break;
            }
        }
    }

    public void addChatCommand(String chat_msg, String gameId, String playerId) {
        ClientCommand command = factory.createChatCommand(chat_msg);
        this._addCommandToGameMap(command, gameId, playerId);
    }

    public ClientCommand makeJoinGameCommand(Game game, User user, String userColor) {
        ClientCommand command = factory.createJoinGameCommand(game, user, userColor);
        this._addCommandToMap(command, user, utils.JOIN_GAME);
        return command;
    }

    public ClientCommand makeCreateGameCommand(Game game) {
        ClientCommand command = factory.createCreateGameCommand(game);
        this._addCommandToMap(command, game, utils.CREATE_GAME);
        return command;
    }

    public ClientCommand makeStartGameCommand(Game game, Player p) {
        ClientCommand command = factory.createStartGameCommand(game, p);

        this._addCommandToMap(command, game, utils.START_GAME);
        return command;
    }

    public ClientCommand makeDiscardDestinationCardCommand(List<Integer> cardIDs, String username){
        ClientCommand command = factory.createDiscardDestinationCardCommand(cardIDs, username);
//TODO  this needs to be fixed too
        //this._addCommandToMap(command, game, utils._GAME);
        return command;
    }

    public ClientCommand makeLoginCommand(User user) {
        ClientCommand command = factory.createLoginCommand(user);
        this.usersCommands.put(user, new ArrayList<>());
        return command;
    }

    public ClientCommand makeRegisterCommand(User user) {
        ClientCommand command = factory.createRegisterCommand(user);
        this.usersCommands.put(user, new ArrayList<>());
        return command;

    }

    public ClientCommand makeDrawThreeCardsCommand(List<DestCard> cards){
        ClientCommand command = factory.createDrawThreeCardsCommand(cards);
        return command;
    }


}