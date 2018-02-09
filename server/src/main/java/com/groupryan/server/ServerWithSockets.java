package com.groupryan.server;

import com.groupryan.server.handlers.CommandHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by alex on 2/5/18.
 */

public class ServerWithSockets {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private ServerSocket server;
//    private static final int TIMEOUT = 10000;
    private static final int PORT = 8080;

    public ServerWithSockets() throws IOException {
        server = new ServerSocket(PORT, MAX_WAITING_CONNECTIONS);
//        server.setSoTimeout(TIMEOUT);
    }

    private void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        server.getLocalPort() + "...");
                Socket client = server.accept();

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                InputStreamReader in = new InputStreamReader(client.getInputStream());

                BufferedReader br = new BufferedReader(in);
                String content = br.readLine();
                System.out.println(content);


                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeUTF("Thank you for connecting to " + client.getLocalSocketAddress()
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
