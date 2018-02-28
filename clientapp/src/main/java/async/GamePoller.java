package async;

import com.groupryan.client.models.RootClientModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alex on 2/26/18.
 */

public class GamePoller {
    private String gameId;
    private String playerId = RootClientModel.getUser().getUsername();
    public GamePoller(String gameId) {
        this.gameId = gameId;
    }

    public void poll() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GamePollerTask gamePollerTask = new GamePollerTask(gameId, playerId);
                gamePollerTask.execute();
            }
        }, 0, 500);
    }

}
