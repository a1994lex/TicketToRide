package com.groupryan.shared.results;

import com.groupryan.shared.commands.ClientCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class CommandResult {

    private String exceptionType;
    private String exceptionMessage;
    private List<ClientCommand> clientCommands = new ArrayList();
    private String resultType;

    public CommandResult(String exceptionType, String exceptionMessage, List<ClientCommand> clientCommands, String resultType) {
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionMessage;
        this.clientCommands = clientCommands;
        this.resultType = resultType;
    }

    public CommandResult(){}

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public List<ClientCommand> getClientCommands() {
        return clientCommands;
    }

    public void setClientCommands(List<ClientCommand> clientCommands) {
        this.clientCommands = clientCommands;
    }

    public void addClientCommand(ClientCommand clientCommand){
        clientCommands.add(clientCommand);
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
