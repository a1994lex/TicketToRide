package com.groupryan.dbplugin;


import java.sql.Connection;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IDatabase {

    Connection startTransaction();
    void endTransaction(Connection connection);
    IUserDao getUserDao();
    IGameDao getGameDao();
    void setUp();
}
