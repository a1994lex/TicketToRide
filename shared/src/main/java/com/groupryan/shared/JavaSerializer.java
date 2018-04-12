package com.groupryan.shared;

import com.groupryan.shared.commands.ServerCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by clairescout on 4/10/18.
 */

public class JavaSerializer {


    private static JavaSerializer instance = new JavaSerializer();

    private JavaSerializer(){}

    public static JavaSerializer getInstance(){
        return instance;
    }

    public byte[] serializeObject(Object o){
        byte[] blob = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(o);
            blob = baos.toByteArray();

        } catch (IOException e) {
                 e.printStackTrace();
        }
        return blob;
    }

    public Object toObject(byte[] stream){
        Object o = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(stream);
             ObjectInputStream ois = new ObjectInputStream(bais);){
            o = ois.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return o;
    }







}
