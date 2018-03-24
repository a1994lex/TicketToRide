package presenters;

import com.example.clientapp.IGameOverView;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.EndGameStat;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by clairescout on 3/24/18.
 */

public class GameOverPresenter implements Observer, IGameOverPresenter {

    IGameOverView gameOverView;
    private ClientGame game;


    private static GameOverPresenter instance = new GameOverPresenter(RootClientModel.getCurrentGame());


    private GameOverPresenter(ClientGame clientGame){
        this.game = clientGame;
        game.addObserver(this);
    }


    public static IGameOverPresenter getInstance(){
        return instance;
    }

    @Override
    public void setView(IGameOverView gameOverView){
        this.gameOverView = gameOverView;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public String getWinnerString(){
        String winnerString = game.getWinner();
        winnerString += " won!!";
        return winnerString;
    }

    @Override
    public List<EndGameStat> getEndGameStats(){
        return game.getEndGameStats();
    }

}
