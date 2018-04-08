import com.groupryan.shared.commands.ServerCommand;

import java.util.List;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IGameDao {

    void addCommandToGame(String gameid, String command);
    void updateGameSnapshot(String gameid, String gameSnapshot);
    void clearCommands(String gameid);
    List<ServerCommand> getCommandsByGamdId(String gameid);
    String getSnapshotByGameId(String gameid);
    List<ServerCommand> getAllCommands();
    List<String> getAllSnapshots();
}
