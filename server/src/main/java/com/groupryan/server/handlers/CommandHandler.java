package com.groupryan.server.handlers;

import com.google.gson.Gson;
import com.groupryan.server.ServerCommand;
import com.groupryan.shared.Serializer;
import com.groupryan.shared.results.CommandResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


/**
 * Created by arctu_000 on 1/31/2018.
 */

public class CommandHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //System.out.println("Entering CommandHandler...");
        CommandResult commandResult = null;
        try {
            InputStream is = httpExchange.getRequestBody();
            InputStreamReader reader = new InputStreamReader(is);
            ServerCommand command = (ServerCommand) Serializer.decode(is, ServerCommand.class);
            commandResult = command.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: check if error and send ResponseHeaders back with HttpURLConnection.HTTP_BAD_REQUEST
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(Serializer.encode(commandResult));
        osw.close();
        os.close();
    }
}