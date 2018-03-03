package com.example.clientapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;

import java.util.HashMap;
import java.util.Map;

public class GameStatActivity extends AppCompatActivity implements IGameStatView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stat);

        init();
    }

    public void init(){
        HashMap<String, Stat> theStats = RootClientModel.getCurrentGame().getStats();
        //HashMap<String, Stat> theStats = new HashMap<>();
        //Stat stats= new Stat("claire", 3, 45, 4, 3);
        //theStats.put("claireasasdfasd", stats);


        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(15,15,15,15);


        TableLayout tableLayout = (TableLayout) findViewById(R.id.stats_table);
        TableRow header = new TableRow(this);
        header.setLayoutParams(tableRowParams);
        TextView tv0 = new TextView(this);
        tv0.setText("    Player    ");
        tv0.setTextSize(18);
        header.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("    Points    ");
        tv1.setTextSize(18);
        header.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("    Trains    ");
        tv2.setTextSize(18);
        header.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("    Train Cards    ");
        tv3.setTextSize(18);
        header.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("    Destination Cards    ");
        tv4.setTextSize(18);
        header.addView(tv4);
        tableLayout.addView(header);


        for(Map.Entry<String, Stat> entry : theStats.entrySet()){
            Stat stat = entry.getValue();
            TableRow row = new TableRow(this);
            TextView player = new TextView(this);
            player.setText(entry.getKey());
            player.setGravity(Gravity.CENTER);
            player.setTextSize(18);
            row.addView(player);
            TextView points = new TextView(this);
            points.setText(Integer.toString(stat.getPoints()));
            points.setGravity(Gravity.CENTER);
            points.setTextSize(18);
            row.addView(points);
            TextView trains = new TextView(this);
            trains.setText(Integer.toString(stat.getTrains()));
            trains.setGravity(Gravity.CENTER);
            trains.setTextSize(18);
            row.addView(trains);
            TextView cards = new TextView(this);
            cards.setText(Integer.toString(stat.getTrainCards()));
            cards.setGravity(Gravity.CENTER);
            cards.setTextSize(18);
            row.addView(cards);
            TextView destinations = new TextView(this);
            destinations.setText(Integer.toString(stat.getDestinationCards()));
            destinations.setGravity(Gravity.CENTER);
            destinations.setTextSize(18);
            row.addView(destinations);
            row.setLayoutParams(tableRowParams);
            tableLayout.addView(row);

        }

    }
}
