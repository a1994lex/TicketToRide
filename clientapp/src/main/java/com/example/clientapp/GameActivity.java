package com.example.clientapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clientapp.dialogs.ClaimRouteDialogActivity;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.clientapp.dialogs.DiscardDestCardDialogActivity;

import presenters.GamePlayPresenter;
import presenters.IGamePlayPresenter;

public class GameActivity extends FragmentActivity implements IGameView {

    private ClientGame game = RootClientModel.getCurrentGame();
    private BottomNavigationView mNav;
    private FloatingActionButton mMenuBtn;
    private FloatingActionButton mDrawCards;
    private FloatingActionButton mClaimRoute;
    private FloatingActionButton mHandButton;
    private List<LineView> lineViews = new ArrayList<>();
    private IGamePlayPresenter gamePlayPresenter = GamePlayPresenter.getInstance();
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
                    mHandButton.setVisibility(View.VISIBLE);
            }
            return false;
        }
    };

   // @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.gamePlayPresenter.setGameView(this);

        mNav = findViewById(R.id.navigation);
        mNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMenuBtn = findViewById(R.id.menu_btn);
        mClaimRoute = findViewById(R.id.claim_route_btn);
        mDrawCards = findViewById(R.id.draw_card_btn);
        mHandButton = findViewById(R.id.hand_btn);
        gamePlayPresenter.setUpIfFirst();
        //RouteLogHelper logger = new RouteLogHelper(this);

        // SET UP LISTENERS
        mClaimRoute.setOnClickListener((View v) -> {
            @Override
            public void onClick(View v) {
                GamePlayPresenter.getInstance().clickClaimRoute(); // the states will do their thing, then th
                //testEndGameStat();
            }
        });

        mMenuBtn.setOnClickListener((View v) -> {
            mNav.setVisibility(View.VISIBLE);
            mClaimRoute.setVisibility(View.INVISIBLE);
            mDrawCards.setVisibility(View.INVISIBLE);
            mMenuBtn.setVisibility(View.INVISIBLE);
            mHandButton.setVisibility(View.INVISIBLE);
        });

      mDrawCards.setOnClickListener((View v) -> {
            //removePrevFrag(utils.BANK);
            GamePlayPresenter.getInstance().clickDrawCard();

            for (LineView lineView : lineViews) {
                lineView.setVisibility(View.INVISIBLE);
            }
            addFragment(R.id.bank_fragment,
                    new BankFragment(), utils.BANK);
           /* FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.bank_fragment,new BankFragment(),utils.BANK);
            transaction.addToBackStack(null);
            transaction.commit();*/
        });
        mHandButton.setOnClickListener((View v) -> {
            for (LineView lineView : lineViews) {
                lineView.setVisibility(View.INVISIBLE);
            }
            addFragment(R.id.hand_fragment,
                    new HandFragment(), utils.HAND);
        });
        if (utils.CLAIMING_ROUTE.equals(getIntent().getStringExtra(utils.CLAIMING_ROUTE))) {
            addFragment(R.id.hand_fragment, new HandFragment(), utils.HAND);
        }
    }
    @Override
    public void showClaimRouteModal(){
        Intent intent = new Intent(this, ClaimRouteDialogActivity.class);
        startActivity(intent);
        // create a dialog ClaimRouteActivity where client can choose their route they would like to buy
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamePlayPresenter.redrawRoutes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lineViews.clear();
    }

    public void cardsDiscarded() {
        HandFragment fragment = (HandFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hand_fragment);
        fragment.cardsDiscarded();
    }

    @Override
    public void goToDrawDestActivity() {
        this.clearLines();
        Intent intent = new Intent(this, DiscardDestCardDialogActivity.class);
        intent.putExtra(utils.GAME_SETUP, true);
        this.startActivity(intent);
    }

    @Override
    public void drawRoute(String playerColor, HashSet<RouteSegment> routeSegments) {
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
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
            lineViews.add(lineView);
        }
    }

    public void clearLines(){
        lineViews.clear();
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
        for (LineView lineView : lineViews) {
            lineView.setVisibility(View.INVISIBLE);
        }
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
                gamePlayPresenter.redrawRoutes();
        }
        for (String fragmentId : removeFragIds) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentId);
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void spendTrainCards() {
        addFragment(R.id.hand_fragment,
                new HandFragment(), utils.HAND);
    }

    public void endGame(){
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }

    public void testEndGameStat(){
        List<EndGameStat> endGameStats = new ArrayList<>();
        String winner = "claire";
        RootClientModel.getCurrentGame().setWinner(winner);
        EndGameStat egs1 = new EndGameStat("claire", 100, 20, 10, 80, 0);
        EndGameStat egs2 = new EndGameStat("haley", 200, 100, 0, 100, 0);
        EndGameStat egs3 = new EndGameStat("grace", 60, 0, 100, 0 , 40);
        endGameStats.add(egs1);
        endGameStats.add(egs2);
        endGameStats.add(egs3);
        RootClientModel.getCurrentGame().setEndGameStats(endGameStats);
        endGame();
    }
}
