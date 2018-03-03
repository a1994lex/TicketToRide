package presenters;

import android.view.View;

import com.example.clientapp.IChatView;
import com.groupryan.shared.models.Chat;

import java.util.List;

/**
 * Created by alex on 2/26/18.
 */

public interface IChatPresenter {
    void sendChat(String msg);
    void updateChat();
    void setView(IChatView view);
}
