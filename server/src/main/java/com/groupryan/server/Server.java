package com.groupryan.server;

import com.groupryan.server.handlers.CommandHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run() {
        String portNumber = "8080";
        System.out.println("Initializing HTTP Server on port " + portNumber);
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext("/executeCommand", new CommandHandler());
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        new Server().run();
    }
}
