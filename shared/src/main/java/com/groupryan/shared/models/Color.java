package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Daniel on 2/6/2018.
 */

public enum Color {
    RED, GREEN, YELLOW, BLUE, BLACK;
    public static Color mapToObject(LinkedTreeMap map){
        String s="";
        System.out.print(s);
        Color color=Color.valueOf(s);
       return color;
    }

}
