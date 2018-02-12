package com.example.clientapp.dialogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.clientapp.IJoinGameView;
import com.example.clientapp.LobbyActivity;
import com.groupryan.shared.utils;

public class JoinGameDialogActivity extends AppCompatActivity implements IJoinGameView{
    @Override
    public void onGameAdd() {
//        This is really implemented in the JoinGameActivity

    }

    @Override
    public void onGameDelete(int position) {
//        This is really implemented in the JoinGameActivity

    }

    @Override
    public void error(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void join(String gameid) {
        Intent i = new Intent(JoinGameDialogActivity.this, LobbyActivity.class);
        i.putExtra(utils.GAME_ID_TAG, gameid);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
