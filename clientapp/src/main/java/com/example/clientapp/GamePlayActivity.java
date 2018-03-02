package com.example.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import async.Poller;
import presenters.GamePlayPresenter;

public class GamePlayActivity extends AppCompatActivity {

    private Button chatHistoryButton;
    private Button claimRouteButton;
    private ImageView claimedRouteImg;
    private GamePlayPresenter gamePlayPresenter = new GamePlayPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        chatHistoryButton = findViewById(R.id.button2);
        claimRouteButton = findViewById(R.id.claim_route_button);
        claimedRouteImg = findViewById(R.id.claimed_route);
        chatHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity();
            }
        });

        claimRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testClaimRoute();
            }
        });

        // TODO: Move to Presenter eventually
        Poller.get().stop();
    }

    public void startActivity(){
        Intent intent = new Intent(this, ChatAndHistoryActivity.class);
        startActivity(intent);
    }

    public void testClaimRoute() {
        claimedRouteImg.setVisibility(View.VISIBLE);
        gamePlayPresenter.testClaimRoute();
    }
}
