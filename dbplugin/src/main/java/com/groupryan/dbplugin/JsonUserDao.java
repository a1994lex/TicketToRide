package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonUserDao implements IUserDao {

    public JsonUserDao() {

    }

    private JsonObject getDatabaseAsJsonObject() {
        JsonDatabase database = new JsonDatabase();
        File databaseFile = database.getDatabaseFile();
        JsonObject databaseObj = null;
        try {
            String jsonStr = FileUtils.readFileToString(databaseFile, "UTF-8");
            databaseObj = new JsonParser().parse(jsonStr).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return databaseObj;
    }

    private JsonObject addUserElement(JsonObject databaseObj) {
        List<User> users = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String usersJson = gson.toJson(users);
        JsonElement usersElem = new JsonParser().parse(usersJson);
        databaseObj.add("users", usersElem);
        return databaseObj;
    }

    @Override
    public void loginUser(User user) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        if (!databaseObj.has("users")) {
            databaseObj = addUserElement(databaseObj);
        }
        JsonArray usersArray = databaseObj.getAsJsonArray("users");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        JsonElement userElem = new JsonParser().parse(userStr);
        usersArray.add(userElem);
        databaseObj.add("users", usersArray);
    }

    @Override
    public void registerUser(User user) {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        if (!databaseObj.has("users")) {
            databaseObj = addUserElement(databaseObj);
        }
        JsonArray usersArray = databaseObj.getAsJsonArray("users");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        JsonElement userElem = new JsonParser().parse(userStr);
        usersArray.add(userElem);
        databaseObj.add("users", usersArray);
    }

    @Override
    public List<User> getUsersList() {
        JsonObject databaseObj = getDatabaseAsJsonObject();
        List<User> users = new ArrayList<>();
        if (databaseObj.has("users")) {
            JsonArray usersArray = databaseObj.getAsJsonArray("users");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (int i = 0; i < usersArray.size(); i++) {
                JsonElement userElem = usersArray.get(i);
                User u = gson.fromJson(userElem, User.class);
                users.add(u);
            }
        }
        return users;
    }
}
