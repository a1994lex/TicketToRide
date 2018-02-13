package async;

import android.app.Activity;
import android.os.AsyncTask;

import com.groupryan.client.ClientCommand;
import com.groupryan.client.UIFacade;
import com.groupryan.shared.models.Color;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.utils;

import java.util.List;

/**
 * Created by clairescout on 2/12/18.
 */

public class JoinAsyncTask extends AsyncTask<Object, Void,CommandResult> {
    private Game game;
    private Activity activity;

    public JoinAsyncTask(Activity activity){this.activity = activity;}

    @Override
    protected CommandResult doInBackground(Object ... objects) {
        Game game = (Game)objects[0];
        Color color = (Color) objects[1];
        CommandResult result = UIFacade.getInstance().joinGame(game, color);
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


    public void executeCommands(List<com.groupryan.shared.commands.ClientCommand> commandList){
        for (com.groupryan.shared.commands.ClientCommand command : commandList) {
            command.execute();
        }
    }


}
