package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.List;


public class DiscardDestCardAsyncTask extends AsyncTask<List<Integer>, Void, CommandResult> {

    Activity activity;
    private UIFacade uifacade = UIFacade.getInstance();


    public DiscardDestCardAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected CommandResult doInBackground(List<Integer>... cardIDs) {
        CommandResult result = this.uifacade.discardDestinationCard(cardIDs[0],
                RootClientModel.getUser().getUsername());
        return result;
    }

    @Override
    protected void onPostExecute(CommandResult result) {
        for (ClientCommand command : result.getClientCommands()) {
            command.execute();
        }
        this.activity.finish();
    }

}
