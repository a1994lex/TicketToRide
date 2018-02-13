package com.example.clientapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

import async.OnJoinOrCreate;
import presenters.JoinGamePresenter;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.utils;

import java.io.IOException;

import static com.groupryan.shared.utils.BLACK;
import static com.groupryan.shared.utils.BLUE;
import static com.groupryan.shared.utils.GREEN;
import static com.groupryan.shared.utils.RED;
import static com.groupryan.shared.utils.YELLOW;

public class CreateGameDialogActivity extends Activity implements OnJoinOrCreate, IJoinGameView{
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
    public void onGameDelete(){//int position) {
//        Not necessary for this activity

    }

    @Override
    public void error(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onJoinOrCreate(String errormsg) {
        if (errormsg != null){
            this.error(errormsg);
        }
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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_game);
        JoinGamePresenter.setView(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        JoinGamePresenter.setCreateDialogActivity(this);

//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
                    String color = getColorFromId(colorChoice);
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



    private String getColorFromId(int colorChoice) throws IOException{
        String color;
        switch (colorChoice){
            case R.id.radio_green:
                color = GREEN;
                break;
            case R.id.radio_blue:
                color = BLUE;
                break;
            case R.id.radio_black:
                color = BLACK;
                break;
            case R.id.radio_yellow:
                color = YELLOW;
                break;
            case R.id.radio_red:
                color = RED;
                break;
            default:
                throw new IOException();
        }
        return color;

    }
}
