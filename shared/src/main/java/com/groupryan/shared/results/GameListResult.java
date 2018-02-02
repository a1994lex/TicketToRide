package com.groupryan.shared.results;

import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class GameListResult extends CommandResult {


    private List<Game> games = new ArrayList<>();

    public GameListResult(String exceptionType, String exceptionMessage, List<ClientCommand> clientCommands, String resultType, List<Game> games){
        super(exceptionType, exceptionMessage, clientCommands, resultType);
        this.games = games;
    }

    public GameListResult(){
        super();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
