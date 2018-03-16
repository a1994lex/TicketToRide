package com.groupryan.client;

import com.google.gson.Gson;
import com.groupryan.shared.Serializer;
import com.groupryan.shared.commands.*;
import com.groupryan.shared.commands.ClientCommand;
import com.groupryan.shared.results.CommandResult;
import com.groupryan.shared.results.GameListResult;
import com.groupryan.shared.results.LoginResult;
import com.groupryan.shared.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/** The ClientCommunicator class is used by the client to send and receive information from the server.
 * It uses HTTP requests and responses to make these communications. It opens a connection to the
 * server, serializes a ServerCommand object to a JSON string, sends the string to the server,
 * and waits for an HTTP response from the server. */
public class ClientCommunicator {

    /** single instance of the ClientCommunicator */
    public static ClientCommunicator instance = new ClientCommunicator();

    /** static instance of Gson to serialize and deserialize objects */
    private static Gson gson = new Gson();

    /** private constructor for implementing the singleton pattern
     * @pre must be called by the ClientCommunicator class
     * @post instantiates an instance of ClientCommunicator class */
    private ClientCommunicator() {}

    /** Returns an instance of the ClientCommunicator class. Implements the singleton method
     * by returning a static instance of the ClientCommunicator
     * @return a copy of the ClientCommunicator object
     * @pre can be called by any class
     * @post returns an instance of ClientCommunicator class
     */
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
     * @pre commandName must be the string "login", "register", or "getGameList", the command object
     * must be a ServerCommand object
     * @post returns a CommandResult object, LoginResult, or GameListResult
     */
    public Object sendCommand(String commandName, ServerCommand command) {
       HttpURLConnection connection = openConnection(utils.EXEC_COMMAND, commandName);
        if (commandName.equals(utils.LOGIN) || commandName.equals(utils.REGISTER)) {
            LoginResult result;
            if (connection != null) {
                sendToServer(connection, command);
                try {
                    return returnResult(connection, LoginResult.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                result = new LoginResult(utils.FAILED_CONNECTION, utils.FAILED_CONNECTION_MESSAGE,
                        new ArrayList<>(), utils.INVALID); // send result with exception, failed to connect
                return result;
            }
        }
        else {
            if (commandName.equals(utils.GET_GAME_LIST)) {
                return returnGameListResult(connection, command);
            }
            else {
                return returnCommandResult(connection, command);
            }
        }
        return new CommandResult(utils.FAILED_CONNECTION, utils.FAILED_CONNECTION_MESSAGE,
                new ArrayList<>(), utils.INVALID);
    }

    /** This method gets called when a GameListResult object is supposed to be sent back to the
     * UIFacade. Creates a GameListResult object or a CommandResult if there is a problem.
     *
     * @param connection An object that represents a connection to the server.
     * @param command The Command object that holds all of the information for executing a command.
     * @return A CommandResult object representing the results of sending a command to the server.
     * @pre connection must be a valid connection to the server, command must be a ServerCommand
     * @post returns a GameListResult object
     */
    private Object returnGameListResult(HttpURLConnection connection, ServerCommand command) {
        GameListResult result;
        if (connection != null) {
            sendToServer(connection, command);
            try {
                return returnResult(connection, GameListResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // send result with exception, failed to connect
        result = new GameListResult(utils.FAILED_CONNECTION,
                utils.FAILED_CONNECTION_MESSAGE, new ArrayList<>(), utils.INVALID,
                null);
        return result;
    }

    /** This method gets called when a GameListResult object is supposed to be sent back to the
     * UIFacade. Creates a GameListResult object or a CommandResult if there is a problem.
     *
     * @param connection An object that represents a connection to the server.
     * @param command The Command object that holds all of the information for executing a command.
     * @return A CommandResult object representing the results of sending a command to the server.
     * @pre connection must be a valid connection to the server, command must be a ServerCommand
     * @post returns a CommandResult object
     */
    private Object returnCommandResult(HttpURLConnection connection, ServerCommand command) {
        CommandResult result;
        if (connection != null) {
            sendToServer(connection, command);
            try {
                return returnResult(connection, CommandResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // send result with exception, failed to connect
        result = new CommandResult(utils.FAILED_CONNECTION,
                utils.FAILED_CONNECTION_MESSAGE, new ArrayList<>(), utils.INVALID);
        return result;
    }

    /** Turns a ServerCommand object into a JSON string.
     *
     * @param command The ServerCommand object going to the server.
     * @return A String object.
     * @pre command must be a ServerCommand
     * @post returns a JSON String representing a serialized ServerCommand
     */
    private String serializeCommand(ServerCommand command) {
        return Serializer.encode(command);
    }


    /** Creates a connection with the server.
     *
     * @param contextIdentifier The URL ending that indicates which web API is called.
     * @param commandName A String that indicates which kind of Command is being sent.
     * @return A connection to the server.
     * @pre contextIdentifier must be a valid URL ending, commandName must be a valid name for a
     * Command class
     * @post returns a valid connection to the server
     */
    private HttpURLConnection openConnection(String contextIdentifier, String commandName) {
        HttpURLConnection result = null;
        try {
            URL url = new URL(utils.URL_PREFIX + contextIdentifier);
            result = (HttpURLConnection)url.openConnection();
            result.setRequestMethod(utils.HTTP_POST);
            result.setDoOutput(true);
            result.setRequestProperty(utils.COMMAND_NAME, commandName);
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
     * @pre connection must be a valid connection to the server, the commandToSend must be a
     * valid ServerCommand
     * @post a command is sent to the server
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

    /**Receives a ClientCommand from the server, deserializes it, and returns it.
     *
     * @param connection The connection to the server.
     * @param klass A class that represents the class of the object sent from the server.
     * @return A CommandResult that indicates the success of the command.
     * @throws Exception when unable to get the input stream or response code from the
     * HttpURLConnection object
     * @pre connection must be a valid connection to the server, klass is a valid Java class
     * @post returns a GameListResult, LoginResult, or CommandResult object
     */
    private Object returnResult(HttpURLConnection connection, Class<?> klass) throws Exception {
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
        } catch (IOException e) {   // return result with exception message
            e.printStackTrace();
            result = new CommandResult(e.getCause().getClass().toString(), e.getMessage(),
                    new ArrayList<ClientCommand>(), utils.INVALID);
        }
        return result;
    }

}
