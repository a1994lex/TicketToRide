package com.groupryan.client;

import com.groupryan.client.models.RootClientModel;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Poller extends TimerTask {

    public Poller() {
    }

    @Override
    public void run() {
        System.out.println("Poller polling.....");
        ServerProxy serverProxy = ServerProxy.getInstance();
        CommandResult commandResult = serverProxy.getCommands(RootClientModel.getUser());
        List<ClientCommand> commandList = commandResult.getClientCommands();
        ServerProxy.getInstance().executeCommands(commandList);
    }

    public void poll() {
        TimerTask timerTask = new Poller();
        Timer timer = new Timer(true);
        System.out.println("TimerTask started");
        timer.scheduleAtFixedRate(timerTask, 0, 500);
    }
}
