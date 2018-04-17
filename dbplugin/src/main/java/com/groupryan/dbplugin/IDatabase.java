package com.groupryan.dbplugin;


/**
 * Created by clairescout on 4/7/18.
 */

public interface IDatabase {
    void startTransaction();
    void endTransaction();
    IUserDao getUserDao();
    IGameDao getGameDao();
    void setMaxCommands(int maxCommands);
}
