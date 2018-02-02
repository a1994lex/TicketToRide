package com.groupryan.client;

import com.google.gson.Gson;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.results.CommandResult;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientCommunicator {
    public static ClientCommunicator instance = new ClientCommunicator();

    private static Gson gson = new Gson();

    private ClientCommunicator() {}

    public static ClientCommunicator getInstance() {
        if (instance == null) {
            instance = new ClientCommunicator();
        }
        return instance;
    }

    // I feel like we are going to need a CommandData object that holds the name of the Command
    // so that we can recreate the Command object properly on the server
    public CommandResult sendCommand(String commandName, ServerCommand command) {
        HttpURLConnection connection = openConnection(EXEC_COMMAND, commandName);
        sendToServer(connection, (ServerCommand)command);
        CommandResult result = returnResult(connection, CommandResult.class);
        return result;
    }

    private String serializeCommand(ServerCommand command) {
        return gson.toJson(command);
    }

    private HttpURLConnection openConnection(String contextIdentifier, String commandName) {
        HttpURLConnection result = null;
        try {
            URL url = new URL(URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(HTTP_POST);
            result.setDoOutput(true);
            result.setRequestProperty(COMMAND_NAME, commandName);
            result.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void sendToServer(HttpURLConnection connection, Object objectToSend) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            String commandString = serializeCommand((ServerCommand)objectToSend);
            outputStreamWriter.write(commandString);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CommandResult returnResult(HttpURLConnection connection, Class<?> klass) {
        CommandResult result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //0 means the body response length == 0; it was empty
                if(connection.getContentLength() == 0) {
                    System.out.println("Response body was empty");
                } else if(connection.getContentLength() == -1) {
                    //-1 means the body was not empty but has an unknown amount of information
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    result = (CommandResult)gson.fromJson(inputStreamReader, klass);
                }
            } else {
                throw new Exception(String.format("http code %d",	connection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final String EXEC_COMMAND = "/execcommand";
    private static final String SERVER_HOST = "localhost";
    private static final int PORT_NUMBER = 8080;
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + PORT_NUMBER;
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_NAME = "CommandName";
    private static final String TO_LOWER_COMMAND = "ToLowerCaseCommand";
    private static final String PARSE_INT_COMMAND = "ParseIntegerCommand";
    private static final String TRIM_COMMAND = "TrimCommand";
}
