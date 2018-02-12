package presenters;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.example.clientapp.IJoinGameView;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;

import java.util.Observable;
import java.util.Observer;

import static com.groupryan.client.models.RootClientModel.getGames;

public class JoinGamePresenter implements Observer {
    RootClientModel root;
    int gameListSize = getGames().size();
    String game_title;
    IJoinGameView gameView;
    IJoinGameView dialogView;
    private static JoinGamePresenter instance = new JoinGamePresenter(RootClientModel.getSingle_instance());
    private UIFacade uifacade = UIFacade.getInstance();

    private JoinGamePresenter(RootClientModel root){
        this.root = root;
        root.addObserver(this);
    }
    public static void setView(IJoinGameView view){
        instance._setView(view);
    }
    public static void joinGame(Game game, Color color){
        instance._joinGame(game, color);
    }
    private void _createGame(String title, int numPlayers, Color color){
        game_title = title;
        uifacade.createGame(color, title, numPlayers);

    }


    private void _joinGame(Game game, Color color){
        if (uifacade.joinGame(game, color, this) != null);
    }

    private static void _setView(IJoinGameView view){
        if (instance.gameView == null){
            instance.gameView = view;
        }
        else if (instance.dialogView == null){
            instance.dialogView = view;
        }
    }

    public static void createGame(String title, int numPlayers, Color color){
        instance._createGame(title, numPlayers, color);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root){
            int secondSize = root.getGames().size();
            if (secondSize > gameListSize){
                gameView.onGameAdd();
            }
            else if (secondSize <gameListSize){
//                gameView.onGameDelete();
            }

            for (Game game :root.getGames()){
                if (game.getGameName() == game_title){
                    dialogView.join(game.getGameId());
                }
            }
        }

    }
}
