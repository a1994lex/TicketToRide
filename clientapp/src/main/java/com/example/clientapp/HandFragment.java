package com.example.clientapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ImageButton mExit;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


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

        return view;
    }


    private void finish(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
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

}
