package com.example.clientapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.utils;

import async.OnLogin;
import presenters.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements IRegisterView, OnLogin {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText hostEditText;
    private Button loginButton;
    private Button registerButton;
    private RegisterPresenter regPresenter = new RegisterPresenter(this, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        RootClientModel.getSingle_instance().addObserver(regPresenter);
        this.usernameEditText = (EditText) findViewById(R.id.username);
        this.passwordEditText = (EditText) findViewById(R.id.password);
        this.hostEditText = (EditText) findViewById(R.id.host);
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.registerButton = (Button) findViewById(R.id.register_button);
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);

        initializeListeners();
    }

    @Override
    public void joinGame(String id) {
        if (RootClientModel.getInstance().hasRejoinLobbyGameId()) {

            Intent intent = new Intent(this, LobbyActivity.class);
            intent.putExtra(utils.GAME_ID_TAG, id);
            startActivity(intent);
        }
    }

    public void initializeListeners() {


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

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().length() == 0 || passwordEditText.getText().toString().trim().length() == 0) {
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().length() == 0 || usernameEditText.getText().toString().trim().length() == 0) {
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void login() {
        //Toast.makeText(this, "logging in!", Toast.LENGTH_SHORT).show();

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        regPresenter.setHost(hostEditText.getText().toString());
        regPresenter.login(username, password);
    }

    public void register() {
//        Toast.makeText(this, "registerrrr", Toast.LENGTH_SHORT).show();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        regPresenter.setHost(hostEditText.getText().toString());
        regPresenter.register(username, password);
    }

    public void onLogin() {
        RootClientModel.getSingle_instance().deleteObserver(regPresenter);

        if (regPresenter.checkIfJoinedGame()) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(utils.GAME_RESTORED, true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, JoinGameActivity.class);
            startActivity(intent);
        }
    }

}
