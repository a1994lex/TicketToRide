package states.game;

import presenters.GamePlayPresenter;
import states.GameState;


public class ActiveState implements GameState {
    @Override
    public void clickDrawCard(GamePlayPresenter gpp) {

        gpp.setState(new DrawCardState());
        gpp.getBankPresenter().setState(new states.bank.ActiveState());
        gpp.getGameView().showBankModal();
    }

    @Override
    public void clickClaimRoute(GamePlayPresenter gpp) {
        gpp.setState(new ClaimRouteState());
        gpp.getGameView().showClaimRouteModal();
    }

    @Override
    public void submit(GamePlayPresenter gpp) {
        // Nothing to submit
        gpp.setState(new InactiveState());
        return;
    }

    @Override
    public void cancel(GamePlayPresenter gpp) {
        // Nothing to cancel
    }
}
