package com.groupryan.shared.commands;

import com.groupryan.shared.results.CommandResult;

import java.lang.reflect.Method;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommand implements IServerCommand {

    private String _className;
    private String _methodName;
    private Class<?>[] _paramTypes;
    private Object[] _paramValues;

    public ServerCommand(String className, String methodName,
                         Class<?>[] paramTypes, Object[] paramValues){
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
    }

    @Override
    public CommandResult execute() {
        CommandResult r = new CommandResult();
        try {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, _paramTypes);
            r = (CommandResult)method.invoke(receiver , _paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
