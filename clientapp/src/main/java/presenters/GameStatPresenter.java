package presenters;

import android.view.View;

import com.example.clientapp.GameStatFragment;
import com.example.clientapp.IGameStatView;
import com.example.clientapp.IHistoryView;
import com.example.clientapp.R;
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
    private View fragView;
    private ClientGame game;
    private static GameStatPresenter instance = new GameStatPresenter(RootClientModel.getInstance().getCurrentGame());

    private GameStatPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }

    public static GameStatPresenter getInstance(){
        return instance;
    }


    public void setView(IGameStatView statView, View view)
    {
        this.statView = statView;
        this.fragView = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == game){

            System.out.println(this.fragView);
//            if (arg.equals(utils.STAT)){
//                //if there is a stat frag rn
//                statView.init(fragView);
//            }
//            else if (arg.equals(utils.NEW_TURN)){
//                //same as above
//                statView.init(fragView);
//            }
        }

    }

}
