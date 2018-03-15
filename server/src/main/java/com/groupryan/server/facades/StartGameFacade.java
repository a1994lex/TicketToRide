package com.groupryan.server.facades;

import com.groupryan.server.CommandManager;
import com.groupryan.server.models.RootServerModel;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * The startGameFacade is responsible for starting and preparing everything necessary so that a game can successfully transition
 * to a new activity.
 */
public class StartGameFacade {
    ArrayList<ClientCommand> commands = new ArrayList<>();

    /**
     *
     * @param gameId-is ensured to be an existing game ID and will be the gameID of the game where the start game button was clicked.
     * @return a CommandResult saying VALID or INVALID
     * pre- the caller promises to have already created an existing game and to pass in the correct gameID
     * post-the callee promises to successfully start the game and to prepare everything for the pollers of the players in the game.
     */
    public CommandResult start(String gameId) {
        //sets the game id = to TRUE and returns the literal VALID unless INVALID
        String result = RootServerModel.getInstance().startGame(gameId);
        CommandResult cr = new CommandResult();
        if (result.equals(utils.VALID)) {
            //if valid it will create a start game command
            //then it will prepare the game to be started
            activateGame(gameId);
            //gives all info necessary to start game to the players via polling
            setStats(gameId);
        }
        cr.setResultType(result);
        return cr;
    }

    /**
     *
     * @param gameId the same gameID passed into the startGameFacade Start function.
     * pre-same as before, game guarenteed to exist and the gameID to be the correct game id passed in earlier.
     * post-ServerGame of gameID created, players added to it, and all necessary commands to be polled are made
     */
    void activateGame(String gameId) {
        //get the root
        RootServerModel root = RootServerModel.getInstance();
        //get the associated gameID
        Game g = root.getGame(gameId);
        //make an empty server game with new decks
        root.createServerGame(gameId);
        int turn = 1;
        //for each player in the original game, create a player for them
        for (Map.Entry<String, String> entry : g.getUsers().entrySet()) {
            Player p = root.createPlayer(gameId, entry, turn);
            //add player to game
            root.addPlayertoGame(p.getUsername(), gameId);
            //make a start game command for that player
            CommandManager.getInstance().makeStartGameCommand(g, p);
            //increment the turn counter
            turn++;
        }
        //sends the bank to all players after they have all been made.
        CommandManager.getInstance().makeBankCommand(root.getServerGameByGameId(gameId).getServerGameID(), root.getServerGameByGameId(gameId).getBank());
        ServerGame sg=root.getServerGameByGameId(gameId);
        CommandManager.getInstance().makeTDeckCommand(sg.getServerGameID(), sg.getTDeckSize());
    }

    /**
     *
     * @param gameId assured to be the same gameID as the rest of the class
     * pre- that the servergame was succesfully created earlier and that it has the correct data necessary to perform the next call and
     * that the GAMEid is still the same as before
     * post- will create stat commands for all players and place them in their poller map
     */
    void setStats(String gameId) {
        RootServerModel root = RootServerModel.getInstance();
        //get servergame by id
        ServerGame g = root.getServerGameByGameId(gameId);
        //make a stat for each player
        for (Player p : g.getPlayers()) {
            CommandManager.getInstance().makeStatCommand(g.getServerGameID(), p.makeStat());

        }
    }
}
