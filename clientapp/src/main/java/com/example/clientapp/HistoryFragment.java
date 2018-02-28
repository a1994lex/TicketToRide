package com.example.clientapp;

import android.content.Context;
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

import presenters.HistoryPresenter;


public class HistoryFragment extends Fragment implements  IHistoryView {
    private RecyclerView.Adapter mAdapter;
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
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new HistoryFragment.HistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
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

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryFragment.HistoryHolder>{
        private ArrayList<String> mGameHistory = combineLists();


        public ArrayList combineLists(){
            ArrayList<String> history = RootClientModel.getCurrentGame().getHistory();
            return history;
        }

        @Override
        public HistoryFragment.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_history_item, parent, false);
            return new HistoryFragment.HistoryHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryFragment.HistoryHolder holder, int position) {
            String chat = mGameHistory.get(position);
            holder.bindChat(chat);
        }

        @Override
        public int getItemCount() {
            return mGameHistory.size();
        }
    }

}
