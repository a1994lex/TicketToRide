package com.groupryan.dbplugin;

import com.groupryan.shared.models.User;

import java.util.List;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IUserDao {
    void registerUser(User user);
    void addGameToUser(User user, String gameID);
    List<User> getUsersList();
    void dropTables();
}
