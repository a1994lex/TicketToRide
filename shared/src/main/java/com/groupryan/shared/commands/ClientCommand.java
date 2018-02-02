package com.groupryan.shared.commands;

import java.lang.reflect.Method;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ClientCommand implements IClientCommand {

    private String _className;
    private String _methodName;
    private Class<?>[] _paramTypes;
    private Object[] _paramValues;

    public ClientCommand(String className, String methodName,
                         Class<?>[] paramTypes, Object[] paramValues){
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
    }

    @Override
    public void execute() {
        try {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, _paramTypes);
            //method.invoke(RootClientModel, _paramValues); //TODO: what do i do here
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
