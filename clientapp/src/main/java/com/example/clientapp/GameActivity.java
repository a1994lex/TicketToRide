package com.example.clientapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import async.Poller;
import presenters.GamePlayPresenter;

public class GameActivity extends AppCompatActivity implements IGameView {

    private BottomNavigationView mNav;
    private FloatingActionButton mMenuBtn;
    private FloatingActionButton mDrawCards;
    private FloatingActionButton mClaimRoute;
    private int mapUpdatePhase;
    private GamePlayPresenter gamePlayPresenter = new GamePlayPresenter(this);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.history:
                    startHistory();
                    return true;
                case R.id.chat:
                    startActivity();
                    return true;
                case R.id.stats:
//                    mTextMessage.setText(R.string.title_notifications);
                    startStats();
                    return true;
//                case R.id.bank:
//                    mTextMessage.setText("BANK");
                case R.id.game:
                    return true;
                case R.id.hide:
                    mNav.setVisibility(View.INVISIBLE);
                    mMenuBtn.setVisibility(View.VISIBLE);
                    mClaimRoute.setVisibility(View.VISIBLE);
                    mDrawCards.setVisibility(View.VISIBLE);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mapUpdatePhase = 0;

        mNav = findViewById(R.id.navigation);
        mMenuBtn = findViewById(R.id.menu_btn);
        mClaimRoute = findViewById(R.id.claim_route_btn);
        mDrawCards = findViewById(R.id.draw_card_btn);

        mNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.setVisibility(View.VISIBLE);
                mClaimRoute.setVisibility(View.INVISIBLE);
                mDrawCards.setVisibility(View.INVISIBLE);
                mMenuBtn.setVisibility(View.INVISIBLE);
            }
        });
        mClaimRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRoot();
            }
        });


        // TODO: Move to Presenter eventually
        Poller.get().stop();
    }

    public void modifyRoot() {
        switch (mapUpdatePhase) {
            case 0:
                Toast.makeText(this, "Testing...adding points to Green player--just because",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("jimbob");
                mapUpdatePhase++;
                break;
            case 1:
                Toast.makeText(this, "Testing...other player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("jimbob");
                mapUpdatePhase++;
                break;
            case 2:
                Toast.makeText(this, "Testing...current player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Also changing number of trains and train cards",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("q");
                mapUpdatePhase++;
                break;
            case 3:
                Toast.makeText(this, "Testing...other player is claiming a route",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.claimRouteTest("joanna");
                mapUpdatePhase++;
                break;
            case 4:
                Toast.makeText(this, "Testing...joanna now has 2 destination cards",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeDestinationCards("joanna");
                mapUpdatePhase++;
                break;
            case 5:
                Toast.makeText(this, "Testing...adding chat message from jimbob",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.addChatMessage("jimbob", "herro!");
                mapUpdatePhase++;
                break;
            case 6:
                break;
        }
    }

    @Override
    public void drawRoute(String playerColor, HashSet<RouteSegment> routeSegments) {
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        ConstraintLayout constraintLayout = findViewById(R.id.container);
        for (RouteSegment routeSegment : routeSegments) {
            LineView lineView = new LineView(this);
            lineView.setLayoutParams(constraintLayoutParams);
            lineView.setColor(playerColor);
            lineView.setxCoordinateA(routeSegment.getxCoordinateA());
            lineView.setyCoordinateA(routeSegment.getyCoordinateA());
            lineView.setxCoordinateB(routeSegment.getxCoordinateB());
            lineView.setyCoordinateB(routeSegment.getyCoordinateB());
            lineView.setRouteId(routeSegment.getRouteId());
            lineView.setVisibility(View.VISIBLE);
            constraintLayout.addView(lineView);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(lineView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);
            constraintSet.connect(lineView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    public void startActivity(){
        Intent intent = new Intent(this, ChatAndHistoryActivity.class);
        startActivity(intent);
    }

    public void startStats(){
        Intent intent = new Intent(this, GameStatActivity.class);
        startActivity(intent);
    }

    public void startHistory(){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
