package com.example.clientapp.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.clientapp.R;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;

import presenters.GamePlayPresenter;

public class DiscardDestCardDialogActivity extends Activity {

    private TextView mDestCardTitle1;
    private TextView mDestCardTitle2;
    private TextView mDestCardTitle3;
    private TextView mDestCardPoints1;
    private TextView mDestCardPoints2;
    private TextView mDestCardPoints3;
    private CheckBox mDestCardCheckBox1;
    private CheckBox mDestCardCheckBox2;
    private CheckBox mDestCardCheckBox3;
    private TextView mErrorMsg;
    private Button mContinueButton;
    private Boolean original=RootClientModel.getCurrentGame().getOriginal();

    private GamePlayPresenter gamePlayPresenter = GamePlayPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // hotfix
        this.gamePlayPresenter.setDiscardActivity(this);
        // -----
        setContentView(R.layout.dialog_discard_card);


        Intent i = getIntent();
        final Boolean isGameSetup = i.getBooleanExtra(utils.GAME_SETUP, true);

        setFinishOnTouchOutside(false);
        // Get myPlayer from RootClientModel
        Player myPlayer = RootClientModel.getCurrentGame().getMyPlayer();
        List<DestCard> destCards = myPlayer.getDestCards();
        final DestCard destCard1 = destCards.get(destCards.size() - 3);
        final DestCard destCard2 = destCards.get(destCards.size() - 2);
        final DestCard destCard3 = destCards.get(destCards.size() - 1);

        mDestCardTitle1 = findViewById(R.id.destCard1_title);
        mDestCardTitle1.setText(destCard1.getRoute());
        mDestCardTitle2 = findViewById(R.id.destCard2_title);
        mDestCardTitle2.setText(destCard2.getRoute());
        mDestCardTitle3 = findViewById(R.id.destCard3_title);
        mDestCardTitle3.setText(destCard3.getRoute());
        mDestCardPoints1 = findViewById(R.id.destCard1_points);
        mDestCardPoints1.setText(Integer.toString(destCard1.getValue()));
        mDestCardPoints2 = findViewById(R.id.destCard2_points);
        mDestCardPoints2.setText(Integer.toString(destCard2.getValue()));
        mDestCardPoints3 = findViewById(R.id.destCard3_points);
        mDestCardPoints3.setText(Integer.toString(destCard3.getValue()));
        mDestCardCheckBox1 = findViewById(R.id.destCard1_checkbox);
        mDestCardCheckBox2 = findViewById(R.id.destCard2_checkbox);
        mDestCardCheckBox3 = findViewById(R.id.destCard3_checkbox);
        mErrorMsg = findViewById(R.id.discardErrorText);
        mContinueButton = findViewById(R.id.selectCardsButton);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> cardIDs = new ArrayList<>();
                if (!mDestCardCheckBox1.isChecked()) {
                    cardIDs.add(destCard1.getCardId());
                }
                if (!mDestCardCheckBox2.isChecked()) {
                    cardIDs.add(destCard2.getCardId());
                }
                if (!mDestCardCheckBox3.isChecked()) {
                    cardIDs.add(destCard3.getCardId());
                }
                if (original) {
                    if (cardIDs.size() <= 1) {
                        gamePlayPresenter.discardDestinationCard(cardIDs);
                    } else {
                        mErrorMsg.setText("Choose 2 or 3 cards to keep.");
                    }
                } else {
                    if (cardIDs.size() <= 2) {
                        gamePlayPresenter.discardDestinationCard(cardIDs);
                    } else {
                        mErrorMsg.setText("Choose 1, 2, or 3 cards to keep.");
                    }
                }
            }
        });
    }
}