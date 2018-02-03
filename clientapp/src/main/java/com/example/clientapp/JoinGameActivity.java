package com.example.clientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupryan.client.ui.IJoinGameView;

public class JoinGameActivity extends AppCompatActivity implements IJoinGameView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }
}
