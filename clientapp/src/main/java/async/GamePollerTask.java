package async;

import android.os.AsyncTask;

import com.groupryan.client.ServerProxy;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;

import java.util.List;

/**
 * Created by alex on 2/26/18.
 */

public class GamePollerTask extends AsyncTask<Void, Void, List<ClientCommand>> {

    String gameId;
    String playerId;
    public GamePollerTask(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    @Override
    protected List<ClientCommand> doInBackground(Void... voids) {
        System.out.println("PollerTask polling.....");
        ServerProxy serverProxy = ServerProxy.getInstance();
        CommandResult commandResult = serverProxy.getGameCommands(gameId, playerId);
        List<ClientCommand> commandList = commandResult.getClientCommands();
        return commandList;
    }


    @Override
    protected void onPostExecute(List<ClientCommand> commandList) {
        System.out.println(commandList);
        for (ClientCommand command : commandList) {
            command.execute();
        }
    }
}
