package com.example.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.groupryan.shared.models.EndGameStat;

import java.util.List;

import presenters.GameOverPresenter;

public class GameOverActivity extends AppCompatActivity implements  IGameOverView{

    TextView winnerText;
    Button backToLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over2);
        GameOverPresenter.getInstance().setView(this);

        winnerText = findViewById(R.id.winner_text_view);
        backToLobby = findViewById(R.id.back_to_lobby);

        backToLobby.setOnClickListener(view -> goToLobby());

        init();
    }

    public void goToLobby(){
        Intent intent = new Intent(this, JoinGameActivity.class);
        startActivity(intent);
    }
    public void init(){

        winnerText.setText(GameOverPresenter.getInstance().getWinnerString());
        List<EndGameStat> endGameStats = GameOverPresenter.getInstance().getEndGameStats();

        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                                                TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(15, 15, 15, 15);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.endgame_stats_table);
        TableRow header = new TableRow(this);

        header.setLayoutParams(tableRowParams);
        TextView tv0 = new TextView(this);
        tv0.setText("    Player    ");
        tv0.setTextSize(18);
        header.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("    Total     ");
        tv1.setTextSize(18);
        header.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("    Claimed Route Points    ");
        tv2.setTextSize(18);
        header.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("    Longest Path Points    ");
        tv3.setTextSize(18);
        header.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("    Reached Destination Points   ");
        tv4.setTextSize(18);
        header.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText("    Unreached Destination Points    ");
        tv5.setTextSize(18);
        header.addView(tv5);
        tableLayout.addView(header);


        for (EndGameStat endGameStat : endGameStats) {
            TableRow row = new TableRow(this);
            TextView player = new TextView(this);
            player.setText(endGameStat.getUsername());
            player.setGravity(Gravity.CENTER);
            player.setTextSize(18);
            row.addView(player);
            TextView points = new TextView(this);
            points.setText(Integer.toString(endGameStat.getTotalPoints()));
            points.setGravity(Gravity.CENTER);
            points.setTextSize(18);
            row.addView(points);
            TextView claimed = new TextView(this);
            claimed.setText(Integer.toString(endGameStat.getClaimedRoutePoints()));
            claimed.setGravity(Gravity.CENTER);
            claimed.setTextSize(18);
            row.addView(claimed);
            TextView longest = new TextView(this);
            longest.setText(Integer.toString(endGameStat.getLongestPath()));
            longest.setGravity(Gravity.CENTER);
            longest.setTextSize(18);
            row.addView(longest);
            TextView reached = new TextView(this);
            reached.setText(Integer.toString(endGameStat.getReachedDestPoints()));
            reached.setGravity(Gravity.CENTER);
            reached.setTextSize(18);
            row.addView(reached );
            TextView unreached = new TextView(this);
            unreached.setText(Integer.toString(endGameStat.getUnreachedDestNegativePoints()));
            unreached.setGravity(Gravity.CENTER);
            unreached.setTextSize(18);
            row.addView(unreached);
            row.setLayoutParams(tableRowParams);
            tableLayout.addView(row);

        }

    }
}
