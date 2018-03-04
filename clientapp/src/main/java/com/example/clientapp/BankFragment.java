package com.example.clientapp;

/**
 * Created by ryanm on 3/3/2018.
 */

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Stat;
import com.groupryan.shared.models.TrainCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import presenters.BankPresenter;
import presenters.GameStatPresenter;


public class BankFragment extends Fragment implements IBankView {
    @Override
    public void init(View view) {
        ArrayList<TrainCard> bank= RootClientModel.getCurrentGame().getBankCards();
        //** make it show up
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_bank, container, false);
        init(view);
        BankPresenter.getInstance().setView(this, view);
        return view;
    }
}
