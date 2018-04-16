package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.models.Game;
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (int i = 0; i < usersObj.size(); i++) {
            JsonElement userElem = usersObj.get(i);
            User u = gson.fromJson(userElem, User.class);
            users.add(u);
        }
        return users;
    }

    @Override
    public void dropTables() {

    }

    @Override
    public void addGameToUser(User user, String gameID) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String username = user.getUsername();
        for (int i = 0; i < usersObj.size(); i++) {
            JsonObject userObj = usersObj.get(i).getAsJsonObject();
            if (userObj.get("username").getAsString().equals(username)) {
                List<Game> gameList = new ArrayList<>();
                gameList.add(new Game("", gameID, 2));
                String gameListStr = gson.toJson(gameList);
                JsonElement gameListElem = new JsonParser().parse(gameListStr);
                userObj.add("gameList", gameListElem);
            }
        }
    }
}
