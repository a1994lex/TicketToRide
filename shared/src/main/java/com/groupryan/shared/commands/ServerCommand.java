package com.groupryan.shared.commands;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
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
        try {
            Class<?> receiver = Class.forName(_className);
            Class<?>[] types = getClassParamTypes();
            Gson g=new Gson();
            ArrayList<Object> objects = getObjects(types, _paramValues);
            Object[] objectParamVals = objects.toArray(new Object[objects.size()]);
            Method method = receiver.getMethod(_methodName, types);
            r = (CommandResult)method.invoke(receiver.newInstance() , objectParamVals);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    private ArrayList<Object> getObjects(Class<?>[] types, Object[] paramValues){
        ArrayList<Object> objects = new ArrayList<>();
        for (int i=0;i<types.length; i++){
            Class<?> receiver = types[i];
            try {
                Method method = receiver.getMethod("mapToObject", LinkedTreeMap.class);
                objects.add(method.invoke(receiver,paramValues[i]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objects;

    }
}
