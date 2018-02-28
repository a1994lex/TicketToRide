package com.example.clientapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;


import java.util.ArrayList;

import presenters.ChatPresenter;

/**
 * A fragment representing a list of Items.
 * <p/>

 */
public class ChatFragment extends Fragment implements IChatView {
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    EditText mNewChat;
    Button mSendChat;

    @Override
    public void onChatAdd() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ChatPresenter.getInstance().setView(this);

        mNewChat = view.findViewById(R.id.msg);
        mSendChat = view.findViewById(R.id.sendChatBtn);

        mSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewChat.getText().length()>0) {
                    ChatPresenter.getInstance().sendChat(mNewChat.getText().toString());
                }
                else {
                    Toast.makeText(getActivity(), "Write a message", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRecyclerView = view.findViewById(R.id.recycler_list);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new ChatAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class ChatHolder extends RecyclerView.ViewHolder{

        private TextView mChat;
        public ChatHolder(View itemView){
            super(itemView);
            mChat = itemView.findViewById(R.id.chat_content);
        }

        public void bindChat(String chat){
            mChat.setText(chat);
        }
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatHolder>{
        private ArrayList<String> mChats = combineLists();


        public ArrayList combineLists(){
            ArrayList<String> chats = RootClientModel.getCurrentGame().getChat();
            return chats;
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_chat, parent, false);
            return new ChatHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatHolder holder, int position) {
            String chat = mChats.get(position);
            holder.bindChat(chat);
        }

        @Override
        public int getItemCount() {
            return mChats.size();
        }
    }

}
