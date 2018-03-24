package presenters;

import com.example.clientapp.IGameOverView;
import com.groupryan.shared.models.EndGameStat;

import java.util.List;

/**
 * Created by clairescout on 3/24/18.
 */

public interface IGameOverPresenter {


    void setView(IGameOverView iGameOverView);
    String getWinnerString();
    List<EndGameStat> getEndGameStats();

}
