package com.groupryan.shared.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by bengu3 on 1/31/18.
 */


public class ClientCommand implements IClientCommand {

    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private Object[] _paramValues;

    public ClientCommand(String className, String methodName,
                         String[] paramTypes, Object[] paramValues){
        _className = className;
        _methodName = methodName;
        _paramTypes = paramTypes;
        _paramValues = paramValues;
    }

    private Class<?>[] getClassParamTypes(){
        ArrayList<Class> types = new ArrayList<>();
        try {
            for (int i = 0; i < _paramTypes.length; i++) {
                types.add(Class.forName(_paramTypes[i]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return types.toArray(new Class[types.size()]);
    }

    @Override
    public void execute() {
        try {
            Class<?> receiver = Class.forName(_className);
            Method method = receiver.getMethod(_methodName, getClassParamTypes());
            method.invoke(receiver, _paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
