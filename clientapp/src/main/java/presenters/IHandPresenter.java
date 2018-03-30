package presenters;

import android.view.View;

import com.example.clientapp.IHandView;
import com.groupryan.shared.models.DestCard;
import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.TrainCard;

import java.util.List;
import java.util.Map;

public interface IHandPresenter {
    boolean claimingRoute();
    void setView(IHandView handView, View view);
    String decrementTextViewCount(String count);
    List<Integer> findTrainCardByColor(List<TrainCard> trainCards, String color, int numberOfCards);
    List<Integer> getDiscardingTrainCards(Map<String, Integer> pickedCards);
    void setShowRoutes(boolean showRoutes);
    Player getMyPlayer();
    List<DestCard> getMyDestCards();
}
