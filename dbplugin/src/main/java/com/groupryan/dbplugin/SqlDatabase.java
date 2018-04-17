package com.groupryan.dbplugin;


import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

/**
 * Created by arctu on 4/11/2018.
 */

public class SqlDatabase implements IDatabase {

    int maxCommands;
    Connection connection;

    public SqlDatabase() {
    }

    @Override
    public void startTransaction() {
        Connection dbconnection = getConnection();
        try {
            dbconnection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = dbconnection;
    }

    @Override
    public void endTransaction() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException fe) {
                fe.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public IUserDao getUserDao() {
        return new SQLUserDAO(connection);
    }

    @Override
    public IGameDao getGameDao() {
        return new SQLGameDAO(maxCommands, connection);
    }

    @Override
    public void setMaxCommands(int maxCommands) {
        this.maxCommands = maxCommands;
    }

    private Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}