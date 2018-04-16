package com.groupryan.dbplugin;

import com.groupryan.dbplugin.IUserDao;
import com.groupryan.shared.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arctu on 4/11/2018.
 */

public class SQLUserDAO implements IUserDao {
    private Connection connection;

    public SQLUserDAO (Connection connection){
        this.connection=connection;
    }

    @Override
    public void registerUser(User user) {
        try {
            Statement stat = connection.createStatement();
            stat.executeUpdate("create table if not exists User ( username text NOT NULL UNIQUE,\n" +
                    "password text NOT NULL,\n" +
                    "game text" +//something else
                    ");");
            PreparedStatement prep = connection.prepareStatement("insert into User values (?, ?, ?);");

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            if(user.getGameList().size()>0){
                prep.setString(3, user.getGameList().get(0).getGameId());
            }
            else{
                prep.setString(3, "NONE");
            }

            prep.addBatch();
            prep.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addGameToUser(User user, String gameID) {
        try{
            Statement stat = connection.createStatement();
            stat.executeUpdate("update User set game ='"+gameID+"' where username='"+user.getUsername()+"'");
        } catch (Exception e) {
            System.out.println("UPDATE USER GAME FAILED");
        }

    }

    @Override
    public List<User> getUsersList() {
        User u;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            String sql = "select * from User";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String gameID = rs.getString(3);//this does nothing
                u=new User(username, pass);//, gamelist);
                users.add(u);
            }
        }
        catch (SQLException e) {
            System.err.println("Could NOT get User");
        }
        finally {
           try {
               if (rs != null) rs.close();
               if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void dropTables(){
        try{
            Statement stat = connection.createStatement();
            stat.executeUpdate("drop table if exists User");
        } catch (Exception e) {
            System.out.println("DROP USER TABLE FAILED");
        }
    }
}
