package presenters;

import com.example.clientapp.ILobbyView;
import com.groupryan.client.UIFacade;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

public class LobbyPresenter implements Observer {

    RootClientModel root;
    int maxGameSize;
    String game_title;
    Game currentGame;
    ILobbyView lobbyView;
    private static LobbyPresenter instance = new LobbyPresenter(RootClientModel.getSingle_instance());
    private UIFacade uifacade = UIFacade.getInstance();

    private LobbyPresenter(RootClientModel root) {
        this.root = root;
        root.addObserver(this);
    }

    public static void setView(ILobbyView view) {
        instance._setView(view);
    }

    public static void createTestClientGame(){  RootClientModel.getInstance().setCurrentGame(instance.currentGame);}

    public static void setGame(Game game) {
        instance._setGame(game);
    }

    public static void startGame(Game game) {
        instance._startGame(game);
    }

    private static void _setView(ILobbyView view) {
        if (instance.lobbyView == null) {
            instance.lobbyView = view;
        }
    }

    private static void _setGame(Game game) {
        instance.currentGame = game;
        instance.maxGameSize = (int) game.getMaxPlayers();
    }

    private void _startGame(Game game) {
        game_title = game.getGameName();
        uifacade.startGame(game.getGameId());
    }


    @Override
    public void update(Observable observable, Object o) {
        if (observable == root) {
            int secondSize = currentGame.getUsers().size();
            if(currentGame.isStarted()){
                lobbyView.onStartGame();
            }
            else if (secondSize == maxGameSize) {
                lobbyView.enableStartButton();
            } else {
                lobbyView.onUserJoined();
            }
        }
    }
}