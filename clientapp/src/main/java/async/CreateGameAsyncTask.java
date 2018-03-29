package async;

import android.app.Activity;
import android.os.AsyncTask;

import com.groupryan.client.UIFacade;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.List;

/**
 * Created by clairescout on 2/12/18.
 */

public class CreateGameAsyncTask extends AsyncTask<Object, Void, CommandResult>{

    /*String userColor, String gameName, int numberOfPlayers*/
    private Activity activity;

    public CreateGameAsyncTask(Activity activity){this.activity = activity;}

    @Override
    protected CommandResult doInBackground(Object ... objects) {
        String color = (String)objects[0];
        String gameName = (String)objects[1];
        int numOfPlayers = (Integer)objects[2];
        CommandResult result = UIFacade.getInstance().createGame(color, gameName, numOfPlayers);
        return result;
    }

    @Override
    protected void onPostExecute(CommandResult result){
        OnJoinOrCreate onJoinOrCreate = (OnJoinOrCreate)activity;
        if(result.getResultType().equals(utils.VALID)) {
            executeCommands(result.getClientCommands());
        }
        else{
//            Print error
            onJoinOrCreate.onJoinOrCreate(result.getResultType());
        }
    }

    public void executeCommands(List<ClientCommand> commandList){
        for (ClientCommand command : commandList) {
//            command.execute();
        }
    }
}
