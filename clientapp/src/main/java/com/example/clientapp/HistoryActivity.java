package com.example.clientapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;

import java.util.ArrayList;

import presenters.HistoryPresenter;


public class HistoryActivity extends AppCompatActivity implements  IHistoryView {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        HistoryPresenter.getInstance().setHistoryView(this);


        mRecyclerView = findViewById(R.id.history_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void updateHistory(){
        mAdapter.notifyDataSetChanged(); //make sure this works!
    }


    private class HistoryHolder extends RecyclerView.ViewHolder{

        private TextView mHistoryTextView;
        public HistoryHolder(View itemView){
            super(itemView);
            mHistoryTextView = itemView.findViewById(R.id.history_item);
        }

        public void bindChat(String history){
            mHistoryTextView.setText(history);
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>{
        private ArrayList<String> mGameHistory = combineLists();


        public ArrayList combineLists(){
            ArrayList<String> history = RootClientModel.getCurrentGame().getHistory();
            return history;
        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HistoryActivity.this);
            View view = layoutInflater.inflate(R.layout.activity_history_item, parent, false);
            return new HistoryHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryHolder holder, int position) {
            String chat = mGameHistory.get(position);
            holder.bindChat(chat);
        }

        @Override
        public int getItemCount() {
            return mGameHistory.size();
        }
    }

}
