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
        String sentence=httpExchange.getRequestURI().toString();
        CommandResult cr=new CommandResult();
        Gson g=new Gson();
        String s="";
        CommandResult response=null;
        /**
         * This next if should be replaces by deserializing everything and then
         * passing it to the mainfacade
         */

        try {
            InputStream is=httpExchange.getRequestBody();
            InputStreamReader reader=new InputStreamReader(is);
            ServerCommand command= (ServerCommand)Serializer.decode(is, ServerCommand.class);
            response=command.execute();
//            response=g.toJson(new ReturnResult(StringProcessor.getInstance().toLowerCase((g.fromJson(reader, LowerCaseRequest.class)).getSentence())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //MainFacade mf=new MainFacade();


        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os=httpExchange.getResponseBody();
        OutputStreamWriter osw=new OutputStreamWriter(os);

        osw.write(g.toJson(response));
        osw.close();
        os.close();
    }
}