package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/12/2018.
 */

public class JsonUserDao implements IUserDao {

    private JsonArray usersObj;

    public JsonUserDao(JsonArray usersObj) {
        this.usersObj = usersObj;
    }

    public JsonArray getUsersObj() {
        return usersObj;
    }

    public void setUsersObj(JsonArray usersObj) {
        this.usersObj = usersObj;
    }

    private void addUserElement() {
//    List<Integer> users = new ArrayList<>();
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    String usersJson = gson.toJson(users);
//    JsonElement usersElem = new JsonParser().parse(usersJson);
//    usersObj.add("users", usersElem);
    }

    @Override
    public void registerUser(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        JsonElement userElem = new JsonParser().parse(userStr);
        usersObj.add(userElem);
    }

    @Override
    public List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        JsonArray usersArray = usersObj.getAsJsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (int i = 0; i < usersArray.size(); i++) {
            JsonElement userElem = usersArray.get(i);
            User u = gson.fromJson(userElem, User.class);
            users.add(u);
        }
        return users;
    }
}
