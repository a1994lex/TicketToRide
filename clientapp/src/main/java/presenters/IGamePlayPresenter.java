package presenters;

import android.app.Activity;

import com.example.clientapp.IClaimRouteView;
import com.example.clientapp.IGameView;

public interface IGamePlayPresenter {
    void claimRoute(int routeId);
    void clickClaimRoute();
    void clickDrawCard();
    void setGameView(IGameView gameView);
    void setUpIfFirst();
    void redrawRoutes();
    void cancel();
    void submit();

}