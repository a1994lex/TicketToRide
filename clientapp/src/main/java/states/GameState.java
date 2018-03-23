package states;

import presenters.GamePlayPresenter;

/**
 * Created by alex on 3/22/18.
 */

public interface GameState {
    void clickDrawCard(GamePlayPresenter gpp);
    void clickClaimRoute(GamePlayPresenter gpp);
    void submit(GamePlayPresenter gpp);
    void cancel(GamePlayPresenter gpp);
}
