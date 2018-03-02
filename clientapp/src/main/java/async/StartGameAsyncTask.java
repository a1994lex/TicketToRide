package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.models.Game;

public class StartGameAsyncTask extends AsyncTask<Game, Void, Void> {

    private Activity lobbyActivity;

    public StartGameAsyncTask(Activity lobbyActivity) {
        this.lobbyActivity = lobbyActivity;
    }

    @Override
    protected Void doInBackground(Game... params) {
        UIFacade uiFacade = UIFacade.getInstance();
        uiFacade.startGame(params[0].getGameId());
        return null;
    }

    public void onPostExecute(Void param) {
        
    }
}
