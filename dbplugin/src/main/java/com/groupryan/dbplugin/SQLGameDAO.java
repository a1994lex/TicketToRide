package com.groupryan.dbplugin;

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
    private int maxCommands;
    private Connection connection;

    public SQLGameDAO(int maxCommands, Connection connection){
        this.maxCommands=maxCommands;
        this.connection=connection;
    }

    @Override
    public Boolean addCommandToGame(String gameid, byte[] command, int order) {
        boolean max=false;
        try {
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists gamecommandtable (" +
                    "gameID text NOT NULL,\n" +
                    "command BLOB NOT NULL,\n" +
                    "ordering integer NOT NULL \n" +
                    ");");

            PreparedStatement prep = connection.prepareStatement("insert into gamecommandtable values (?, ?, ?);");
            prep.setString(1, gameid);
            Blob b = new SerialBlob(command);
            prep.setBytes(2, command);
            prep.setInt(3, order);
            prep.addBatch();
            prep.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int currentCommands=0;
            try {
                String sql = "select command from gamecommandtable where gameID='"+gameid+"' order by ordering";//TEST THIS
                stmt = connection.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    currentCommands++;
                }
                if(currentCommands==maxCommands){
                    max=true;
                }
                Statement stat = connection.createStatement();
                stat.executeUpdate("update gamecommandtable set ordering ='"+currentCommands+"' where ordering='"+-1+"'");//kjfkjf
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }

    @Override
    public void updateGameSnapshot(String gameid, byte[] gameSnapshot) {
        try {
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists GameTable (" +
                    "gameID text NOT NULL,\n" +
                    "game BLOB NOT NULL" +
                    ");");
            stat.executeUpdate("delete from GameTable where gameID='"+gameid+"'");
            PreparedStatement prep = connection.prepareStatement("insert into GameTable values (?, ?);");
            prep.setString(1, gameid);
            Blob b = new SerialBlob(gameSnapshot);
            prep.setBytes(2, gameSnapshot);
            prep.addBatch();
            prep.executeBatch();
            clearCommands(gameid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCommands(String gameid){
        try{
            Statement stat = connection.createStatement();
            stat.executeUpdate("delete from gamecommandtable where gameID='"+gameid+"'");
        } catch (Exception e) {
            System.out.println("CLEAR game commands FAILED");
        }
    }

    @Override
    public List<byte[]> getCommandsByGameId(String gameid) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<byte[]> commands= new ArrayList<>();
        try {
            String sql = "select command from gamecommandtable where gameID='"+gameid+"' order by ordering";//TEST THIS
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                byte[] bytes = rs.getBytes(1);
                commands.add(bytes);//pray no memory errors
            }
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
        byte[] bytes = null;
        try {
            String sql = "select game from GameTable where gameID='"+gameid+"'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                bytes = rs.getBytes(1);
            }
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
            String sql = "select * from gamecommandtable order by ordering";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String gameID = rs.getString(1);
                byte[] bytes = rs.getBytes(2);
                if(commands.get(gameID)==null){
                    commands.put(gameID, new ArrayList<>());
                }
                commands.get(gameID).add(bytes);
            }
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
            String sql = "select game from GameTable ";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                byte[] bytes = rs.getBytes(1);
                games.add(bytes);//pray no memory errors
            }
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




