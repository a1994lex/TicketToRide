package com.example.clientapp;

import com.groupryan.shared.models.Game;


public interface IJoinGameView {
    void onGameAdd();
    void onGameDelete();//int position);
    void join(String gameid);
    void error(String msg);
}
