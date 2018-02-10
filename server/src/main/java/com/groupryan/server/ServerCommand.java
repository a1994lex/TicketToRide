package com.groupryan.server;

import com.groupryan.server.facades.MainFacade;
import com.groupryan.shared.commands.IServerCommand;
import com.groupryan.shared.models.User;
import com.groupryan.shared.results.CommandResult;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by bengu3 on 1/31/18.
 */

public class ServerCommand implements IServerCommand {
    private String _className;
    private String _methodName;
    private String[] _paramTypes;
    private Object[] _paramValues;

    public ServerCommand(String className, String methodName,
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
    public CommandResult execute() {
        CommandResult r = new CommandResult();
        System.out.print(_paramValues.length);
        System.out.print("yolo");
        //System.out.print(_paramValues);
        //System.out.print((User)_paramValues);
        //System.out.print(_paramValues.getClass());
        System.out.println("yolo");
        try {
            Class<?> receiver = Class.forName(_className);
            Class<?>[] types = getClassParamTypes();
            Method method = receiver.getMethod(_methodName, types);
            r = (CommandResult)method.invoke(receiver.newInstance(), _paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
