import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ServerCommand;

import java.util.List;
import java.util.Map;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IGameDao {

    void addCommandToGame(String gameid, String command);
    void updateGameSnapshot(String gameid, String gameSnapshot);
    void clearCommands(String gameid);
    List<ServerCommand> getCommandsByGamdId(String gameid);
    String getSnapshotByGameId(String gameid);
    Map<String, List<ServerCommand>> getAllCommands();
    List<ServerGame> getAllSnapshots();
}
