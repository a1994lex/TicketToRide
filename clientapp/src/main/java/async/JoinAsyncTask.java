package async;

import android.app.Activity;
import android.os.AsyncTask;

import com.groupryan.client.UIFacade;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;

/**
 * Created by clairescout on 2/12/18.
 */

public class JoinAsyncTask extends AsyncTask<Object, Void, String> {

    private Activity activity;

    public JoinAsyncTask(Activity activity){this.activity = activity;}

    @Override
    protected String doInBackground(Object ... objects) {
        Game game = (Game)objects[0];
        Color color = (Color) objects[1];
        String errormsg = UIFacade.getInstance().joinGame(game, color);
        return errormsg;
    }

    @Override
    protected void onPostExecute(String errormsg){
        OnJoinOrCreate onJoinOrCreate = (OnJoinOrCreate)activity;
        onJoinOrCreate.onJoinOrCreate(errormsg);

    }


}
