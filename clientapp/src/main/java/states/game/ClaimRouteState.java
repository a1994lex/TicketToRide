package states.game;

import com.groupryan.shared.models.ClaimRouteData;

import async.DiscardTrainCardAsyncTask;
import presenters.GamePlayPresenter;
import states.GameState;


public class ClaimRouteState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {
        return;
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {
        gpp.setState(new ClaimRouteState());
        gpp.getGameView().showClaimRouteModal();
    }

    @Override
    public void submit(GamePlayPresenter gpp) {
        gpp.callClaimRouteAsyncTask();
        gpp.setState(new InactiveState());
        gpp.endTurn();
    }

    @Override
    public void cancel(GamePlayPresenter gpp) {
        gpp.setState(new ActiveState());
    }

}
