import com.groupryan.server.Server;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ServerCommand;

import java.util.List;
import java.util.Map;

/**
 * Created by arctu on 4/11/2018.
 */

public class SQLGameDAO implements IGameDao{
    public SQLGameDAO(){}




    @Override
    public void addCommandToGame(String gameid, String command) {

    }

    @Override
    public void updateGameSnapshot(String gameid, String gameSnapshot) {

    }

    public void clearCommands(String gameid){

    }

    @Override
    public List<ServerCommand> getCommandsByGamdId(String gameid) {
        return null;
    }

    @Override
    public String getSnapshotByGameId(String gameid) {
        return null;
    }

    public Map<String, List<ServerCommand>> getAllCommands(){
        return null;
    }
    public List<ServerGame> getAllSnapshots(){
        return null;
    }
}




