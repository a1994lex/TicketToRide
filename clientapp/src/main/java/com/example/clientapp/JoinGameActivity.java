package com.example.clientapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.clientapp.dialogs.JoinGameDialogActivity;
import com.example.clientapp.dialogs.CreateGameDialogActivity;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;

import async.OnJoinOrCreate;
import presenters.JoinGamePresenter;

public class JoinGameActivity extends AppCompatActivity implements IJoinGameView {
    Button mCreateGamebtn;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
//    Boolean addGame;
//    Boolean removeGame;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoinGamePresenter.setView(this);
        JoinGamePresenter.setActivity(this);

        setContentView(R.layout.activity_game_list);
        mRecyclerView = findViewById(R.id.game_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new GameAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mCreateGamebtn = findViewById(R.id.create_game_btn);

        mCreateGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JoinGameActivity.this, CreateGameDialogActivity.class);
                startActivity(i);
            }
        });

    }
   @Override
   public void onGameAdd(){
//      This method is called by the JoinGamePresenter to update the View
//        addGame = true;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGameDelete(){//int position){
//      This method is called by the JoinGamePresenter to update the View
//        mRecyclerView.removeViewAt(position);
//        mAdapter.notifyItemRemoved(position);
//        mAdapter.notifyItemRangeChanged(position, RootClientModel.getGames().size());
//        removeGame = true;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void join(String gameid){
//        This is really implemented in the JoinGameDialogActivity and the CreateGameDialogActivity
    }

    @Override
    public void error(String msg){
//        This is really implemented in the JoinGameDialogActivity and the CreateGameDialogActivity

    }


    private class GameHolder extends RecyclerView.ViewHolder{
        private Button mJoinGameBtn;
        private TextView mGameTitle;
        private Game mGame;
         public GameHolder(View itemView){
             super(itemView);
             mJoinGameBtn = itemView.findViewById(R.id.join_game_btn);
             mGameTitle = itemView.findViewById(R.id.game_title);


             mJoinGameBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(mGame.getUsers().containsKey(RootClientModel.getUser().getUsername())){

                         Intent i = new Intent(JoinGameActivity.this, LobbyActivity.class);
                         i.putExtra(utils.GAME_ID_TAG, mGame.getGameId());
                         startActivity(i);
                     }
                     else {
                         Intent i = new Intent(JoinGameActivity.this, JoinGameDialogActivity.class);
                         i.putExtra(utils.GAME_ID_TAG, mGame.getGameId());
                         startActivity(i);
                     }
                 }
             });
         }

         public void bindGame(Game game){
             this.mGame = game;
             mGameTitle.setText(game.getGameName());
         }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder>{
        private ArrayList<Game> mGames = combineLists();


        public ArrayList combineLists(){
           // List<Game> usersGames = RootClientModel.getUser().getGameList();
            ArrayList<Game> games = RootClientModel.getGames();
           // games.addAll(usersGames);
            return games;
        }

        @Override
        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(JoinGameActivity.this);
            View view = layoutInflater.inflate(R.layout.item_game_in_gamelist, parent, false);
            return new GameHolder(view);
        }

        @Override
        public void onBindViewHolder(GameHolder holder, int position) {
                Game game = mGames.get(position);
                holder.bindGame(game);
        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }
}
