package com.groupryan.shared.results;

import com.groupryan.shared.commands.ClientCommand;

import java.util.List;

/**
 * Created by bengu3 on 1/31/18.
 */

public class LoginResult extends CommandResult{
    /*- Succeeded: Bool
- userMessage: String*/

    private boolean succeeded;
    private String userMessage;

    public LoginResult(String exceptionType, String exceptionMessage, List<ClientCommand> clientCommands, String resultType, boolean succeeded, String userMessage){
        super(exceptionType, exceptionMessage, clientCommands, resultType);
        this.succeeded = succeeded;
        this.userMessage = userMessage;
    }

    public LoginResult(){
        super();
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
