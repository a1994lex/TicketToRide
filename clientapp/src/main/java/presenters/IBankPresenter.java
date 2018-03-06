package presenters;

import android.view.View;

import com.example.clientapp.IBankView;

/**
 * Created by ryanm on 3/3/2018.
 */

public interface IBankPresenter {
    void updateBank();
    void setView(IBankView bankView, View view);
    void cardOneClicked();
}
