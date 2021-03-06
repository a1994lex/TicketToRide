package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groupryan.client.models.RootClientModel;

import java.util.ArrayList;
import java.util.Collections;

import presenters.HistoryPresenter;


public class HistoryFragment extends Fragment implements  IHistoryView {
    private HistoryAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        HistoryPresenter.getInstance().setHistoryView(this);

        mRecyclerView = view.findViewById(R.id.history_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void updateHistory(){
        mAdapter.combineLists();
        mAdapter.notifyDataSetChanged();//make sure this works!
    }

    @Override
    public void endGame(){
        Intent intent = new Intent(this.getContext(), GameOverActivity.class);
        startActivity(intent);
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

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
        private ArrayList<String> mGameHistory = combineLists();


        public ArrayList combineLists(){
            ArrayList<String> history = RootClientModel.getCurrentGame().getHistory();
            ArrayList<String> invertedList = new ArrayList();
            for (int i = history.size() - 1; i >= 0; i--) {
                invertedList.add(history.get(i));
            }
            mGameHistory = invertedList;
            return invertedList;
        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
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
