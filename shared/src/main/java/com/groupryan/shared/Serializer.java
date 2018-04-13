package com.groupryan.shared;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ${CLASS} in com.groupryan.shared
 *
 * @author Alex POwley
 * @version 1.0 2/9/2018.
 */

public class Serializer {

    private static Serializer _instance = new Serializer();
    private String jsonString;

    public static Serializer instance() {
        if (_instance == null) {
            _instance = new Serializer();
        }
        return _instance;
    }

    private Serializer() {
    }

    private String _encode(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private Object _decode(InputStream is, Class<?> klass) {

        StringBuilder strBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = scanner.readLine()) != null) {
                strBuilder.append(line);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());

        }
        jsonString = strBuilder.toString();

        Gson gson = new Gson();
        Object obj = gson.fromJson(jsonString, klass);
        return obj;
    }

    private Object _decode(JsonElement element, Class<?> klass) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(element, klass);
        return obj;
    }

    public static String encode(Object object) {
        return _instance._encode(object);
    }

    public static Object decode(InputStream is, Class<?> klass) {
        return _instance._decode(is, klass);
    }

    public static Object decode(JsonElement element, Class<?> klass) {
        return _instance._decode(element, klass);
    }
}

