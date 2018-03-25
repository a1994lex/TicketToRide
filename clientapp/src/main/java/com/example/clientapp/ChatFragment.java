package com.example.clientapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Chat;
import com.groupryan.shared.utils;


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
                    String chat = mNewChat.getText().toString();
                    mNewChat.clearComposingText();
                    ChatPresenter.getInstance().sendChat(chat);
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
        //view.setZ(15);
        return view;
    }

    @Override
    public void endGame(){
        Intent intent = new Intent(this.getContext(), GameOverActivity.class);
        startActivity(intent);
    }

    protected class ChatHolder extends RecyclerView.ViewHolder{

        private TextView mChat;
        private LinearLayout mView;
        public ChatHolder(View itemView){
            super(itemView);
            mChat = itemView.findViewById(R.id.chat_content);
            mView = itemView.findViewById(R.id.color_holder);
        }

        public void bindChat(Chat chat){
            mChat.setText(chat.getMesssage());
            String color = chat.getColor();
            int cid = 0;
            switch(color){
                case utils.BLUE:
                    cid = R.color.blue_back;
                    break;
                case utils.YELLOW:
                    cid = R.color.yellow_back;
                    break;
                case utils.GREEN:
                    cid = R.color.green_back;
                    break;
                case utils.RED:
                    cid = R.color.red_back;
                    break;
                default:
                    cid = R.color.black_back;
            }

            mView.setBackgroundColor(cid);
        }
    }

    protected class ChatAdapter extends RecyclerView.Adapter<ChatHolder>{
        private ArrayList<Chat> mChats = combineLists();


        public ArrayList combineLists(){
            ArrayList<Chat> chats = RootClientModel.getCurrentGame().getChat();
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
            holder.bindChat(mChats.get(position));
        }


        @Override
        public int getItemCount() {
            return mChats.size();
        }
    }

}
