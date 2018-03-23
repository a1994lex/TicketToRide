package states;

import presenters.BankPresenter;

/**
 * Created by alex on 3/22/18.
 */

public interface BankState {
    void chooseWild(BankPresenter bp);
    void chooseCard(BankPresenter bp);
    void chooseDest(BankPresenter bp);
    void cancel(BankPresenter bp);
}
