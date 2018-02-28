package presenters;

import android.view.View;

import com.example.clientapp.IChatView;
import com.example.clientapp.IJoinGameView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import async.ChatAsyncTask;

/**
 * Created by alex on 2/26/18.
 */

public class ChatPresenter implements Observer, IChatPresenter {
    private ClientGame game;
    IChatView chatView;

    private static ChatPresenter instance = new ChatPresenter(RootClientModel.getInstance().getCurrentGame());

    private ChatPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }

    public static ChatPresenter getInstance(){
        return instance;
    }

    @Override
    public void setView(IChatView view) {
        chatView = view;
    }

    @Override
    public void sendChat(String msg) {
        ChatAsyncTask chatAsyncTask = new ChatAsyncTask();
        chatAsyncTask.execute(msg);
    }

    @Override
    public void updateChat() {
        chatView.onChatAdd();
    }




    @Override
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.CHAT)){
                updateChat();
            }
        }
    }
}
