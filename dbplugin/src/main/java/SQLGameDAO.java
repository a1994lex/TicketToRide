import com.groupryan.dbplugin.IGameDao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.rowset.serial.SerialBlob;

/**
 * Created by arctu on 4/11/2018.
 */

public class SQLGameDAO implements IGameDao {




    @Override
    public void addCommandToGame(String gameid, byte[] command, int order) {
        SqlDatabase sql=new SqlDatabase(0);
        try {
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameCommandTable (" +
                    "gameID text NOT NULL,\n" +
                    "command BLOB NOT NULL,\n" +
                    "ordering integer NOT NULL,\n" +
                    ");");
            PreparedStatement prep = connection.prepareStatement("insert into GameCommandTable values (?, ?, ?);");

            Blob b = new SerialBlob(command);
            prep.setBlob(2, b);
            prep.setInt(3, order);
            prep.addBatch();
            prep.executeBatch();
            sql.endTransaction(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        int commandNumber=0;//??????

        SqlDatabase sql= new SqlDatabase(0);
        try {
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameTable (" +
                    "gameID text NOT NULL,\n" +
                    "commandNumber integer NOT NULL,\n" +
                    "game BLOB NOT NULL,\n" +
                    ");");
            PreparedStatement prep = connection.prepareStatement("insert into GameTable values (?, ?, ?);");
               prep.setString(1, gameid);
               prep.setInt(2, commandNumber);
            Blob b = new SerialBlob(gameSnapshot);
               prep.setBlob(3, b);
               prep.addBatch();
               prep.executeBatch();
            sql.endTransaction(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCommands(String gameid){
        try{
            SqlDatabase sql= new SqlDatabase(0);
            Connection connection = sql.startTransaction();
            Statement stat = connection.createStatement();
            stat.executeUpdate("drop table if exists GameCommandTable");
        } catch (Exception e) {
            System.out.println("CLEAR game command table FAILED");
        }
    }

    @Override
    public List<byte[]> getCommandsByGamdId(String gameid) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<byte[]> commands= new ArrayList<>();
        try {
            SqlDatabase sqlDB= new SqlDatabase(0);
            Connection connection = sqlDB.startTransaction();
            String sql = "select command from GameCommandTable where gameID='"+gameid+"' order by ordering";//TEST THIS
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                byte[] bytes = rs.getBytes(1);
                commands.add(bytes);//pray no memory errors
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
    public byte[] getSnapshotByGameId(String gameid) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String game=null;
        byte[] bytes = null;
        try {
            SqlDatabase sqlDB= new SqlDatabase(0);
            Connection connection = sqlDB.startTransaction();
            String sql = "select game from GameTable where gameID='"+gameid+"'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                bytes = rs.getBytes(1);
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
        return bytes;
    }

    public Map<String, List<byte[]>> getAllCommands(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map <String, List<byte[]>> commands= new TreeMap<>();
        try {
            SqlDatabase sqlDB= new SqlDatabase(0);
            Connection connection = sqlDB.startTransaction();
            String sql = "select * from GameCommandTable order by ordering";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String gameID = rs.getString(1);
                byte[] bytes = rs.getBytes(2);
                commands.get(gameID).add(bytes);
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

    public List<byte[]> getAllSnapshots(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<byte[]> games= new ArrayList<>();
        String game=null;
        try {
            SqlDatabase sqlDB= new SqlDatabase(0);
            Connection connection = sqlDB.startTransaction();
            String sql = "select game from GameTable ";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                byte[] bytes = rs.getBytes(1);
                games.add(bytes);//pray no memory errors
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




