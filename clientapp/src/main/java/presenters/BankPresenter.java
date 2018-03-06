package presenters;

import android.view.View;

import com.example.clientapp.IBankView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.utils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ryanm on 3/3/2018.
 */

public class BankPresenter implements Observer, IBankPresenter {

    private IBankView bankView;
    private View fragView;
    private ClientGame game;
    private static BankPresenter instance = new BankPresenter(RootClientModel.getInstance().getCurrentGame());

    private BankPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }

    public static BankPresenter getInstance(){
        return instance;
    }

    @Override
    public void updateBank() {

    }

    public ArrayList<TrainCard> getBank(){
        return RootClientModel.getCurrentGame().getBankCards();
    }

    @Override
    public void setView(IBankView bankView, View view)
    {
        this.bankView = bankView;
        this.fragView = view;
    }

    @Override
    public void cardOneClicked() {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.BANK)){
                bankView.init(fragView);
            }
        }
    }

}
