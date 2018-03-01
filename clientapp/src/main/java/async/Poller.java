package async;

import java.util.Timer;
import java.util.TimerTask;

public class Poller {

    private Timer timer;
    private static Poller instance;

    private Poller() {
        this.timer = new Timer(true);
    }

    public static Poller get() {
        if (instance == null) {
            instance = new Poller();
        }
        return instance;
    }

    public void poll() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PollerTask pollerTask = new PollerTask();
                pollerTask.execute();
            }
        }, 0, 500);
    }

    public void stop() {
        this.timer.cancel();
    }


}
