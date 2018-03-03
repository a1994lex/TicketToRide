package async;

import android.os.AsyncTask;

import com.example.clientapp.IChatView;
import com.groupryan.client.UIFacade;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.List;

/**
 * Created by alex on 2/26/18.
 */

public class ChatAsyncTask extends AsyncTask<String, Void, CommandResult> {


    @Override
    protected CommandResult doInBackground(String... strings) {
        CommandResult result = UIFacade.getInstance().sendChat(strings[0]);
        return result;
    }


    @Override
    protected void onPostExecute(CommandResult result){
        // do nothing
        if(result.getResultType().equals(utils.VALID)) {
            for (com.groupryan.shared.commands.ClientCommand command : result.getClientCommands()) {
                command.execute();
            }
        }
    }

}
