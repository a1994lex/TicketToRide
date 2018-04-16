package com.example.clientapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;

import java.util.ArrayList;
import java.util.List;

import presenters.GameStatPresenter;

public class GameStatFragment extends Fragment implements IGameStatView {


    private RecyclerView statRecycler;
    private RecyclerView.Adapter statAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_stat, container, false);
        GameStatPresenter.getInstance().setView(this, view);

        statRecycler = view.findViewById(R.id.stats_recycler_view);
        Context context = view.getContext();
        statRecycler.setLayoutManager(new LinearLayoutManager(context));
        statAdapter = new StatAdapter();
        statRecycler.setAdapter(statAdapter);

        return view;
    }

    @Override
    public void updateStat(){
        statRecycler.setAdapter(statAdapter);
        //statAdapter.notifyDataSetChanged();
    }

    private class StatHolder extends RecyclerView.ViewHolder {
        private TextView turn;
        private TextView player_name;
        private TextView points;
        private TextView trains;
        private TextView trainCards;
        private TextView destCards;
        int currentTurn;

        public StatHolder(View itemView) {
            super(itemView);
            turn = itemView.findViewById(R.id.turn_order);
            player_name = itemView.findViewById(R.id.player);
            points = itemView.findViewById(R.id.points);
            trains = itemView.findViewById(R.id.trains);
            trainCards = itemView.findViewById(R.id.train_cards);
            destCards = itemView.findViewById(R.id.dest_Cards);
        }

        public void bindStat(Stat stat){
            //do turn stuff
            currentTurn = RootClientModel.getCurrentGame().getCurrentTurn();
            if(currentTurn != stat.getTurn()){
                turn.setVisibility(View.INVISIBLE);
            }
            player_name.setText(stat.getUsername());
            points.setText(Integer.toString(stat.getPoints()));
            trains.setText(Integer.toString(stat.getTrains()));
            trainCards.setText(Integer.toString(stat.getTrainCards()));
            destCards.setText(Integer.toString(stat.getDestinationCards()));

        }
    }

    private class StatAdapter extends RecyclerView.Adapter<StatHolder> {
        private List<Stat> stats = createList();

        public List<Stat> createList(){
            return new ArrayList<>(RootClientModel.getCurrentGame().getStats().values());
        }

        @Override
        public StatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_stat, parent, false);
            return new StatHolder(view);
        }

        @Override
        public void onBindViewHolder(StatHolder holder, int position) {
            holder.bindStat(stats.get(position));

        }

        @Override
        public int getItemCount() {
            return stats.size();
        }


    }
}



/*        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.stats_table);


        if(tableLayout != null){
            if ( tableLayout.getChildCount() > 1){
                tableLayout.removeViews(0, tableLayout.getChildCount());
            }
        }





        HashMap<String, Stat> theStats = RootClientModel.getCurrentGame().getStats();

        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(15, 15, 15, 15);




        TableRow header = null;
        header = new TableRow(getActivity());
        header.setLayoutParams(tableRowParams);
        TextView tv0 = new TextView(getActivity());
        tv0.setText("    Turn Order    ");
        tv0.setTextSize(18);
        header.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText("    Player    ");
        tv1.setTextSize(18);
        header.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText("    Points    ");
        tv2.setTextSize(18);
        header.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText("    Trains    ");
        tv3.setTextSize(18);
        header.addView(tv3);
        TextView tv4 = new TextView(getActivity());
        tv4.setText("    Train Cards    ");
        tv4.setTextSize(18);
        header.addView(tv4);
        TextView tv5 = new TextView(getActivity());
        tv5.setText("    Destination Cards    ");
        tv5.setTextSize(18);
        header.addView(tv5);
        tableLayout.addView(header);


        int currentTurn = RootClientModel.getCurrentGame().getCurrentTurn();
//        Map<Integer, String> turnOrderMap = RootClientModel.getCurrentGame().getTurnOrderMap();

        for (Map.Entry<String, Stat> entry : theStats.entrySet()) {
            Stat stat = entry.getValue();
            TableRow row = null;
            row = new TableRow(getActivity());
            TextView turn = new TextView(getActivity());
            if (currentTurn == stat.getTurn()) {
                turn.setText("->" + Integer.toString(stat.getTurn()));
            } else {
                turn.setText(Integer.toString(stat.getTurn()));
            }
            turn.setGravity(Gravity.CENTER);
            turn.setTextSize(18);
            row.addView(turn);
            TextView player = new TextView(getActivity());
            player.setText(entry.getKey());
            player.setGravity(Gravity.CENTER);
            player.setTextSize(18);
            row.addView(player);
            TextView points = new TextView(getActivity());
            points.setText(Integer.toString(stat.getPoints()));
            points.setGravity(Gravity.CENTER);
            points.setTextSize(18);
            row.addView(points);
            TextView trains = new TextView(getActivity());
            trains.setText(Integer.toString(stat.getTrains()));
            trains.setGravity(Gravity.CENTER);
            trains.setTextSize(18);
            row.addView(trains);
            TextView cards = new TextView(getActivity());
            cards.setText(Integer.toString(stat.getTrainCards()));
            cards.setGravity(Gravity.CENTER);
            cards.setTextSize(18);
            row.addView(cards);
            TextView destinations = new TextView(getActivity());
            destinations.setText(Integer.toString(stat.getDestinationCards()));
            destinations.setGravity(Gravity.CENTER);
            destinations.setTextSize(18);
            row.addView(destinations);
            row.setLayoutParams(tableRowParams);
            tableLayout.addView(row);



        }*/
