package com.groupryan.client.ui;

import com.groupryan.shared.models.User;

public class RegisterPresenter {

    public RegisterPresenter(){

    }

    public void login(String username, String password){
        User u = new User(username, password);

    }

    public void register(String username, String password){
        User u = new User(username, password);
    }
}
