package async;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.clientapp.BankFragment;
import com.groupryan.client.UIFacade;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import presenters.BankPresenter;

/**
 * Created by arctu on 3/14/2018.
 */


public class DrawDestinationCardsAsyncTask extends AsyncTask<Void, Void, CommandResult>{

    private UIFacade uifacade = UIFacade.getInstance();
    public DrawDestinationCardsAsyncTask() {
    }

    @Override
    protected CommandResult doInBackground(Void... voids) {
        CommandResult result = this.uifacade.drawDestinationCards( RootClientModel.getUser().getUsername());
        return result;
    }


    @Override
    protected void onPostExecute(CommandResult result) {
        for (ClientCommand command : result.getClientCommands()) {
            command.execute();
        }
    }
}




