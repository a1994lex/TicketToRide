package com.example.clientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.groupryan.client.ui.IJoinGameView;

public class JoinGameActivity extends AppCompatActivity implements IJoinGameView{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
    }
}
