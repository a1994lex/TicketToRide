package states.bank;

import presenters.BankPresenter;
import states.BankState;

/**
 * Created by alex on 3/22/18.
 */

public class DrawDestState implements BankState {
    @Override
    public void chooseWild(BankPresenter bp) {
        return;
    }

    @Override
    public void chooseCard(BankPresenter bp) {
        return;
    }

    @Override
    public void chooseDest(BankPresenter bp) {
        bp.getGamePlayPresenter().submit();
        bp.setState(new InactiveState());
    }

    @Override
    public void cancel(BankPresenter bp) {
        bp.setState(new ActiveState());
        // Destination view will cancel itself
    }

}
