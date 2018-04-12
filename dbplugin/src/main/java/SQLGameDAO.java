import com.groupryan.server.Server;
import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ServerCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by arctu on 4/11/2018.
 */

public class SQLGameDAO implements IGameDao{

    @Override
    public void addCommandToGame(String gameid, String command) {
        int order=0;  //HOW
        SqlDatabase sql=new SqlDatabase();

        try {
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameCommandTable (" +
                    "gameID text NOT NULL,\n" +
                    "command text NOT NULL,\n" +
                    "ordering integer NOT NULL,\n" +
                    ");");
            PreparedStatement prep = connection.prepareStatement("insert into GameCommandTable values (?, ?, ?);");
            prep.setString(1, gameid);
            prep.setString(2, command);
            prep.setInt(3, order);
            prep.addBatch();
            prep.executeBatch();
            sql.endTransaction(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateGameSnapshot(String gameid, String gameSnapshot) {
        int commandNumber=0;//??????

        SqlDatabase sql= new SqlDatabase();
        try {
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameTable (" +
                                  "gameID text NOT NULL,\n" +
                                  "commandNumber integer NOT NULL,\n" +
                                  "game text NOT NULL,\n" +
                                  ");");
            PreparedStatement prep = connection.prepareStatement("insert into GameTable values (?, ?, ?);");
            prep.setString(1, gameid);
            prep.setInt(2, commandNumber);
            prep.setString(3, gameSnapshot);
            prep.addBatch();
            prep.executeBatch();
            sql.endTransaction(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCommands(String gameid){
        try{
            SqlDatabase sql= new SqlDatabase();
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("drop table if exists GameCommandTable");
        } catch (Exception e) {
            System.out.println("CLEAR game command table FAILED");
        }
    }

    @Override
    public List<ServerCommand> getCommandsByGamdId(String gameid) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServerCommand> commands= new ArrayList<>();
        try {
            SqlDatabase sqlDB= new SqlDatabase();
            Connection connection = sqlDB.startTransaction();
            String sql = "select command from GameCommandTable where gameID='"+gameid+"' order by ordering";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String cmnd = rs.getString(1);
             //   commands.add(cmnd);
            }
            sqlDB.endTransaction(connection);
        }
        catch (SQLException e) {
            System.err.println("GET commands by gameID FAILED");
        }
        finally {
           try {
               if (rs != null) rs.close();
               if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return commands;
    }

    @Override
    public ServerGame getSnapshotByGameId(String gameid) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String game=null;
        ServerGame sg = null;
        try {
            SqlDatabase sqlDB= new SqlDatabase();
            Connection connection = sqlDB.startTransaction();
            String sql = "select game from GameTable where gameID='"+gameid+"'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                game = rs.getString(1);
                //sg. do something
            }
            sqlDB.endTransaction(connection);
        }
        catch (SQLException e) {
            System.err.println("GET game by gameID FAILED");
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return sg;
    }

    public Map<String, List<ServerCommand>> getAllCommands(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map <String, List<ServerCommand>> commands= new TreeMap<>();
        try {
            SqlDatabase sqlDB= new SqlDatabase();
            Connection connection = sqlDB.startTransaction();
            String sql = "select * from GameCommandTable order by ordering";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String gameID = rs.getString(1);
                String command = rs.getString(2);
                String ordering = rs.getString(3);
                ServerCommand sc=null;
                //GOTTA MAKE THE STRONG EQUAL A SERVER COMMAND
                commands.get(gameID).add(sc);
            }
            sqlDB.endTransaction(connection);
        }
        catch (SQLException e) {
            System.err.println("GET commands by gameID FAILED");
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return commands;
    }

    public List<ServerGame> getAllSnapshots(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServerGame> games= new ArrayList<>();
        String game=null;
        try {
            SqlDatabase sqlDB= new SqlDatabase();
            Connection connection = sqlDB.startTransaction();
            String sql = "select game from GameTable ";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                game = rs.getString(1);
                ServerGame sg=null;
                //MAKE THIS WORK
                games.add(sg);
            }
            sqlDB.endTransaction(connection);
        }
        catch (SQLException e) {
            System.err.println("GET all games FAILED");
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return games;
    }
}




