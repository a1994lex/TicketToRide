package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonDatabase implements IDatabase {
    private File databaseFile;

    public JsonDatabase() {
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    @Override
    public void startTransaction() {
        // TODO: what to do here?
    }

    @Override
    public void endTransaction() {
        // TODO: what to do here?
    }

    @Override
    public IUserDao getUserDao() {
        return new JsonUserDao();
    }

    @Override
    public IGameDao getGameDao() {
        return new JsonGameDao();
    }

    @Override
    public void setUp() {
        File jsonFile = new File("jsonDatabase.json");
        if (!jsonFile.isFile()) {
            try {
                jsonFile.createNewFile();
                databaseFile = jsonFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
