import com.groupryan.server.Server;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ServerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created by arctu on 4/11/2018.
 */

public class SQLGameDAO implements IGameDao{

    @Override
    public void addCommandToGame(String gameid, String command) {
        int order=0;  //HOW
        Connection connection = new SqlDatabase().startTransaction();
        Statement stat = null;
        try {
            stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameCommandTable (" +
                    "gameID text NOT NULL,\n" +
                    "command text NOT NULL,\n" +
                    "order text NOT NULL,\n" +
                    ");");
            PreparedStatement prep = connection.prepareStatement("insert into GameCommandTable values (?, ?, ?);");
            prep.setString(1, gameid);
            prep.setString(2, command);
            prep.setString(3, Integer.toString(order));
            prep.addBatch();
            prep.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //This is for the other table
    /*
    	Statement stat = connection.createStatement();
    stat.executeUpdate("create table if not exists GameTable (" +
                       "gameID text NOT NULL,\n" +
                       "commandNumber text NOT NULL,\n" +
                       "game text NOT NULL,\n" +
                       ");");
    PreparedStatement prep = connection.prepareStatement("insert into GameTable values (?, ?, ?);");
        try {
            prep.setString(1, gameID);
            prep.setString(2, Integer.toString(commandNumber));
            prep.setString(3, game);
            prep.addBatch();
            prep.executeBatch();
        } catch (SQLException sqlException) {
            System.err.println("Could NOT initialize Game");
            return false;
        }
     */

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




