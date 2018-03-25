package states.game;

import presenters.GamePlayPresenter;
import states.GameState;


public class DrawCardState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {
        return;
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {
        return;
    }

    @Override
    public void submit(GamePlayPresenter gpp) {
        gpp.setState(new InactiveState());
    }

    @Override
    public void cancel(GamePlayPresenter gpp) {
        gpp.setState(new ActiveState());
    }
}
