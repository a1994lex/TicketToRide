package async;

import android.os.AsyncTask;

import com.groupryan.client.UIGameFacade;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.models.ClaimRouteData;
import com.groupryan.shared.models.TrainCard;
import com.groupryan.shared.results.CommandResult;

import java.util.List;

/**
 * Created by Daniel on 3/24/2018.
 */

public class DiscardTrainCardAsyncTask extends AsyncTask<ClaimRouteData, Void, CommandResult> {

    private UIGameFacade uiGameFacade = UIGameFacade.getInstance();

    @Override
    protected CommandResult doInBackground(ClaimRouteData... claimRouteData) {
        List<Integer> trainCards = claimRouteData[0].getTrainCards();
        String username = claimRouteData[0].getUsername();
        int routeId = claimRouteData[0].getRouteId();
        CommandResult commandResult = uiGameFacade.placeRoute(trainCards, username, routeId);
        return commandResult;
    }

    @Override
    protected void onPostExecute(CommandResult commandResult) {
        for (ClientCommand clientCommand : commandResult.getClientCommands()) {
            clientCommand.execute();
        }
    }
}
