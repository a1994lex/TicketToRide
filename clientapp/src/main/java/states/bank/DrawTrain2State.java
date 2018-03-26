package states.bank;

import android.widget.Toast;

import presenters.BankPresenter;
import states.BankState;

/**
 * Created by alex on 3/22/18.
 */

public class DrawTrain2State implements BankState {
    @Override
    public void chooseWild(BankPresenter bp) {
        return;
    }

    @Override
    public void chooseCard(BankPresenter bp) {
        bp.setState(new InactiveState());
        bp.getGamePlayPresenter().submit();
        bp.endTurn();
        bp.drawTrainCard(bp.getCurDeckIndex());
    }

    @Override
    public void chooseDest(BankPresenter bp) {
        return;
    }

    @Override
    public void cancel(BankPresenter bp) {
        // cannot exit
        bp.callCancelFail();
        return;
    }
}
