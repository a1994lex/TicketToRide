package com.groupryan.client;

import com.google.gson.Gson;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.GameListResult;
import com.groupryan.shared.results.LoginResult;

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

    /** Sends an HTTP request to the server and receives a CommandResult. Opens a connection to server,
     *  sends the command in the request body, and receives the HTTP response. Returns the deserialized
     *  CommandResult
     *
     * @param commandName The kind of command that is being sent. Added as request header.
     * @param command The Command object that holds all of the information for executing a command.
     * @return CommandResult that indicates whether the command was executed properly and returns
     * a list of Commands or success or error messages.
     */
    public Object sendCommand(String commandName, ServerCommand command) {
        HttpURLConnection connection = openConnection(EXEC_COMMAND, commandName);
        if (commandName.equals(LOGIN) || commandName.equals(REGISTER)) {
            LoginResult result;
            if (connection != null) {
                sendToServer(connection, command);
                return result = (LoginResult) returnResult(connection, LoginResult.class);
            }
            else {
                result = new LoginResult(); // send result with exception, failed to connect
                return result;
            }
        }
        else {
            if (commandName.equals(GET_GAME_LIST)) {
                GameListResult result;
                if (connection != null) {
                    sendToServer(connection, command);
                    return result = (GameListResult) returnResult(connection, GameListResult.class);
                }
                else {
                    result = new GameListResult(); // send result with exception, failed to connect
                    return result;
                }
            }
            else {
                CommandResult result;
                if (connection != null) {
                    sendToServer(connection, command);
                    return result = (CommandResult) returnResult(connection, CommandResult.class);
                }
                else {
                    result = new CommandResult(); // send result with exception, failed to connect
                    return result;
                }
            }
        }
    }

    /** Sends a HTTP request by calling the sendCommand() method, but returns only the list of
     * commands received from the server.
     *
     * @param commandName The kind of command that is being sent. Added as request header.
     * @param command The Command object that holds all of the information for executing a command.
     * @return CommandResult that indicates whether the command was executed properly and returns
     * a list of Commands or success or error messages.
     */
    public CommandResult sendGetCommands(String commandName, ServerCommand command) {
        CommandResult result = (CommandResult) sendCommand(commandName, command);
        return result;
    }

    /** Turns a Command into a JSON string.
     *
     * @param command The ServerCommand object going to the server.
     * @return A String object.
     */
    private String serializeCommand(ServerCommand command) {
        return gson.toJson(command);
    }


    /** Creates a connection with the server.
     *
     * @param contextIdentifier The URL ending that indicates which web API is called.
     * @param commandName A String that indicates which kind of Command is being sent.
     * @return A connection to the server.
     */
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

    /** Does the actual sending to the server. The ServerCommand is serialized and the HTTP request
     * body is sent.
     *
     * @param connection A connection to the server.
     * @param commandToSend A ServerCommand object being sent to the server.
     */
    private void sendToServer(HttpURLConnection connection, ServerCommand commandToSend) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            String commandString = serializeCommand(commandToSend);
            outputStreamWriter.write(commandString);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Receives a ClientCommand from the server, deserializes it, and returns it.
     *
     * @param connection The connection to the server.
     * @param klass A class that represents the class of the object sent from the server.
     * @return A CommandResult that indicates the success of the command.
     */
    private Object returnResult(HttpURLConnection connection, Class<?> klass) {
        Object result = null;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if(connection.getContentLength() == 0) {
                    System.out.println("Response body was empty");
                } else if(connection.getContentLength() == -1) {    // response body was not empty
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    if (klass.equals(CommandResult.class)) {
                        result = (CommandResult) gson.fromJson(inputStreamReader, klass);
                    }
                    else if (klass.equals(GameListResult.class)) {
                        result = (GameListResult) gson.fromJson(inputStreamReader, klass);
                    }
                    else if (klass.equals(LoginResult.class)) {
                        result = (LoginResult) gson.fromJson(inputStreamReader, klass);
                    }
                }
            } else {
                throw new Exception(String.format("http code %d",	connection.getResponseCode()));
            }
        } catch (Exception e) {   // return result with exception message
            e.printStackTrace();
            if (klass.equals(LoginResult.class)) {
                result = new LoginResult();
            }
            else if (klass.equals(GameListResult.class)) {
                result = new GameListResult();
            }
            else if (klass.equals(CommandResult.class)) {
                result = new CommandResult();
            }
        }
        return result;
    }

    private static final String EXEC_COMMAND = "/executeCommand";  // url for command API
    private static final String SERVER_HOST = "localhost";
    private static final int PORT_NUMBER = 8080;
    private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + PORT_NUMBER;
    private static final String HTTP_POST = "POST";
    private static final String COMMAND_NAME = "CommandName";   // HTTP request header to determine
                                                                // which kind of command is sent
    private static final String REGISTER = "Register";
    private static final String LOGIN = "Login";
    private static final String GET_GAME_LIST = "Get Game List";
}
