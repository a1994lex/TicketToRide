package presenters;

import android.app.Activity;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.example.clientapp.IJoinGameView;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;

import java.util.Observable;
import java.util.Observer;

import async.CreateGameAsyncTask;
import async.JoinAsyncTask;
import async.OnJoinOrCreate;

import static com.groupryan.client.models.RootClientModel.getGames;

public class JoinGamePresenter implements Observer, IJoinGamePresenter{
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

    private JoinGamePresenter(RootClientModel root){
        this.root = root;
        root.addObserver(this);
    }

    private static void _setActivity(Activity activity){ instance.joinGameActivity = activity;}
    private static void _setJoinDialogActivity(Activity activity){instance.joinDialogActivity = activity;}
    private static void _setCreateDialogActivity(Activity activity){instance.createDialogActivity = activity;}
    public static void setView(IJoinGameView view){
        instance._setView(view);
    }
    public static void joinGame(Game game, String color){
        instance._joinGame(game, color);
    }
    private void _createGame(String title, int numPlayers, String color){
        game_title = title;
        CreateGameAsyncTask createGameAsyncTask = new CreateGameAsyncTask(createDialogActivity);
        Object[] objects = {color, title, numPlayers};
        createGameAsyncTask.execute(objects);

    }


    private void _joinGame(Game game, String color){
        game_title = game.getGameName();

        JoinAsyncTask joinAsyncTask = new JoinAsyncTask(joinDialogActivity);
        Object[] objects = {game, color};
        joinAsyncTask.execute(objects);
    }

    private static void _setView(IJoinGameView view){
        if (instance.gameView == null){
            instance.gameView = view;
        }
        else if (instance.dialogView == null){
            instance.dialogView = view;
        }
    }

    public static void createGame(String title, int numPlayers, String color){
        instance._createGame(title, numPlayers, color);
    }

    public static void setActivity(Activity activity){
        instance._setActivity(activity);
    }
    public static void setJoinDialogActivity(Activity activity){instance._setJoinDialogActivity(activity);}
    public static void setCreateDialogActivity(Activity activity){instance._setCreateDialogActivity(activity);}

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root){
            int secondSize = root.getGames().size();
            if (secondSize > gameListSize){
                gameView.onGameAdd();
            }
            else if(secondSize < gameListSize){
                gameView.onGameDelete();
            }
//            else if (){
////                gameView.onGameDisable(id);
//            }

            for (Game game : root.getUser().getGameList()){
                if (game.getGameName().equals(game_title)){
                    dialogView.join(game.getGameId());
                }
            }
        }

    }


}
