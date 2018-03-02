package com.example.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import async.Poller;

public class GamePlayActivity extends AppCompatActivity {

    Button chatHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        chatHistoryButton = findViewById(R.id.button2);
        chatHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity();
            }
        });

        // TODO: Move to Presenter eventually
        Poller.get().stop();
    }

    public void startActivity(){
        Intent intent = new Intent(this, ChatAndHistoryActivity.class);
        startActivity(intent);
    }
}
