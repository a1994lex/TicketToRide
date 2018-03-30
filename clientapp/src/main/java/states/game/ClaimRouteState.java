package states.game;

import com.groupryan.shared.models.ClaimRouteData;

import presenters.GamePlayPresenter;
import states.GameState;


public class ClaimRouteState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {
        return;
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {

    }

    @Override
    public void submit(GamePlayPresenter gpp) {
        gpp.setState(new InactiveState());
        gpp.callClaimRouteAsyncTask();
    }

    @Override
    public void cancel(GamePlayPresenter gpp) {
        gpp.setState(new ActiveState());
    }

}
