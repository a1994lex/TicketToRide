package com.groupryan.client;

import com.google.gson.Gson;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.commands.ServerCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Executor;


/**
 * Created by alex on 2/5/18.
 */

public class ClientSocketCommunicator {
    public static ClientSocketCommunicator instance;
    private Socket client;
    private boolean startRead = false;
    private static Gson gson = new Gson();

    private ClientSocketCommunicator() throws IOException {
        client = new Socket(SERVER, PORT);
    }

    public static ClientSocketCommunicator getInstance() throws IOException{
        if (instance == null) {
            instance = new ClientSocketCommunicator();
        }
        return instance;
    }

    /**Sends a command object to the server via the socket
     * Once the first command is sent from the Client, CommandExecutor spawns a thread which reads
     * input given to the client socket from the server.
     * All ClientCommands will be read from here on out on the thread and executed accordingly.
     * ...I hope this works
     *
     * @param command The ServerCommand object going to the server
     */
    public void write(ServerCommand command){
        try {
            String json = serializeCommand(command);
            OutputStream outToServer = client.getOutputStream();

            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(outToServer, DEFAULT_ENCODING));

            wr.write(HTTP_POST+ " " + PATH + " HTTP/1.0\r\n");
            wr.write("Content-Length: " + json.length() + "\r\n");
            wr.write("Content-Type: application/json\r\n");
            wr.write("\r\n");

            wr.write(json);
            wr.flush();

            if (startRead == false){
                startRead = true;
                new CommandExecutor().execute(new SocketReadTask());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    /** Turns a Command into a JSON string.
     *
     * @param command The ServerCommand object going to the server.
     * @return A String object.
     */
    private String serializeCommand(ServerCommand command) {
        return gson.toJson(command);
    }


    /**
     * The CommandExecutor executes a runnable task called SocketReadTask. This task reads all
     * output from the server socket.
     */
    private class CommandExecutor implements Executor{
        /** Executes a runnable task which runs on a separate thread.
         *
         * @param r
         */
        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }

    /**
     * The SocketReadTask runs when the CommandExecutor calls it. The Task has one method
     * and is responsible fo reading command objects via the ClientSocket sent from the Server.
     */
    private class SocketReadTask implements Runnable{
        /** Run is the method which is called one the thread. It
         *
         */
        @Override
        public void run() {
            try{
                while(true) {
                    InputStream inFromServer = client.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(inFromServer));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    ClientCommand result = gson.fromJson(line, ClientCommand.class);
                    result.execute();
                }

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    private static final int PORT = 8080;
    private static final String DEFAULT_ENCODING= "UTF8";
    private static final String PATH = "/executeCommand";
    private static final String SERVER = "localhost";
    private static final String HTTP_POST = "POST";


}
