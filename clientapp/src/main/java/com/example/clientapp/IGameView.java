package com.example.clientapp;

import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.RouteSegment;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Daniel on 3/5/2018.
 */

public interface IGameView {
    void drawRoute(String playerColor, HashSet<RouteSegment> routeSegments);
    void cardsDiscarded();
    void showClaimRouteModal();
    void spendTrainCards();
    void goToDrawDestActivity();
}
