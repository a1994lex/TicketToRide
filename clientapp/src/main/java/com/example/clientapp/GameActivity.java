package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.clientapp.dialogs.DiscardDestCardDialogActivity;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;

import presenters.GamePlayPresenter;

public class GameActivity extends FragmentActivity {


    private BottomNavigationView mNav;
    private FloatingActionButton mMenuBtn;
    private FloatingActionButton mDrawCards;
    private FloatingActionButton mClaimRoute;
    private ImageView claimedRouteImg;

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
        claimedRouteImg = findViewById(R.id.claimed_route);
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
                testClaimRoute();
            }
        });

        gamePlayPresenter.stopLobbyPolling();
        startDiscardDestCardActivity();
    }

    public void startDiscardDestCardActivity() {
        Intent intent = new Intent(this, DiscardDestCardDialogActivity.class);
        intent.putExtra(utils.GAME_SETUP, true);
        startActivity(intent);
    }

    public void testClaimRoute() {
        claimedRouteImg.setVisibility(View.VISIBLE);
        gamePlayPresenter.testClaimRoute();
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
