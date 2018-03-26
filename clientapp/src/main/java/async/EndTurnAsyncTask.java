package async;

import android.os.AsyncTask;

import com.groupryan.client.UIFacade;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

/**
 * Created by root on 3/26/18.
 */

public class EndTurnAsyncTask  extends AsyncTask<Void, Void, CommandResult> {
    @Override
    protected void onPostExecute(CommandResult result) {

        if(result.getResultType().equals(utils.VALID)) {
            for (ClientCommand command : result.getClientCommands()) {
                command.execute();
            }
        }
    }

    @Override
    protected CommandResult doInBackground(Void... voids) {
        CommandResult result = UIFacade.getInstance().endTurn();
        return result;
    }
}

