package states.game;

import presenters.GamePlayPresenter;
import states.GameState;


public class InactiveState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {
        gpp.getGameView().showBankModal();
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {
    }

    @Override
    public void submit(GamePlayPresenter gpp) {

    }

    @Override
    public void cancel(GamePlayPresenter gpp) {

    }
}
