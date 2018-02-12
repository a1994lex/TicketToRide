package com.example.clientapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupryan.client.ui.IRegisterView;
import com.groupryan.client.ui.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements IRegisterView{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.usernameEditText = (EditText) findViewById(R.id.username);
        this.passwordEditText = (EditText) findViewById(R.id.password);
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.registerButton = (Button) findViewById(R.id.register_button);
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);

        initializeListeners();
    }

    public void initializeListeners(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

//        usernameEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                usernameEditText.setText("");
//            }
//        });
//
//        passwordEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                passwordEditText.setText("");
//            }
//        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==0 || passwordEditText.getText().toString().trim().length() == 0){
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                }  else{
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==0 || usernameEditText.getText().toString().trim().length() == 0){
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                }  else{
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void login(){
        Toast.makeText(this, "logging in!", Toast.LENGTH_SHORT).show();
        RegisterPresenter regPres = new RegisterPresenter();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        regPres.login(username, password);
    }

    public void register(){
        Toast.makeText(this, "registerrrr", Toast.LENGTH_SHORT).show();
        RegisterPresenter regPres = new RegisterPresenter();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        regPres.register(username, password);
    }

}
