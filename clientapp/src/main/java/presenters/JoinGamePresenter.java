package presenters;

import android.app.Activity;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.ClientGame;
import com.groupryan.client.models.RootClientModel;
import com.example.clientapp.IJoinGameView;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.utils;

import java.util.Observable;
import java.util.Observer;

import async.CreateGameAsyncTask;
import async.JoinAsyncTask;

import static com.groupryan.client.models.RootClientModel.getGames;

public class JoinGamePresenter implements Observer, IJoinGamePresenter {
    RootClientModel root;
    int gameListSize = getGames().size();
    String game_title;
    IJoinGameView gameView;
    IJoinGameView dialogView;
    private static JoinGamePresenter instance = new JoinGamePresenter(RootClientModel.getSingle_instance());
    private UIFacade uifacade = UIFacade.getInstance();
    Activity joinGameActivity;
    Activity joinDialogActivity;
    Activity createDialogActivity;

    private JoinGamePresenter(RootClientModel root) {
        this.root = root;
        root.addObserver(this);
    }

    private static void _setActivity(Activity activity) {
        instance.joinGameActivity = activity;
    }

    private static void _setJoinDialogActivity(Activity activity) {
        instance.joinDialogActivity = activity;
    }

    private static void _setCreateDialogActivity(Activity activity) {
        instance.createDialogActivity = activity;
    }

    public static void setView(IJoinGameView view) {
        instance._setView(view);
    }

    public static void joinGame(Game game, String color) {
        instance._joinGame(game, color);
    }

    private void _createGame(String title, int numPlayers, String color) {
        game_title = title;
        CreateGameAsyncTask createGameAsyncTask = new CreateGameAsyncTask(createDialogActivity);
        Object[] objects = {color, title, numPlayers};
        createGameAsyncTask.execute(objects);

    }


    private void _joinGame(Game game, String color) {
        game_title = game.getGameName();

        JoinAsyncTask joinAsyncTask = new JoinAsyncTask(joinDialogActivity);
        Object[] objects = {game, color};
        joinAsyncTask.execute(objects);
    }

    private static void _setView(IJoinGameView view) {
        if (instance.gameView == null) {
            instance.gameView = view;
        } else if (instance.dialogView == null) {
            instance.dialogView = view;
        }
    }

    public static void createGame(String title, int numPlayers, String color) {
        instance._createGame(title, numPlayers, color);
    }

    public static void setActivity(Activity activity) {
        instance._setActivity(activity);
    }

    public static void setJoinDialogActivity(Activity activity) {
        instance._setJoinDialogActivity(activity);
    }

    public static void setCreateDialogActivity(Activity activity) {
        instance._setCreateDialogActivity(activity);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root) {
            int secondSize = root.getGames().size();
            if (secondSize > gameListSize) {
                gameView.onGameAdd();
            } else if (secondSize < gameListSize) {
                gameView.onGameDelete();
            }
//            else if (){
////                gameView.onGameDisable(id);
//            }
            if (o == null) {
                String userGameId = RootClientModel.getUser().getGameId();
                for (Game game : RootClientModel.getGames()) {
                    if (userGameId.equals(game.getGameId()) && game.getGameName().equals(game_title)) {
                        dialogView.join(game.getGameId());
                    }
                }
            } else if (o.getClass().equals(ClientGame.class)) {
//                if (checkIfJoinedGame()) {
                ClientGame cg = (ClientGame) o;
                gameView.join(cg.getGameId());
//                }
            } else if (o == utils.REJOIN_LOBBY) {
                String id = RootClientModel.getRejoinLobbyGameId();
                if (id != null) {
                    gameView.join(id);
                }
            }
        }


    }

    public boolean checkIfJoinedGame() {
        //checks to see if a user has already joined a game, and if they have, takes them directly to game activity
        if (RootClientModel.getCurrentGame() == null) {
            return false;
        } else {
            return true;
        }

    }


}
