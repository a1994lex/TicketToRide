package com.example.clientapp.dialogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.clientapp.R;

import presenters.JoinGamePresenter;
import com.groupryan.shared.models.Color;

import java.io.IOException;

public class CreateGameDialogActivity extends AppCompatActivity {
    private EditText mGameTitle;
    private NumberPicker mNumPlayers;
    private RadioGroup mColors;
    private Button mContinue;
    private TextView mError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        mGameTitle = findViewById(R.id.game_title);
        mNumPlayers = findViewById(R.id.numberPicker);
        mColors = findViewById(R.id.radio_color_group);
        mContinue = findViewById(R.id.button);
        mError = findViewById(R.id.errorText);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorChoice = mColors.getCheckedRadioButtonId();
                try{
                    Color color = getColorfromId(colorChoice);
                    JoinGamePresenter.createGame();
                }
                catch (IOException exception){
                    mError.setText("Please choose a color");
                }
            }
        });
    }

    private Color getColorfromId(int colorChoice) throws IOException{
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
