package com.groupryan.dbplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.shared.*;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.Route;
import com.groupryan.shared.models.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 4/13/2018.
 */

public class GsonTester {

    public class RouteHolder {
        private List<Route> routeList;
        private String name;
        private String id;
        private List<Route> otherList;

        public RouteHolder(List<Route> routes, List<Route> otherRoutes, String name, String id) {
            routeList = routes;
            otherList = otherRoutes;
            this.name = name;
            this.id = id;
        }

        public List<Route> getOtherList() {
            return otherList;
        }

        public List<Route> getRouteList() {
            return routeList;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOtherList(List<Route> otherList) {
            this.otherList = otherList;
        }

        public void setRouteList(List<Route> routeList) {
            this.routeList = routeList;
        }
    }

    public class Blah {
        private Stat stat;
        private String name;
        private int[] array;

        public Blah(Stat stat, String name, int[] array) {
            this.stat = stat;
            this.name = name;
            this.array = array;
        }

        public String getName() {
            return name;
        }

        public int[] getArray() {
            return array;
        }

        public Stat getStat() {
            return stat;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setArray(int[] array) {
            this.array = array;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }
    }

    public void run() {
        List<Route> routeList = new ArrayList<>();
        routeList.add(new Route(2,"provo", "logan", 2, utils.GREEN, 101));
        routeList.add(new Route(5, "panguitch", "cedar city", 15, utils.BLUE, 102));
        routeList.add(new Route(3, "slc", "ogden", 6, utils.PINK, 103));
        List<Route> otherList = new ArrayList<>();
        otherList.add(new Route(5, "midway", "provo", 15, utils.RED, 104));
        otherList.add(new Route(4, "denver", "boulder", 10, utils.BLACK, 105));
        String name = "thingamajig";
        String id = "102942849858353e5346gdsg";
        RouteHolder routeHolder = new RouteHolder(routeList, otherList, name, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(routeHolder);
        JsonElement routeListElement = new JsonParser().parse(json);
        JsonObject rLobject = new JsonObject();
        rLobject.add("routeHolder", routeListElement);
        int[] array = {1,2,3,4,5,6,7,8,9,0};
        Blah blah = new Blah(new Stat("daniel", 3, 9001, 4, 12, 3),
                                        "Daniel", array);
        String blahJson = gson.toJson(blah);
        JsonElement blahElement = new JsonParser().parse(blahJson);
        JsonObject blahObject = blahElement.getAsJsonObject();
        System.out.println("testing: " + gson.toJson(blahObject));
        rLobject.add("blah", blahObject);
        JsonElement blahElement2 = rLobject.get("blah");
        JsonObject blahObject2 = blahElement2.getAsJsonObject();
        JsonElement arrayAsElement = blahObject2.get("array");
        JsonArray arrayAsArray = arrayAsElement.getAsJsonArray();
        JsonArray copy = arrayAsArray;
        blahObject.remove("array");
        System.out.println("blah array removed: " + blahObject);
        blahObject.add("array", copy);
        System.out.println("blah array replaced: " + blahObject);
        arrayAsArray.add(33);
        arrayAsArray.add(432);
        arrayAsArray.add(5243);
        Integer val = 9999;
        JsonElement valElem = new JsonParser().parse(val.toString());
        arrayAsArray.set(2, valElem);
        String modifiedBlahArray = gson.toJson(arrayAsArray);
        System.out.println("array from blah: " + modifiedBlahArray);
        List<Integer> intArray = new ArrayList<>();
        intArray.add(9);
        intArray.add(8);
        intArray.add(7);
        intArray.add(6);
        List<String> strArray = new ArrayList<>();
        strArray.add("a");
        strArray.add("b");
        String strArrayStr = gson.toJson(strArray);
        String intArrayStr = gson.toJson(intArray);
        JsonElement strArrayElem = new JsonParser().parse(strArrayStr);
        JsonElement intArrayElem = new JsonParser().parse(intArrayStr);
        rLobject.add("int_array", intArrayElem);
        rLobject.add("int_array", strArrayElem);
        json = gson.toJson(rLobject);
        System.out.println(json);

        Map<String, List<String>> testMap = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        list1.add("abc");
        list1.add("def");
        list1.add("ghi");
        String key = "a";
        testMap.put(key, list1);
        List<String> list2 = new ArrayList<>();
        list2.add("123");
        list2.add("456");
        list2.add("789");
        String key2 = "b";
        testMap.put(key2, list2);
        Map<String, String> users = new HashMap<>();
        users.put("blue", "p1");
        users.put("red", "p2");
        users.put("yellow", "p3");
        Game game = new Game(users, "gameName", "gameId", 2, false);
        byte[] gameBytes = gson.toJson(game).getBytes();
        String gameStr = new String(gameBytes);
        JsonElement element = new JsonParser().parse(gameStr);
        String a = element.toString();
        System.out.println("string: " + a);
        String testMapStr = gson.toJson(testMap);
        JsonElement mapElem = new JsonParser().parse(testMapStr);
        rLobject.add("map", mapElem);
        JsonObject mapObj = rLobject.get("map").getAsJsonObject();
        JsonArray commandsList = mapObj.getAsJsonArray("a");
        JsonElement listElem = new JsonParser().parse(gson.toJson("777"));
        commandsList.add(listElem);
        json = gson.toJson(rLobject);
        System.out.println(json);
    }

    public static void main(String[] args) {
        new GsonTester().run();
    }
}
