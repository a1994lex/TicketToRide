package com.example.clientapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;

import java.util.HashMap;
import java.util.Map;

import presenters.GameStatPresenter;

public class GameStatFragment extends Fragment implements IGameStatView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_stat, container, false);
        init(view);
        GameStatPresenter.getInstance().setView(this, view);
        return view;
    }

    public void init(View view) {
        HashMap<String, Stat> theStats = RootClientModel.getCurrentGame().getStats();
        //HashMap<String, Stat> theStats = new HashMap<>();
        //Stat stats= new Stat("claire", 3, 45, 4, 3);
        //theStats.put("claireasasdfasd", stats);


        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(15, 15, 15, 15);


        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.stats_table);
        TableRow header = new TableRow(getActivity());
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
        Map<Integer, String> turnOrderMap = RootClientModel.getCurrentGame().getTurnOrderMap();

        for (Map.Entry<String, Stat> entry : theStats.entrySet()) {
            Stat stat = entry.getValue();
            TableRow row = new TableRow(getActivity());
            TextView turn = new TextView(getActivity());
            if (turnOrderMap.get(currentTurn).equals(entry.getKey())) {
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

        }

    }
}
