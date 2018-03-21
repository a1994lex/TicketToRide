package presenters;

import android.app.Activity;
import android.view.View;

import com.example.clientapp.IDiscardDestCardView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by arctu on 3/19/2018.
 */

public class DiscardDestCardPresenter implements Observer, IDiscardDestCardPresenter {

    private IDiscardDestCardView discardDestCardView;
    private Activity discardDestCardActivity;
    private RootClientModel root = RootClientModel.getSingle_instance();
    private ClientGame game;


    public DiscardDestCardPresenter(Activity discardDestCardActivity ){
        this.discardDestCardActivity = discardDestCardActivity;
        root.addObserver(this);}



    @Override
    public void update(Observable o, Object arg) {

    }
}
