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
    private Timer timer = new Timer(true);
    private static GamePoller instance;

    private GamePoller(String gameId) {
        this.gameId = gameId;
    }

    public static GamePoller get(String gameId) {
        if (instance == null) {
            instance = new GamePoller(gameId);
        }
        return instance;
    }

    public void poll() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GamePollerTask gamePollerTask = new GamePollerTask(gameId, playerId);
                gamePollerTask.execute();
            }
        }, 0, 500);
    }

    public void stop() {
        this.timer.cancel();
    }

}
