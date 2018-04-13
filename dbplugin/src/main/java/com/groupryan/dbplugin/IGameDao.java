package com.groupryan.dbplugin;

import com.groupryan.shared.commands.ServerCommand;

import java.util.List;
import java.util.Map;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IGameDao {

    void addCommandToGame(String gameid, byte[] command);
    void updateGameSnapshot(String gameid, byte[] gameSnapshot);
    void clearCommands(String gameid);
    List<byte[]> getCommandsByGamdId(String gameid);
    byte[] getSnapshotByGameId(String gameid);
    Map<String, List<byte[]>> getAllCommands();
    List<byte[]> getAllSnapshots();
}
