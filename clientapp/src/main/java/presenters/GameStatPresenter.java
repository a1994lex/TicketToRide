package presenters;

import com.example.clientapp.IGameStatView;
import com.example.clientapp.IHistoryView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.utils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by clairescout on 3/3/18.
 */

public class GameStatPresenter implements Observer, IGameStatPresenter {


    private IGameStatView statView;
    private ClientGame game;
    private static GameStatPresenter instance = new GameStatPresenter(RootClientModel.getInstance().getCurrentGame());

    private GameStatPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }

    public static GameStatPresenter getInstance(){
        return instance;
    }


    public void setView(IGameStatView gameView) {
        this.statView = statView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.STAT)){
                statView.init();
            }
        }
    }

}
