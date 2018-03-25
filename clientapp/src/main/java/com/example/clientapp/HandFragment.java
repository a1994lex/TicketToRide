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
    private int routeLength;    // move these somewhere else eventually because they are only used
                                // when claiming a route
    private String routeColor;  // move these somewhere else eventually because they are only used
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
        HandPresenter.getInstance().setView(this, view);


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

        mRedTrainCard = view.findViewById(R.id.red_train_card);
        mRedTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.RED) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.RED;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mOrangeTrainCard = view.findViewById(R.id.orange_train_card);
        mOrangeTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.ORANGE) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.ORANGE;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mYellowTrainCard = view.findViewById(R.id.yellow_train_card);
        mYellowTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.YELLOW) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.YELLOW;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mGreenTrainCard = view.findViewById(R.id.green_train_card);
        mGreenTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.GREEN) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.GREEN;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mBlueTrainCard = view.findViewById(R.id.blue_train_card);
        mBlueTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.BLUE) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.BLUE;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mPinkTrainCard = view.findViewById(R.id.pink_train_card);
        mPinkTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.PINK) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.PINK;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mWhiteTrainCard = view.findViewById(R.id.white_train_card);
        mWhiteTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.WHITE) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.WHITE;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mBlackTrainCard = view.findViewById(R.id.black_train_card);
        mBlackTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    if (routeColor.equals(utils.BLACK) || routeColor.isEmpty()) {
                        routeLength--;
                        routeColor = utils.BLACK;
                        pickedCards =
                                GamePlayPresenter.getInstance()
                                        .mapColorToCount(routeColor, pickedCards);
                        outputMessage("Pick " + routeLength + " more " +
                                routeColor + " cards");
                    }
                }
            }
        });
        mLocoTrainCard = view.findViewById(R.id.loco_train_card);
        mLocoTrainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                        getStringExtra(utils.CLAIMING_ROUTE))) {
                    routeLength--;
                    pickedCards =
                            GamePlayPresenter.getInstance()
                                    .mapColorToCount(utils.LOCOMOTIVE, pickedCards);
                    outputMessage("Pick " + routeLength + " more " +
                            routeColor + " cards");
                }
            }
        });

        if (routeLength == 0) {
            routeLength = -1;
            List<TrainCard> discardingTrainCards = GamePlayPresenter.getInstance()
                    .getDiscardingTrainCards(pickedCards);
            int routeId = getActivity().getIntent().getIntExtra(utils.ROUTE_ID, -1);
            GamePlayPresenter.getInstance().discardTrainCards(routeId, discardingTrainCards);
            finish();
        }

        mExit=view.findViewById(R.id.exit);
        mExit.setZ(5);
        mExit.setImageResource(R.drawable.ic_home_black_24dp);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = view.findViewById(R.id.destCard_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new HandFragment.DestCardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (utils.CLAIMING_ROUTE.equals(getActivity().getIntent().
                getStringExtra(utils.CLAIMING_ROUTE))) {
            outputMessage("Pick the cards you want to discard to claim route.");
            routeColor = getActivity().getIntent().getStringExtra(utils.ROUTE_COLOR);
            routeLength = getActivity().getIntent().getIntExtra(utils.ROUTE_LENGTH, -1);
            listRouteRequirements();
        }

        return view;
    }

    public void listRouteRequirements() {
        TextView colorReminder = getView().findViewById(R.id.color_reminder);
        TextView lengthReminder = getView().findViewById(R.id.length_reminder);
        if (!routeColor.isEmpty()) {
            colorReminder.setText("Choose color: " + routeColor);
        } else {
            colorReminder.setText("Choose any color, but all cards must be same color");
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

    private class DestCardAdapter extends RecyclerView.Adapter<HandFragment.DestCardHolder> {
        List<DestCard> destCards = RootClientModel.getCurrentGame().getMyPlayer().getDestCards();

        @Override
        public HandFragment.DestCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HandFragment.this.getActivity());
            View view = layoutInflater.inflate(R.layout.item_destcard_in_hand, parent, false);
            return new DestCardHolder(view);
        }

        @Override
        public void onBindViewHolder(HandFragment.DestCardHolder holder, int position) {
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
