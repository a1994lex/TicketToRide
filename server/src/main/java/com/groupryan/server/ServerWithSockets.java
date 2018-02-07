package com.groupryan.server;

import com.groupryan.server.handlers.CommandHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by alex on 2/5/18.
 */

public class ServerWithSockets {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private ServerSocket serverSocket;
    private static final int TIMEOUT = 10000;
    private static final int PORT = 8080;

    public ServerWithSockets() throws IOException {
        serverSocket = new ServerSocket(PORT, MAX_WAITING_CONNECTIONS);
        serverSocket.setSoTimeout(TIMEOUT);
    }

    private void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                System.out.println(in.readUTF());


                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
//        server.setExecutor(null);
//
//        System.out.println("Creating contexts");
//        server.createContext("/executeCommand", new CommandHandler());
//
//        System.out.println("Starting server");
//        server.start();
//        System.out.println("Server started")


    }

    public static void main(String[] args) {
        try {
            new ServerWithSockets().run();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
