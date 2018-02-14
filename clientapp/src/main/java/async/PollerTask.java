package async;

import android.os.AsyncTask;

import com.groupryan.client.ServerProxy;
import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;

import java.util.List;

public class PollerTask extends AsyncTask<Void, Void, List<ClientCommand>> {

    public PollerTask() {
    }

    @Override
    protected List<ClientCommand> doInBackground(Void... voids) {
        System.out.println("PollerTask polling.....");
        ServerProxy serverProxy = ServerProxy.getInstance();
        CommandResult commandResult = serverProxy.getCommands(RootClientModel.getUser());
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
