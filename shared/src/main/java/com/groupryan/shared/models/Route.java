package com.groupryan.shared.models;

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
//todo make maptoobject
    public Route(int length, String one, String two, int worth, String color, int id){
        this.length=length;
        cityOne=one;
        cityTwo=two;
        this.worth=worth;
        this.color=color;
        this.id=id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCityOne() {
        return cityOne;
    }

    public void setCityOne(String cityOne) {
        this.cityOne = cityOne;
    }

    public String getCityTwo() {
        return cityTwo;
    }

    public void setCityTwo(String cityTwo) {
        this.cityTwo = cityTwo;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
