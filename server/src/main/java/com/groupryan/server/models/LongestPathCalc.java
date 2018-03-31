package com.groupryan.server.models;

import com.groupryan.shared.models.Player;
import com.groupryan.shared.models.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class LongestPathCalc {

    public LongestPathCalc() {

    }

    public ArrayList<Player> getLongestPathInGame(ServerGame game) {
        // Construct map of Player to their Longest Path length
        Map<Player, ArrayList<Integer>> playerToLongestPath = new HashMap<>();
        for (Map.Entry<String, Player> playerEntry : game.getPlayaMap().entrySet()) {
            // Get Longest Path for every possible sourceCity
            ArrayList<Integer> possibleLongestPaths = new ArrayList<>();
            Map<String, ArrayList<Route>> graph = this.constructGraph(playerEntry.getValue());
            for (Route route : playerEntry.getValue().getRoutes()) {
                Integer longestPathLength = this.findLongestPath(route.getCityOne(), graph);
                possibleLongestPaths.add(-1 * longestPathLength);
            }
            playerToLongestPath.put(playerEntry.getValue(), possibleLongestPaths);
        }
        // Find longest path from above map and return that Player object
        Map.Entry<Player, ArrayList<Integer>> maxEntry = null;
        ArrayList<Map.Entry<Player, ArrayList<Integer>>> maxPlayerEntryList = new ArrayList<>();
        for (Map.Entry<Player, ArrayList<Integer>> entry : playerToLongestPath.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                if (maxEntry == null || Collections.max(entry.getValue()) >= Collections.max(maxEntry.getValue())) {
                    maxEntry = entry;
                    maxPlayerEntryList.add(entry);
                }
            }
        }

        ArrayList<Player> maxPlayerList = new ArrayList<>();
        for (Map.Entry<Player, ArrayList<Integer>> entry : maxPlayerEntryList) {
            if (Collections.max(entry.getValue()) == Collections.max(maxEntry.getValue())) {
                maxPlayerList.add(entry.getKey());
            }
        }
        // maxPlayerList contains Player objects with longest path length
        return maxPlayerList;
    }

    private Integer findLongestPath(String srcCity, Map<String, ArrayList<Route>> graph) {

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        myPriorityQueue pq = new myPriorityQueue();

        for (String startCity : graph.keySet()) {
            dist.put(startCity, -10000);
            prev.put(startCity, null);
            pq.addWithPriority(startCity, dist.get(startCity));
        }

        dist.put(srcCity, 0);

        while (!pq.isEmpty()) {
            String city = pq.getMin();
            for (Route route : graph.get(city)) {
                String startCity = route.getCityOne();
                String endCity = route.getCityTwo();
                Integer potentialLength = dist.get(startCity) + route.getLength();
                if (dist.get(endCity) < potentialLength) {
                    dist.put(endCity, potentialLength);
                    prev.put(endCity, startCity);
                    pq.updatePriority(endCity, dist.get(endCity));
                }
            }
        }

        Map.Entry<String, Integer> minEntry = null;
        for (Map.Entry<String, Integer> entry : dist.entrySet()) {
            if ((minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) && !entry.getValue().equals(-10000)) {
                minEntry = entry;
            }
        }

        return minEntry.getValue();
    }

    private Map<String, ArrayList<Route>> constructGraph(Player player) {
        Map<String, ArrayList<Route>> graph = new HashMap<>();
        List<Route> routes = player.getRoutes();
        for (Route route : routes) {
            // convert all route lengths to negative
            route.setLength(-1 * route.getLength());
            String startCity = route.getCityOne();
            if (graph.containsKey(startCity)) {
                ArrayList<Route> edgeList = graph.get(startCity);
                edgeList.add(route);
                graph.put(startCity, edgeList);
            } else {
                ArrayList<Route> edgeList = new ArrayList<>();
                edgeList.add(route);
                graph.put(startCity, edgeList);
            }
            // bidirectional edges
            String endCity = route.getCityTwo();
            if (graph.containsKey(endCity)) {
                ArrayList<Route> edgeList = graph.get(endCity);
                edgeList.add(new Route(route.getLength(), route.getCityTwo(), route.getCityOne(), route.getWorth(), route.getColor(), route.getId()));
                graph.put(endCity, edgeList);
            } else {
                ArrayList<Route> edgeList = new ArrayList<>();
                edgeList.add(new Route(route.getLength(), route.getCityTwo(), route.getCityOne(), route.getWorth(), route.getColor(), route.getId()));
                graph.put(endCity, edgeList);
            }

        }
        return graph;
    }

    private class myPriorityQueue {
        private PriorityQueue<CityVertex> pq;

        private myPriorityQueue() {
            this.pq = new PriorityQueue<>();
        }

        private void addWithPriority(String cityName, int weight) {
            CityVertex v = new CityVertex(cityName, weight);
            this.pq.add(v);
        }

        private void updatePriority(String cityName, int weight) {
            CityVertex v = new CityVertex(cityName, weight);
            this.pq.remove(v);
            this.pq.add(v);
        }

        private String getMin() {
            CityVertex v = this.pq.poll();
            return v.cityName;
        }

        private boolean isEmpty() {
            if (this.pq.size() == 0) {
                return true;
            } else {
                return false;
            }
        }

        private class CityVertex implements Comparable<CityVertex> {

            private String cityName;
            private int weight;

            private CityVertex(String cityName, int weight) {
                this.cityName = cityName;
                this.weight = weight;
            }

            @Override
            public int compareTo(CityVertex v) {
                if (this.weight > v.weight) {
                    return 1;
                } else if (this.weight < v.weight) {
                    return -1;
                } else {
                    return 0;
                }
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                CityVertex that = (CityVertex) o;

                return cityName != null ? cityName.equals(that.cityName) : that.cityName == null;
            }

            @Override
            public int hashCode() {
                return cityName != null ? cityName.hashCode() : 0;
            }
        }
    }
}
