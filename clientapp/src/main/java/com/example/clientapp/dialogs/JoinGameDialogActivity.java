package com.example.clientapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.IJoinGameView;
import com.example.clientapp.LobbyActivity;
import com.example.clientapp.R;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.utils;

import java.io.IOException;
import java.util.Map;

import async.OnJoinOrCreate;
import presenters.JoinGamePresenter;

public class JoinGameDialogActivity extends Activity implements IJoinGameView, OnJoinOrCreate{
    private RadioGroup mColors;
    private Button mContinue;
    private TextView mError;
    private Game mGame;
    RadioButton radioGreen;
    RadioButton radioYellow;
    RadioButton radioRed;
    RadioButton radioBlue;
    RadioButton radioBlack;


    @Override
    public void onGameAdd() {
//        This is really implemented in the JoinGameActivity

    }

    @Override
    public void onGameDelete(){//int position) {
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
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        JoinGamePresenter.setView(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        JoinGamePresenter.setJoinDialogActivity(this);
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
        RadioButton radioGreen = findViewById(R.id.radio_green);
        RadioButton radioYellow = findViewById(R.id.radio_yellow);
        RadioButton radioRed = findViewById(R.id.radio_red);
        RadioButton radioBlue = findViewById(R.id.radio_blue);
        RadioButton radioBlack = findViewById(R.id.radio_black);

        try{
            enableColors();
        }catch(IOException e){
            mError.setText("Error enabling colors from game map");
        }

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

    public void enableColors() throws IOException{
        Map<String, Color> colorMap = mGame.getUsers();
        for (Color color: colorMap.values()) {
            switch (color){
                case RED:
                    radioRed.setEnabled(false);
                    break;
                case BLUE:
                    radioBlue.setEnabled(false);
                    break;
                case BLACK:
                    radioBlack.setEnabled(false);
                    break;
                case GREEN:
                    radioGreen.setEnabled(false);
                    break;
                case YELLOW:
                    radioYellow.setEnabled(false);
                    break;
                default:
                    throw new IOException();
            }
        }
    }

    @Override
    public void onJoinOrCreate(String errormsg) {
        if (errormsg != null){
            this.error(errormsg);
        }

    }
}
