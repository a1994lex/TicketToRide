package com.groupryan.shared.models;

import com.groupryan.shared.utils;

/**
 * Created by alex on 3/3/18.
 */

public class Chat implements java.io.Serializable {
    private String messsage;
    private String color;

    public Chat(String message, String color) {
        this.messsage = message;
        this.color = color;
    }

    public String getMesssage() {
        return messsage;
    }

    public String getColor() {
        return color;
    }





}
