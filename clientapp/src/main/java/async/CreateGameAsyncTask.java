package async;

import android.app.Activity;
import android.os.AsyncTask;

import com.groupryan.client.UIFacade;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;

/**
 * Created by clairescout on 2/12/18.
 */

public class CreateGameAsyncTask extends AsyncTask<Object, Void, String>{

    /*Color userColor, String gameName, int numberOfPlayers*/
    private Activity activity;

    public CreateGameAsyncTask(Activity activity){this.activity = activity;}

    @Override
    protected String doInBackground(Object ... objects) {
        Color color = (Color)objects[0];
        String gameName = (String)objects[1];
        int numOfPlayers = (Integer)objects[2];
        String errormsg = UIFacade.getInstance().createGame(color, gameName, numOfPlayers);
        return errormsg;
    }

    @Override
    protected void onPostExecute(String errormsg){
        OnJoinOrCreate onJoinOrCreate = (OnJoinOrCreate)activity;
        onJoinOrCreate.onJoinOrCreate(errormsg);
    }
}
