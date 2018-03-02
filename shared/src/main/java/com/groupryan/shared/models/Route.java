package com.groupryan.shared.models;

/**
 * Created by arctu on 2/17/2018.
 */

public class Route {
    int length;
    String cityOne;
    String cityTwo;
    String routeId;
    int worth;
    String color;

    public Route(int length, String one, String two, int worth, String color, String routeId){
        this.length=length;
        cityOne=one;
        cityTwo=two;
        this.worth=worth;
        this.color=color;
        this.routeId= routeId;
    }
}
