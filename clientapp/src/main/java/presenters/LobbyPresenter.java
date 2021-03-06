package presenters;

import android.app.Activity;

import com.example.clientapp.ILobbyView;
import com.example.clientapp.LobbyActivity;
import com.groupryan.client.UIFacade;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

import async.GamePoller;
import async.Poller;
import async.StartGameAsyncTask;

public class LobbyPresenter implements Observer {

    RootClientModel root;
    int maxGameSize;
    Game currentGame;
    ILobbyView lobbyView;
    private Activity lobbyActivity;
    private static LobbyPresenter instance = new LobbyPresenter(RootClientModel.getSingle_instance());

    private LobbyPresenter(RootClientModel root) {
        this.root = root;
        root.addObserver(this);
    }

    public static void setView(ILobbyView view) {
        instance._setView(view);
    }

    public static void setGame(Game game) {
        instance._setGame(game);
    }

    public static void startGame(Game game) {
        instance._startGame(game);
    }

    public static void setActivity(Activity activity) {
        instance._setActivity(activity);
    }

    private static void _setView(ILobbyView view) {
        if (instance.lobbyView == null) {
            instance.lobbyView = view;
        }
    }

    private static void _setActivity(Activity activity) {
        instance.lobbyActivity = activity;
    }


    private static void _setGame(Game game) {
        instance.currentGame = game;
        instance.maxGameSize = (int) game.getMaxPlayers();
    }

    private void _startGame(Game game) {
        StartGameAsyncTask startGameAsyncTask = new StartGameAsyncTask(instance.lobbyActivity);
        startGameAsyncTask.execute(game);
    }

    public static void startGamePoller() {
        instance._startGamePoller();
    }

    private void _startGamePoller() {
        String gameId = currentGame.getGameId();
        GamePoller.get(gameId).poll();
    }


    @Override
    public void update(Observable observable, Object o) {
        if (observable == root) {
            int secondSize = currentGame.getUsers().size();
            if (o != null && o.equals(currentGame.getGameId())) {
                lobbyView.onStartGame();
            } else if (secondSize == maxGameSize) {
                lobbyView.enableStartButton();
            } else {
                lobbyView.onUserJoined();
            }
        }
    }
}