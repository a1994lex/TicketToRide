package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.clientapp.dialogs.DiscardDestCardDialogActivity;
import presenters.GamePlayPresenter;

public class GameActivity extends FragmentActivity implements IGameView {

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
                    removePrevFrag(utils.HISTORY);
                    addFragment(R.id.chat_history_fragment,
                            new HistoryFragment(), utils.HISTORY);
                    return true;
                case R.id.chat:
                    removePrevFrag(utils.CHAT);
                    addFragment(R.id.chat_history_fragment,
                            new ChatFragment(), utils.CHAT);
                    return true;
                case R.id.stats:
                    removePrevFrag(utils.STAT);
                    addFragment(R.id.stat_fragment,
                            new GameStatFragment(), utils.STAT);
                    return true;
                case R.id.game:
                    removePrevFrag("home");
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
        mDrawCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removePrevFrag(utils.BANK);
                addFragment(R.id.bank_fragment,
                        new BankFragment(), utils.BANK);
               /* FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.bank_fragment,new BankFragment(),utils.BANK);
                transaction.addToBackStack(null);
                transaction.commit();*/
            }
        });
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

        gamePlayPresenter.stopLobbyPolling();
        startDiscardDestCardActivity();
    }

    public void cardsDiscarded() {

    }

    public void startDiscardDestCardActivity() {
        Intent intent = new Intent(this, DiscardDestCardDialogActivity.class);
        intent.putExtra(utils.GAME_SETUP, true);
        startActivity(intent);
    }

    public void modifyRoot() {
        switch (mapUpdatePhase) {
            case 0:
                Toast.makeText(this, "Testing...adding train cards to Green player--just because",
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
                Toast.makeText(this, "Testing...adding train cards to current player",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeTrainCards("q");
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

    public void startActivity() {
        Intent intent = new Intent(this, ChatAndHistoryActivity.class);
        startActivity(intent);
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String FRAGMENT_ID) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, FRAGMENT_ID)
                .disallowAddToBackStack()
                .commit();
    }

    private void removePrevFrag(String tag) {
        List<String> removeFragIds = new ArrayList<>();
        switch (tag) {
            case utils.CHAT:
                removeFragIds.add(utils.STAT);
                removeFragIds.add(utils.HISTORY);
                break;
            case utils.HISTORY:
                removeFragIds.add(utils.STAT);
                removeFragIds.add(utils.CHAT);
                break;
            case utils.STAT:
                removeFragIds.add(utils.CHAT);
                removeFragIds.add(utils.HISTORY);
                break;
            default:
                removeFragIds.add(utils.CHAT);
                removeFragIds.add(utils.HISTORY);
                removeFragIds.add(utils.STAT);
        }
        for (String fragmentId : removeFragIds) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentId);
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }
}
