package com.groupryan.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupryan.dbplugin.IDatabase;
import com.groupryan.server.handlers.CommandHandler;
import com.groupryan.server.plugin_helps.PluginEntry;
import com.groupryan.shared.utils;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;

import com.groupryan.shared.Serializer;


public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run(String DBType, Integer numCommandsToStore) {

        this.loadDBPlugin(DBType, numCommandsToStore);

        int portNumber = utils.PORT_NUMBER;
        System.out.println("Initializing HTTP Server on port " + portNumber);
        try {
            server = HttpServer.create(
                    new InetSocketAddress(portNumber),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext(utils.EXEC_COMMAND, new CommandHandler());
        server.start();
        System.out.println("Server started");
    }

    private void loadDBPlugin(String DBType, Integer maxCommands) {
        try {
            Reader fileReader = new FileReader(new File(utils.PLUGIN_REGISTRY_PATH));

            JsonParser parser = new JsonParser();
            JsonObject rootElement = (JsonObject) parser.parse(fileReader);
            JsonObject pluginRegistryObject = (JsonObject) rootElement.get("plugin_registry");
            JsonElement pluginElement = pluginRegistryObject.get(DBType);

            PluginEntry pluginEntry = (PluginEntry) Serializer.decode(pluginElement, PluginEntry.class);

            // Getting the jar URL which contains target class
            URL[] classLoaderUrls = new URL[]{new URL(pluginEntry.getFilePath())};

            // Create a new URLClassLoader
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            try {
                // Load the target class
                Class<?> pluginClass = urlClassLoader.loadClass(pluginEntry.getClassPath());

                // Create a new instance from the loaded class
                Constructor<?> constructor = pluginClass.getConstructor();
                Object pluginObject = constructor.newInstance();
                IDatabase databasePlugin = (IDatabase) pluginObject;
                DatabaseHolder.getInstance().setDatabase(databasePlugin);

            } catch (Exception ex) {
                System.err.println(ex.getClass());
                System.err.println(ex.getMessage());
            }
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            new Server().run(args[0], Integer.getInteger(args[1]));
        } else {
            System.err.println("Required plugin command-line arguments.");
        }
    }

}
