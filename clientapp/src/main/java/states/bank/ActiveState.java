package states.bank;

import presenters.BankPresenter;
import states.BankState;

/**
 * Created by alex on 3/22/18.
 */

public class ActiveState implements BankState {
    @Override
    public void chooseWild(BankPresenter bp) {
        bp.setState(new InactiveState());
        bp.drawTrainCard(bp.getCurDeckIndex());
    }

    @Override
    public void chooseCard(BankPresenter bp) {
        bp.setState(new DrawTrain2State());
        bp.drawTrainCard(bp.getCurDeckIndex());
    }

    @Override
    public void chooseDest(BankPresenter bp) {
       // bp.setState(new DrawDestState());
        bp.getGamePlayPresenter().submit();
        bp.setState(new InactiveState());
        //bp.endTurn();
        bp.drawDestinationCards();
    }

    @Override
    public void cancel(BankPresenter bp) {
        bp.setState(new InactiveState());
        bp.getGamePlayPresenter().cancel();
        bp.getBankView().finish();

    }
}
