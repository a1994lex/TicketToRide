package com.example.clientapp.dialogs;

import android.content.Intent;
import android.graphics.Paint;
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
import com.example.clientapp.ILobbyView;
import com.example.clientapp.LobbyActivity;
import com.example.clientapp.R;

import presenters.JoinGamePresenter;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.utils;

import java.io.IOException;

public class CreateGameDialogActivity extends AppCompatActivity implements IJoinGameView{
    private EditText mGameTitle;
    private NumberPicker mNumPlayers;
    private RadioGroup mColors;
    private Button mContinue;
    private TextView mError;

    @Override
    public void onGameAdd() {
//        Not necessary for this activity
    }

    @Override
    public void onGameDelete(int position) {
//        Not necessary for this activity

    }

    @Override
    public void error(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();


    }

    @Override
    public void join(String gameid) {
        Intent i = new Intent(CreateGameDialogActivity.this, LobbyActivity.class);
        i.putExtra(utils.GAME_ID_TAG, gameid);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_game);
        JoinGamePresenter.setView(this);
        mGameTitle = findViewById(R.id.game_title);
        mNumPlayers = findViewById(R.id.numberPicker);
        mNumPlayers.setMinValue(2);
        mNumPlayers.setMaxValue(5);
        mColors = findViewById(R.id.radio_color_group);
        mContinue = findViewById(R.id.button);
        mError = findViewById(R.id.errorText);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorChoice = mColors.getCheckedRadioButtonId();
                try{
                    Color color = getColorFromId(colorChoice);
                    String title = mGameTitle.getText().toString();
                    int numPlayers = mNumPlayers.getValue();
                    if (title.length() == 0){
                        mError.setText("Please include a title");
                    }
                    else {
                        JoinGamePresenter.createGame(title, numPlayers, color);
                    }
                }
                catch (IOException exception){
                    mError.setText("Please choose a color");
                }
            }
        });
    }

    private Color getColorFromId(int colorChoice) throws IOException{
        Color color;
        switch (colorChoice){
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
