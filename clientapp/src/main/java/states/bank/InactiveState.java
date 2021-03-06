package states.bank;

import presenters.BankPresenter;
import states.BankState;

/**
 * Created by alex on 3/22/18.
 */

public class InactiveState implements BankState {
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
        return;
    }

    @Override
    public void cancel(BankPresenter bp) {
        bp.getBankView().finish();
        return;
    }
}
