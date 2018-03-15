package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

/**
 * Created by arctu on 3/14/2018.
 */


public class DrawTrainCardAsyncTask extends AsyncTask<Integer, Void, CommandResult>{

    private UIFacade uifacade = UIFacade.getInstance();
    public DrawTrainCardAsyncTask() {
    }


    @Override
    protected CommandResult doInBackground(Integer... integers) {
        CommandResult result = this.uifacade.drawTrainCard(integers[0], RootClientModel.getUser().getUsername());
        return result;
    }

    @Override
    protected void onPostExecute(CommandResult result) {
        for (ClientCommand command : result.getClientCommands()) {
            command.execute();
        }
    }
}




