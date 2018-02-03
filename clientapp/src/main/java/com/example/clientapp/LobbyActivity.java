package com.example.clientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupryan.client.ui.ILobbyView;

public class LobbyActivity extends AppCompatActivity implements ILobbyView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }
}
