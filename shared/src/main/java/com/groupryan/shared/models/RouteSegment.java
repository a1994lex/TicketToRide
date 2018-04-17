package com.groupryan.shared.models;

/**
 * Created by Daniel on 3/6/2018.
 */

public class RouteSegment implements java.io.Serializable {
    private int routeId;
    private float xCoordinateA;
    private float yCoordinateA;
    private float xCoordinateB;
    private float yCoordinateB;

    public RouteSegment(float xCoordinateA, float yCoordinateA, float xCoordinateB,
                        float yCoordinateB, int routeId) {
        this.xCoordinateA = xCoordinateA;
        this.yCoordinateA = yCoordinateA;
        this.xCoordinateB = xCoordinateB;
        this.yCoordinateB = yCoordinateB;
        this.routeId = routeId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public float getxCoordinateA() {
        return xCoordinateA;
    }

    public void setxCoordinateA(float xCoordinateA) {
        this.xCoordinateA = xCoordinateA;
    }

    public float getyCoordinateA() {
        return yCoordinateA;
    }

    public void setyCoordinateA(float yCoordinateA) {
        this.yCoordinateA = yCoordinateA;
    }

    public float getxCoordinateB() {
        return xCoordinateB;
    }

    public void setxCoordinateB(float xCoordinateB) {
        this.xCoordinateB = xCoordinateB;
    }

    public float getyCoordinateB() {
        return yCoordinateB;
    }

    public void setyCoordinateB(float yCoordinateB) {
        this.yCoordinateB = yCoordinateB;
    }

}