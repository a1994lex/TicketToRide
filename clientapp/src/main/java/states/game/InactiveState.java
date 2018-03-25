package states.game;

import presenters.GamePlayPresenter;
import states.GameState;


public class InactiveState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {
        gpp.getGameActivity().showClaimRouteModal();
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {
        return;
    }

    @Override
    public void submit(GamePlayPresenter gpp) {

    }

    @Override
    public void cancel(GamePlayPresenter gpp) {

    }
}
