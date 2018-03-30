package presenters;

import android.view.View;

import com.example.clientapp.IHandView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.utils;

import java.util.Observable;
import java.util.Observer;

public class HandPresenter implements Observer, IHandPresenter {


    private IHandView handView;
    private View fragView;
    private ClientGame game;
    private static HandPresenter instance = new HandPresenter(RootClientModel.getInstance().getCurrentGame());

    private HandPresenter(ClientGame clientGame) {
        this.game = clientGame;
        game.addObserver(this);
    }

    public static HandPresenter getInstance() {
        return instance;
    }

    @Override
    public void setView(IHandView handView, View view) {
        this.handView = handView;
        this.fragView = view;
    }

    @Override
    public boolean claimingRoute(){
        if (GamePlayPresenter.getInstance().isClaimingRoute()){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o == game) {
            if (arg.equals(utils.HAND)) {
//                handView.init(fragView);
            } else if(o.equals(utils.GAME_OVER)){
                handView.endGame();
            }
        }
    }
}
