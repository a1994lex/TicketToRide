package com.example.clientapp;

import com.groupryan.shared.models.RouteSegment;

import java.util.HashSet;

/**
 * Created by Daniel on 3/5/2018.
 */

public interface IGameView {
    void drawRoute(String playerColor, HashSet<RouteSegment> routeSegments);
}
