package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import async.Poller;
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
                    return true;
                case R.id.chat:
                    addFragment(R.id.chat_fragment,
                            new ChatFragment());
                    return true;
                case R.id.stats:
//                    mTextMessage.setText(R.string.title_notifications);
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


        mNav = findViewById(R.id.navigation);
        mMenuBtn = findViewById(R.id.menu_btn);
        mClaimRoute = findViewById(R.id.claim_route_btn);
        mDrawCards = findViewById(R.id.draw_card_btn);
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


        // TODO: Move to Presenter eventually
        Poller.get().stop();
    }

    public void testClaimRoute() {
        claimedRouteImg.setVisibility(View.VISIBLE);
        gamePlayPresenter.testClaimRoute();
    }
    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment)
                .disallowAddToBackStack()
                .commit();
    }

}
