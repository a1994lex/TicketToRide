package com.example.clientapp;

/**
 * Created by ryanm on 3/3/2018.
 */

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
    ImageButton mDestDeck;
    ImageButton mExit;
    TextView mTCardsLeft;
    TextView mDCardsLeft;

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

        }
        return R.drawable.train_icon;
    }

    @Override
    public void init(View view) {
        ArrayList<TrainCard> bank = BankPresenter.getInstance().getBank();
        mBankButton = view.findViewById(R.id.deck);
        mBankButton.setImageResource(R.drawable.top_of_deck);
        mBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                    mBankButton.setImageResource(R.drawable.outline);
                    BankPresenter.drawTrainCard(-1);
                    //async reset task and set this card to the user who clicked it

            }
        });
        mCardButtonOne = view.findViewById(R.id.card1);
        mCardButtonOne.setImageResource(colorFinder(bank.get(0).getColor()));
        mCardButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mCardButtonOne.setImageResource(R.drawable.outline);
                BankPresenter.drawTrainCard(1);
                //async reset task and set this card to the user who clicked it
            }
        });
        mCardButtonTwo = view.findViewById(R.id.card2);
        mCardButtonTwo.setImageResource(colorFinder(bank.get(1).getColor()));
        mCardButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mCardButtonTwo.setImageResource(R.drawable.outline);
                BankPresenter.drawTrainCard(2);
                //async reset task and set this card to the user who clicked it
            }
        });
        mCardButtonThree = view.findViewById(R.id.card3);
        mCardButtonThree.setImageResource(colorFinder(bank.get(2).getColor()));
        mCardButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mCardButtonThree.setImageResource(R.drawable.outline);
                BankPresenter.drawTrainCard(3);
                //async reset task and set this card to the user who clicked it
            }
        });
        mCardButtonFour = view.findViewById(R.id.card4);
        mCardButtonFour.setImageResource(colorFinder(bank.get(3).getColor()));
        mCardButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mCardButtonFour.setImageResource(R.drawable.outline);
                BankPresenter.drawTrainCard(4);
                //async reset task and set this card to the user who clicked it
            }
        });
        mCardButtonFive = view.findViewById(R.id.card5);
        mCardButtonFive.setImageResource(colorFinder(bank.get(4).getColor()));
        mCardButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mCardButtonFive.setImageResource(R.drawable.outline);
                BankPresenter.drawTrainCard(5);
                //async reset task and set this card to the user who clicked it
            }
        });
        mDestDeck = view.findViewById(R.id.destdeck);
        mDestDeck.setImageResource(R.drawable.dest_card);
        mDestDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //blank it
                mDestDeck.setImageResource(R.drawable.outline);
                BankPresenter.drawDestinationCards();
                //async reset task and set this card to the user who clicked it
            }
        });

        mExit = view.findViewById(R.id.exit);
        mExit.setImageResource(R.drawable.ic_home_black_24dp);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootClientModel.getCurrentGame().endTurn();
                finish();
            }
        });
        mTCardsLeft = view.findViewById(R.id.tcards_left);
        mTCardsLeft.setText(Integer.toString(BankPresenter.getInstance().getTDeckSize()));
        mDCardsLeft = view.findViewById(R.id.dcards_left);
        mDCardsLeft.setText(Integer.toString(BankPresenter.getInstance().getDDeckSize()));
    }

    private void finish() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        GamePlayPresenter.getInstance().redrawRoutes();
    }

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

    public void toast(){
        Toast.makeText(this.getActivity(), "No cards in Deck", Toast.LENGTH_SHORT).show();
    }

}
