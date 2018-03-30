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

import com.example.clientapp.dialogs.ClaimRouteDialogActivity;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.EndGameStat;
import com.groupryan.shared.models.RouteSegment;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.clientapp.dialogs.DiscardDestCardDialogActivity;

import presenters.GamePlayPresenter;
import presenters.IGamePlayPresenter;
import states.game.ClaimRouteState;

public class GameActivity extends FragmentActivity implements IGameView {

    // views for finding the points of the route segments
//    private ImageView mapImage;
//    private EditText routeName;
//    private Button nameEntry;

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
                GamePlayPresenter.getInstance().clickClaimRoute(); // the states will do their thing, then th
                //testEndGameStat();
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

//            for (LineView lineView : lineViews) {
//                lineView.setVisibility(View.INVISIBLE);
//            }
//            addFragment(R.id.bank_fragment,
//                    new BankFragment(), utils.BANK);
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
        if (GamePlayPresenter.getInstance().getState().getClass().equals(ClaimRouteState.class)) {
            addFragment(R.id.hand_fragment, new HandFragment(), utils.HAND);
            clearLines();
        }

        // views for finding the points of the route segments
//        mapImage = findViewById(R.id.map_button);
//        routeName = findViewById(R.id.route_name_entry);
//        nameEntry = findViewById(R.id.name_entry_button);
//        Logger logger = Logger.getLogger("MyLog");
//
//        mapImage.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                    logger.severe("x value: " + String.valueOf(event.getX()) +
//                            " y value: " + String.valueOf(event.getY()) + "\n");
//                }
//                return true;
//            }
//        });
    }
    @Override
    public void showBankModal(){
        for (LineView lineView : lineViews) {
            lineView.setVisibility(View.INVISIBLE);
        }
        addFragment(R.id.bank_fragment,
                new BankFragment(), utils.BANK);
    }

    @Override
    public void showClaimRouteModal(){
        for (LineView lineView : lineViews) {
            lineView.setVisibility(View.INVISIBLE);
        }
        Intent intent = new Intent(this, ClaimRouteDialogActivity.class);
        startActivity(intent);
        // create a dialog ClaimRouteActivity where client can choose their route they would like to buy
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GamePlayPresenter.getInstance().getState().getClass().equals(ClaimRouteState.class)) {
            gamePlayPresenter.setShowRoutes(false);
        }
        else {
            gamePlayPresenter.setShowRoutes(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearLines();
        gamePlayPresenter.setShowRoutes(false);
    }

    @Override
    public void cardsDiscarded() {
        HandFragment fragment = (HandFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hand_fragment);
        if (fragment != null) {
            fragment.cardsDiscarded();
        }
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
            drawSegment(routeSegment, playerColor, constraintLayout, constraintLayoutParams);
        }
    }

    public void drawSegment(RouteSegment routeSegment, String playerColor,
                            ConstraintLayout constraintLayout,
                            ConstraintLayout.LayoutParams constraintLayoutParams) {
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
        constraintSet.connect(lineView.getId(), ConstraintSet.LEFT, constraintLayout.getId(),
                ConstraintSet.RIGHT, 0);
        constraintSet.connect(lineView.getId(), ConstraintSet.LEFT, constraintLayout.getId(),
                ConstraintSet.LEFT, 0);
        constraintSet.applyTo(constraintLayout);
        lineViews.add(lineView);
    }

    public void clearLines(){
        lineViews.clear();
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String FRAGMENT_ID) {
//        if (containerViewId == R.id.hand_fragment &&
//                GamePlayPresenter.getInstance().getState().getClass().equals(ClaimRouteState.class)) {
//            lineViews.clear();
//        }
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
                gamePlayPresenter.setShowRoutes(true);
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
