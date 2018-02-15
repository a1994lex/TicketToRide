package com.example.clientapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.dialogs.CreateGameDialogActivity;
import com.example.clientapp.dialogs.JoinGameDialogActivity;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Set;

import presenters.JoinGamePresenter;
import presenters.LobbyPresenter;

public class LobbyActivity extends AppCompatActivity implements ILobbyView {
    Button mStartGameBtn;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LobbyPresenter.setView(this);
        setContentView(R.layout.activity_lobby);

        Intent i = getIntent();
        String gameId = i.getStringExtra(utils.GAME_ID_TAG);
        for (Game game : RootClientModel.getUser().getGameList()) {
            if (game.getGameId().equals(gameId)) {
                mGame = game;
                LobbyPresenter.setGame(game);
            }
        }

        mRecyclerView = findViewById(R.id.players_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new LobbyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mStartGameBtn = findViewById(R.id.start_game_btn);
        mStartGameBtn.setEnabled(false);

        mStartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LobbyPresenter.startGame(mGame);
                if (mGame.getMaxPlayers() == (double) mGame.getUsers().size()) {
                    Intent i = new Intent(LobbyActivity.this, GamePlayActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onUserJoined() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartGame() {
        // ???????
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void enableStartButton() {
        mStartGameBtn.setEnabled(true);
    }

    private class LobbyHolder extends RecyclerView.ViewHolder {
        private TextView mPlayerName;
        private String mUserId;

        public LobbyHolder(View itemView) {
            super(itemView);
            mPlayerName = itemView.findViewById(R.id.player_name);
        }

        public void bindUser(String userId) {
            this.mUserId = userId;
//            mPlayerName.setBackgroundColor();
            mPlayerName.setText(mUserId);
        }
    }

    private class LobbyAdapter extends RecyclerView.Adapter<LobbyActivity.LobbyHolder> {
        private ArrayList<String> mUserIds = new ArrayList<>(mGame.getUsers().keySet());

        @Override
        public LobbyActivity.LobbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(LobbyActivity.this);
            View view = layoutInflater.inflate(R.layout.item_player_in_lobby, parent, false);
            return new LobbyHolder(view);
        }

        @Override
        public void onBindViewHolder(LobbyActivity.LobbyHolder holder, int position) {
            String userId = mUserIds.get(position);
            holder.bindUser(userId);
        }

        @Override
        public int getItemCount() {
            if (mUserIds.size() == mGame.getMaxPlayers()) {
                enableStartButton();
            }
            return mUserIds.size();
        }
    }
}
