package com.example.clientapp.dialogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.IJoinGameView;
import com.example.clientapp.LobbyActivity;
import com.example.clientapp.R;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.utils;

import java.io.IOException;

import presenters.JoinGamePresenter;

public class JoinGameDialogActivity extends AppCompatActivity implements IJoinGameView{
    private RadioGroup mColors;
    private Button mContinue;
    private TextView mError;
    private Game mGame;

    @Override
    public void onGameAdd() {
//        This is really implemented in the JoinGameActivity

    }

    @Override
    public void onGameDelete(int position) {
//        This is really implemented in the JoinGameActivity

    }

    @Override
    public void error(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void join(String gameid) {
        Intent i = new Intent(JoinGameDialogActivity.this, LobbyActivity.class);
        i.putExtra(utils.GAME_ID_TAG, gameid);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoinGamePresenter.setView(this);
        Intent incoming = getIntent();
        String gameID = incoming.getStringExtra(utils.GAME_ID_TAG);
        for (Game g: RootClientModel.getGames()){
            if (g.getGameId().equals(gameID)){
                mGame = g;
                break;
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_join_game);
        JoinGamePresenter.setView(this);

        mColors = findViewById(R.id.radio_color_group);
        mContinue = findViewById(R.id.button);
        mError = findViewById(R.id.errorText);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorChoice = mColors.getCheckedRadioButtonId();
                try {
                    Color color = getColorFromId(colorChoice);

                    JoinGamePresenter.joinGame(mGame, color);
                } catch (IOException exception) {
                    mError.setText("Please choose a color");
                }
            }
        });
    }

    private Color getColorFromId(int colorChoice) throws IOException {
        Color color;
        switch (colorChoice) {
            case R.id.radio_green:
                color = Color.GREEN;
                break;
            case R.id.radio_blue:
                color = Color.BLUE;
                break;
            case R.id.radio_black:
                color = Color.BLACK;
                break;
            case R.id.radio_yellow:
                color = Color.YELLOW;
                break;
            case R.id.radio_red:
                color = Color.RED;
                break;
            default:
                throw new IOException();
        }
        return color;
    }
}
