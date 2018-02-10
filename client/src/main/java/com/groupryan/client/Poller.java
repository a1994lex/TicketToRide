package com.groupryan.client;

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
        CommandResult commandResult = serverProxy.getCommands();
        List<ClientCommand> commandList = commandResult.getClientCommands();
        this.executeCommands(commandList);
    }

    private void executeCommands(List<ClientCommand> commandList) {
        for (ClientCommand command : commandList) {
            command.execute();
        }
    }

    public void poll() {
        TimerTask timerTask = new Poller();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 500);
        System.out.println("TimerTask started");
    }
}
