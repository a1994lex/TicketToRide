package presenters;

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

public class JoinGamePresenter implements Observer, OnJoinOrCreate, IJoinGamePresenter{
    RootClientModel root;
    int gameListSize = getGames().size();
    String game_title;
    IJoinGameView gameView;
    IJoinGameView dialogView;
    private static JoinGamePresenter instance = new JoinGamePresenter(RootClientModel.getSingle_instance());
    private UIFacade uifacade = UIFacade.getInstance();
    Activity joinGameActivity;

    private JoinGamePresenter(RootClientModel root){
        this.root = root;
        root.addObserver(this);
    }

    private static void _setActivity(Activity activity){ instance.joinGameActivity = activity;}
    public static void setView(IJoinGameView view){
        instance._setView(view);
    }
    public static void joinGame(Game game, Color color){
        instance._joinGame(game, color);
    }
    private void _createGame(String title, int numPlayers, Color color){
        game_title = title;
//        String errormsg = uifacade.createGame(color, title, numPlayers);
//        if (errormsg != null){
//            dialogView.error(errormsg);
//        }
        CreateGameAsyncTask createGameAsyncTask = new CreateGameAsyncTask(joinGameActivity);
        Object[] objects = {color, title, numPlayers};
        createGameAsyncTask.execute(objects);

    }


    private void _joinGame(Game game, Color color){
        game_title = game.getGameName();
//        String errormsg = uifacade.joinGame(game, color);
//        if (errormsg != null){
//            dialogView.error(errormsg);
//        }
        JoinAsyncTask joinAsyncTask = new JoinAsyncTask(joinGameActivity);
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

    public static void createGame(String title, int numPlayers, Color color){
        instance._createGame(title, numPlayers, color);
    }

    public static void setActivity(Activity activity){
        instance._setActivity(activity);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable == root){
            int secondSize = root.getGames().size();
            if (secondSize > gameListSize){
                gameView.onGameAdd();
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

    @Override
    public void onJoinOrCreate(String errormsg) {
        if (errormsg != null){
            dialogView.error(errormsg);
        }
    }
}
