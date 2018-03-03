package com.example.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.groupryan.client.models.RootClientModel;

import async.Poller;
import presenters.GamePlayPresenter;

public class GamePlayActivity extends AppCompatActivity {

    private Button statsButton;
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
        statsButton = findViewById(R.id.stats_button);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStats();
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

    public void startStats() {
        Intent intent = new Intent(this, GameStatActivity.class);
        startActivity(intent);
    }
      
    public void testClaimRoute() {
        RootClientModel.getCurrentGame().updateHistory("this is testing from claim route");
        RootClientModel.getCurrentGame().updateHistory("hello again");
        claimedRouteImg.setVisibility(View.VISIBLE);
        gamePlayPresenter.testClaimRoute();
    }
}
