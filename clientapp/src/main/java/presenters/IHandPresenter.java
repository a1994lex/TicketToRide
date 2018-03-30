package presenters;

import android.view.View;

import com.example.clientapp.IHandView;

public interface IHandPresenter {
    boolean claimingRoute();
    void setView(IHandView handView, View view);
}
