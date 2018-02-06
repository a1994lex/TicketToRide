package com.example.clientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupryan.client.ui.IRegisterView;

public class RegisterActivity extends AppCompatActivity implements IRegisterView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
    }
}
