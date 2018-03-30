package com.example.clientapp;

/**
 * Created by ryanm on 3/3/2018.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;

import presenters.BankPresenter;
import presenters.GamePlayPresenter;


public class BankFragment extends Fragment implements IBankView {
    ImageButton mBankButton;
    ImageButton mCardButtonOne;
    ImageButton mCardButtonTwo;
    ImageButton mCardButtonThree;
    ImageButton mCardButtonFour;
    ImageButton mCardButtonFive;
    ImageButton chosenCard;
    ImageButton mDestDeck;
    ImageButton mExit;
    TextView mTCardsLeft;
    TextView mDCardsLeft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank, container, false);
        init(view);
        BankPresenter.getInstance().setView(this, view);
        return view;
    }

    @Override
    public void showCardDrawn(){
        this.setOutlineCard();
    }



    @Override
    public void finish() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        GamePlayPresenter.getInstance().setShowRoutes(true);
        GamePlayPresenter.getInstance().getGameView().setBankClose();
    }
    @Override
    public void toastPleaseFinishDraw(){
        Toast.makeText(getActivity(), "Please draw 2 cards", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCardToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(View view) {
        ArrayList<TrainCard> bank = BankPresenter.getInstance().getBank();
        /*Bind to xml*/
        mBankButton = view.findViewById(R.id.deck);
        mCardButtonOne = view.findViewById(R.id.card1);
        mCardButtonTwo = view.findViewById(R.id.card2);
        mCardButtonThree = view.findViewById(R.id.card3);
        mCardButtonFour = view.findViewById(R.id.card4);
        mCardButtonFive = view.findViewById(R.id.card5);
        mDestDeck = view.findViewById(R.id.destdeck);
        mExit = view.findViewById(R.id.exit);
        mTCardsLeft = view.findViewById(R.id.tcards_left);
        mDCardsLeft = view.findViewById(R.id.dcards_left);
        /* Set images for ImageViews*/
        mBankButton.setImageResource(R.drawable.top_of_deck);
        mCardButtonOne.setImageResource(colorFinder(bank.get(0).getColor()));
        mCardButtonTwo.setImageResource(colorFinder(bank.get(1).getColor()));
        mCardButtonThree.setImageResource(colorFinder(bank.get(2).getColor()));
        mCardButtonFour.setImageResource(colorFinder(bank.get(3).getColor()));
        mCardButtonFive.setImageResource(colorFinder(bank.get(4).getColor()));
        mDestDeck.setImageResource(R.drawable.dest_card);
        mExit.setImageResource(R.drawable.ic_home_black_24dp);
        /* Set texts for TextViews*/
        String text = "";
        mTCardsLeft.setText(Integer.toString(BankPresenter.getInstance().getTDeckSize()));
        mDCardsLeft.setText(Integer.toString(BankPresenter.getInstance().getDDeckSize()));
        /* Set listeners for ImageButtonViews */
        mBankButton.setOnClickListener((View v) -> {
            chosenCard = mBankButton;
            BankPresenter.getInstance().clickTCard(-1);
        });
        mCardButtonOne.setOnClickListener((View v) -> {
            chosenCard = mCardButtonOne;
            BankPresenter.getInstance().clickTCard(0);
        });
        mCardButtonTwo.setOnClickListener((View v) -> {
            chosenCard = mCardButtonTwo;
            BankPresenter.getInstance().clickTCard(1);
        });
        mCardButtonThree.setOnClickListener((View v) -> {
            chosenCard = mCardButtonThree;
            BankPresenter.getInstance().clickTCard(2);
        });
        mCardButtonFour.setOnClickListener((View v) -> {
            chosenCard = mCardButtonFour;
            BankPresenter.getInstance().clickTCard(3);
        });
        mCardButtonFive.setOnClickListener((View v) -> {
            chosenCard = mCardButtonFive;
            BankPresenter.getInstance().clickTCard(4);
        });
        mDestDeck.setOnClickListener((View v) -> {
            chosenCard = mDestDeck;
            BankPresenter.getInstance().clickDCard();
        });
        mExit.setOnClickListener((View v) -> {
            BankPresenter.getInstance().exit();
        });
    }

    public int colorFinder(String color) {
        switch (color) {
            case utils.PINK:
                return R.drawable.trainpink;
            case utils.WHITE:
                return R.drawable.trainwhite;
            case utils.BLUE:
                return R.drawable.trainblue;
            case utils.YELLOW:
                return R.drawable.trainyellow;
            case utils.ORANGE:
                return R.drawable.trainorange;
            case utils.BLACK:
                return R.drawable.trainblack;
            case utils.RED:
                return R.drawable.trainred;
            case utils.GREEN:
                return R.drawable.traingreen;
            case utils.LOCOMOTIVE:
                return R.drawable.trainloco;
            default:
                return R.drawable.outline;
        }
    }

    public void setOutlineCard(){
        if (chosenCard!=null) {
            chosenCard.setImageResource(R.drawable.outline);
        }
    }

    public void endGame(){
        Intent intent = new Intent(this.getContext(), GameOverActivity.class);
        startActivity(intent);
    }

}
