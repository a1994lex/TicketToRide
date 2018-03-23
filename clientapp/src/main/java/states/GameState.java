package states;

/**
 * Created by alex on 3/22/18.
 */

public interface GameState {
    void clickDrawCard();
    void clickClaimRoute();
    void submit();
    void cancel();
}
