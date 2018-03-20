package com.example.clientapp;

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

import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
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

public class GameActivity extends FragmentActivity implements IGameView, Observer {

    private ClientGame game = RootClientModel.getCurrentGame();
    private BottomNavigationView mNav;
    private FloatingActionButton mMenuBtn;
    private FloatingActionButton mDrawCards;
    private FloatingActionButton mClaimRoute;
    private FloatingActionButton mHandButton;
    private List<LineView> lineViews = new ArrayList<>();
    private int mapUpdatePhase;
    private GamePlayPresenter gamePlayPresenter = GamePlayPresenter.getInstance();

    // views for finding the points of the route segments
    private ImageView mapImage;
   // private Button newRouteButton;
    private EditText routeName;
    private Button nameEntry;
    //---------------------------------------------------

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // hotfix
        this.gamePlayPresenter.setGameActivity(this);
        // ------
        mapUpdatePhase = 0;
        mNav = findViewById(R.id.navigation);
        mNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMenuBtn = findViewById(R.id.menu_btn);

        // views for finding the points of the route segments
        mapImage = findViewById(R.id.map_button);
        //newRouteButton = findViewById(R.id.new_route);
        routeName = findViewById(R.id.route_name_entry);
        nameEntry = findViewById(R.id.name_entry_button);
        Logger logger = Logger.getLogger("MyLog");

//        newRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logger.severe("New route\n");
////                System.out.println("New route\n");
//            }
//        });

        game.addObserver(this);

        mapImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                        // the following statement is used to log any messages
                    logger.severe("x value: " + String.valueOf(event.getX()) +
                            " y value: " + String.valueOf(event.getY()) + "\n");
//                    System.out.println("x value: " + String.valueOf(event.getX()) +
//                            " y value: " + String.valueOf(event.getY()) + "\n");
                }
                return true;
            }
        });

        nameEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(routeName.getText().toString());
                logger.severe(routeName.getText().toString());
            }
        });

        //--------------------------------------------------------------

        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.setVisibility(View.VISIBLE);
                mClaimRoute.setVisibility(View.INVISIBLE);
                mDrawCards.setVisibility(View.INVISIBLE);
                mMenuBtn.setVisibility(View.INVISIBLE);
                mHandButton.setVisibility(View.INVISIBLE);
            }
        });
        mClaimRoute = findViewById(R.id.claim_route_btn);
        mClaimRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRoot();
            }
        });
        mDrawCards = findViewById(R.id.draw_card_btn);
        mDrawCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removePrevFrag(utils.BANK);
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
            }
        });
        mHandButton = findViewById(R.id.hand_btn);
        mHandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (LineView lineView : lineViews) {
                    lineView.setVisibility(View.INVISIBLE);
                }
                addFragment(R.id.hand_fragment,
                        new HandFragment(), utils.HAND);
            }
        });

        gamePlayPresenter.stopLobbyPolling();
        gamePlayPresenter.drawDestCards();
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
                gamePlayPresenter.claimRouteTest(utils.CURRENT_PLAYER);
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
                gamePlayPresenter.addChatMessage("jimbob", "hewwo! (jimbob)");
                mapUpdatePhase++;
                break;
            case 6:
                Toast.makeText(this, "Testing...removing train card from current player",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeTrainCards();
                mapUpdatePhase++;
                break;
            case 7:
                Toast.makeText(this, "Testing...removing destination card from current player",
                        Toast.LENGTH_SHORT).show();
                gamePlayPresenter.changeDestCards();
                mapUpdatePhase++;
                break;
        }
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
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.DRAW_THREE_CARDS)){
                lineViews.clear();
                Intent intent = new Intent(this, DiscardDestCardDialogActivity.class);
                //intent.putExtra(utils.GAME_SETUP, true);
               // startActivity(intent);
            }
        }
    }
}
