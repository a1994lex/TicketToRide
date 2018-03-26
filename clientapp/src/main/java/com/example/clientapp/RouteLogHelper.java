package com.example.clientapp;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.logging.Logger;

/**
 * Created by alex on 3/22/18.
 */

public class RouteLogHelper {
    // views for finding the points of the route segments
    private ImageView mapImage;
    // private Button newRouteButton;
    private EditText routeName;
    private Button nameEntry;

    public RouteLogHelper(Activity act){

        // views for finding the points of the route segments
        mapImage = act.findViewById(R.id.map_button);
        //newRouteButton = findViewById(R.id.new_route);
//        routeName = act.findViewById(R.id.route_name_entry);
//        nameEntry = act.findViewById(R.id.name_entry_button);
        Logger logger = Logger.getLogger("MyLog");

//        game.addObserver(this); // why is this here!!! AHHHH


        mapImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    // the following statement is used to log any messages
                    logger.severe("x value: " + String.valueOf(event.getX()) +
                            " y value: " + String.valueOf(event.getY()) + "\n");
//                    System.out.println("x value: " + String.valueOf(event.getX()) +
//                            " y value: " + String.valueOf(event.getY()) + "\n");
                }
                return true;
            }
        });

        nameEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(routeName.getText().toString());
                logger.severe(routeName.getText().toString());
            }
        });
    }
}
