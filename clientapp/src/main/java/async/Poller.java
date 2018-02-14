package async;

import java.util.Timer;
import java.util.TimerTask;

public class Poller {

    public Poller() {
    }

    public void poll() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PollerTask pollerTask = new PollerTask();
                pollerTask.execute();
            }
        }, 0, 500);
    }


}
