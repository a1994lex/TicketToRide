package com.groupryan.shared.models;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by arctu on 2/17/2018.
 */

public class Route {
    int length;
    String cityOne;
    String cityTwo;
    int worth;
    String color;
    int id;
    boolean available;

//todo make maptoobject

    public static Route mapToObject(LinkedTreeMap map) {
        double  len = (double) map.get("length");
        String one = (String) map.get("cityOne");
        String two = (String) map.get("cityTwo");
        double worth = (double) map.get("worth");
        String color = (String) map.get("color");
        double ID = (double) map.get("id");
        boolean available = (boolean) map.get("available");

        return new Route((int)len, one, two, (int)worth, color, (int)ID, available);
    }

    public Route(int length, String one, String two, int worth, String color, int id, boolean available){
        this.length=length;
        cityOne=one;
        cityTwo=two;
        this.worth=worth;
        this.color=color;
        this.id=id;
        this.available = available;
    }

    public Route(int length, String one, String two, int worth, String color, int id){
        this.length=length;
        cityOne=one;
        cityTwo=two;
        this.worth=worth;
        this.color=color;
        this.id=id;
        this.available = true;
    }

    public int getLength() {
        return length;
    }

    public void setColor(String color){
        this.color=color;
    }

    public String getCityOne() {
        return cityOne;
    }

    public String getCityTwo() {
        return cityTwo;
    }

    public int getWorth() {
        return worth;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
  
    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
