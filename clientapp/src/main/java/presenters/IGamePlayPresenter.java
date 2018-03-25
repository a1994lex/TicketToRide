package presenters;

import android.app.Activity;

import com.example.clientapp.IGameView;

public interface IGamePlayPresenter {
    void claimRoute(String playerColor, int routeId);
    void clickClaimRoute();
    void clickDrawCard();
    void setGameActivity(IGameView gameView);
    void setUpIfFirst();
    void redrawRoutes();
    void cancel();
    void submit();

}