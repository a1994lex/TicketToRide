package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import presenters.GamePlayPresenter;
import presenters.HandPresenter;
import presenters.IHandPresenter;
import states.game.ClaimRouteState;


public class HandFragment extends Fragment implements IHandView {

    private TextView mRedTrainCardCount;
    private TextView mOrangeTrainCardCount;
    private TextView mYellowTrainCardCount;
    private TextView mGreenTrainCardCount;
    private TextView mBlueTrainCardCount;
    private TextView mPinkTrainCardCount;
    private TextView mWhiteTrainCardCount;
    private TextView mBlackTrainCardCount;
    private TextView mLocoTrainCardCount;
    private ImageView mRedTrainCard;
    private ImageView mOrangeTrainCard;
    private ImageView mYellowTrainCard;
    private ImageView mGreenTrainCard;
    private ImageView mBlueTrainCard;
    private ImageView mPinkTrainCard;
    private ImageView mWhiteTrainCard;
    private ImageView mBlackTrainCard;
    private ImageView mLocoTrainCard;
    private ImageButton mExit;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GameActivity gameActivity;
    private IHandPresenter presenter;
    private int routeLength;    // move these somewhere else eventually because they are only used
                                // when claiming a route
    private String routeColor = "";  // move these somewhere else eventually because they are only used
                                // when claiming a route
    private Map<String, Integer> pickedCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hand, container, false);
        presenter = HandPresenter.getInstance();
        presenter.setView(this, view);



        // Get myPlayer from RootClientModel
        Player myPlayer = RootClientModel.getCurrentGame().getMyPlayer();
        Map<String, Integer> trainCardsMap = myPlayer.getTrainCardsMap();
        pickedCards = new HashMap<>();
        routeLength = -1;
        mRedTrainCardCount = view.findViewById(R.id.red_count);
        mRedTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.RED)));
        mOrangeTrainCardCount = view.findViewById(R.id.orange_count);
        mOrangeTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.ORANGE)));
        mYellowTrainCardCount = view.findViewById(R.id.yellow_count);
        mYellowTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.YELLOW)));
        mGreenTrainCardCount = view.findViewById(R.id.green_count);
        mGreenTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.GREEN)));
        mBlueTrainCardCount = view.findViewById(R.id.blue_count);
        mBlueTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.BLUE)));
        mPinkTrainCardCount = view.findViewById(R.id.pink_count);
        mPinkTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.PINK)));
        mWhiteTrainCardCount = view.findViewById(R.id.white_count);
        mWhiteTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.WHITE)));
        mBlackTrainCardCount = view.findViewById(R.id.black_count);
        mBlackTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.BLACK)));
        mLocoTrainCardCount = view.findViewById(R.id.loco_count);
        mLocoTrainCardCount.setText(Integer.toString(trainCardsMap.get(utils.LOCOMOTIVE)));

        if (GamePlayPresenter.getInstance().getState().getClass().equals(ClaimRouteState.class)) {
            RootClientModel.getCurrentGame().setShowRoutes(false);
            getActivity().setFinishOnTouchOutside(false);
        }

        mRedTrainCard = view.findViewById(R.id.red_train_card);
        mRedTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mRedTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.RED)) {
                            mRedTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mRedTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.RED;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mOrangeTrainCard = view.findViewById(R.id.orange_train_card);
        mOrangeTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mOrangeTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.ORANGE)) {
                            mOrangeTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mOrangeTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.ORANGE;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mYellowTrainCard = view.findViewById(R.id.yellow_train_card);
        mYellowTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mYellowTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.YELLOW)) {
                            mYellowTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mYellowTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.YELLOW;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mGreenTrainCard = view.findViewById(R.id.green_train_card);
        mGreenTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mGreenTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.GREEN)) {
                            mGreenTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mGreenTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.GREEN;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mBlueTrainCard = view.findViewById(R.id.blue_train_card);
        mBlueTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mBlueTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.BLUE)) {
                            mBlueTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mBlueTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.BLUE;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mPinkTrainCard = view.findViewById(R.id.pink_train_card);
        mPinkTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mPinkTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.PINK)) {
                            mPinkTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mPinkTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.PINK;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mWhiteTrainCard = view.findViewById(R.id.white_train_card);
        mWhiteTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mWhiteTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.WHITE)) {
                            mWhiteTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mWhiteTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.WHITE;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mBlackTrainCard = view.findViewById(R.id.black_train_card);
        mBlackTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mBlackTrainCardCount.getText().toString()) > 0) {
                        if (routeColor.isEmpty() || routeColor.equals(utils.BLACK)) {
                            mBlackTrainCardCount.setText(GamePlayPresenter.getInstance()
                                    .decrementTextViewCount(mBlackTrainCardCount.getText().toString()));
                            routeLength--;
                            routeColor = utils.BLACK;
                            pickedCards =
                                    GamePlayPresenter.getInstance()
                                            .mapColorToCount(routeColor, pickedCards);
                            if (routeLength > 0) {
                                outputMessage("Pick " + routeLength + " more " +
                                        routeColor + " cards");
                            } else {
                                startDiscardCardTask();
                            }
                        }
                    }
                }
            }
        });
        mLocoTrainCard = view.findViewById(R.id.loco_train_card);
        mLocoTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.claimingRoute()) {
                    if (Integer.parseInt(mLocoTrainCardCount.getText().toString()) > 0) {
                        mLocoTrainCardCount.setText(GamePlayPresenter.getInstance()
                                .decrementTextViewCount(mLocoTrainCardCount.getText().toString()));
                        routeLength--;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(utils.LOCOMOTIVE, pickedCards);
                        if (routeLength > 0) {
                            outputMessage("Pick " + routeLength + " more " +
                                    routeColor + " cards");
                        } else {
                            startDiscardCardTask();
                        }
                    }
                }
            }
        });

        mExit=view.findViewById(R.id.exit);
        mExit.setZ(5);
        mExit.setImageResource(R.drawable.ic_home_black_24dp);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GamePlayPresenter.getInstance().setShowRoutes(true);
                finish();
            }
        });

        mRecyclerView = view.findViewById(R.id.destCard_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new DestCardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (presenter.claimingRoute()) {
            outputMessage("Pick the cards you want to discard to claim route.");
            routeColor = getActivity().getIntent().getStringExtra(utils.ROUTE_COLOR);
            routeLength = getActivity().getIntent().getIntExtra(utils.ROUTE_LENGTH, -1);
            listRouteRequirements(view);
        }

        return view;
    }

    public void startDiscardCardTask() {
//            routeLength = 0;
            List<Integer> discardingTrainCards = GamePlayPresenter.getInstance()
                    .getDiscardingTrainCards(pickedCards);
            int routeId = getActivity().getIntent().getIntExtra(utils.ROUTE_ID, -1);
            GamePlayPresenter.getInstance().discardTrainCards(routeId, discardingTrainCards);
            finish();
    }

    public void listRouteRequirements(View view) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        TextView colorReminder = view.findViewById(R.id.color_reminder);
        TextView lengthReminder = view.findViewById(R.id.length_reminder);
        if (!routeColor.isEmpty()) {
            colorReminder.setText("Choose color: " + routeColor);
        } else {
            colorReminder.setText("Choose any color, but\nall cards must be same color");
        }
        lengthReminder.setText("Route length: " + routeLength);
        colorReminder.setVisibility(View.VISIBLE);
        lengthReminder.setVisibility(View.VISIBLE);
    }

    public void outputMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

  @Override
    public void endGame(){
        Intent intent = new Intent(this.getContext(), GameOverActivity.class);
        startActivity(intent);
    }

    private void finish(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//        GamePlayPresenter.getInstance().redrawRoutes();
    }

    private class DestCardHolder extends RecyclerView.ViewHolder {
        private TextView mDestCardRoute;
        private TextView mDestCardPoints;

        public DestCardHolder(View itemView) {
            super(itemView);
            mDestCardRoute = itemView.findViewById(R.id.DestCardRoute);
            mDestCardPoints = itemView.findViewById(R.id.DestCardPoints);
        }

        public void bindDestCard(String route, String value) {
            mDestCardRoute.setText(route);
            mDestCardPoints.setText(value);
        }
    }

    private class DestCardAdapter extends RecyclerView.Adapter<DestCardHolder> {
        List<DestCard> destCards = RootClientModel.getCurrentGame().getMyPlayer().getDestCards();

        @Override
        public DestCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HandFragment.this.getActivity());
            View view = layoutInflater.inflate(R.layout.item_destcard_in_hand, parent, false);
            return new DestCardHolder(view);
        }

        @Override
        public void onBindViewHolder(DestCardHolder holder, int position) {
            String route = destCards.get(position).getRoute();
            String value = Integer.toString(destCards.get(position).getValue());
            holder.bindDestCard(route, value);
        }

        @Override
        public int getItemCount() {
            return destCards.size();
        }
    }

    public void cardsDiscarded() {
        mAdapter.notifyDataSetChanged();
    }
}
