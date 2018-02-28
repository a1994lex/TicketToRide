package presenters;

import com.example.clientapp.IHistoryView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.utils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by clairescout on 2/27/18.
 */

public class HistoryPresenter implements Observer, IHistoryPresenter {


    private IHistoryView historyView;
    private ClientGame game;
    private static HistoryPresenter instance = new HistoryPresenter(RootClientModel.getInstance().getCurrentGame());

    private HistoryPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }

    public static HistoryPresenter getInstance(){
        return instance;
    }


    public void setHistoryView(IHistoryView historyView) {
        this.historyView = historyView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == game){
            if (arg.equals(utils.CHAT)){
                historyView.updateHistory();
            }
        }
    }

}
