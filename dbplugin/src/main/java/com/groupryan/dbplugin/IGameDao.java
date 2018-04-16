package com.groupryan.dbplugin;

import java.util.List;
import java.util.Map;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IGameDao {

    Boolean addCommandToGame(String gameid, byte[] command, int order);
    void updateGameSnapshot(String gameid, byte[] gameSnapshot);
    void clearCommands(String gameid);
    List<byte[]> getCommandsByGameId(String gameid);
    byte[] getSnapshotByGameId(String gameid);
    Map<String, List<byte[]>> getAllCommands();
    List<byte[]> getAllSnapshots();


}
