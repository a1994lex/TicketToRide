package com.groupryan.server.handlers;
        import com.sun.net.httpserver.Headers;
        import com.sun.net.httpserver.HttpExchange;
        import com.sun.net.httpserver.HttpHandler;

        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;

public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Headers headers=httpExchange.getResponseHeaders();
        headers.set("Content-Type", "text/html");

        String link=httpExchange.getRequestURI().toString();
        String fileLocation;
        if(link.equals("/")) {
            fileLocation="HTML/index.html";
        }
        else{
            fileLocation="HTML"+link;
            headers.set("Content-Type", "text/css");
        }
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os=httpExchange.getResponseBody();
        Path p= Paths.get(fileLocation);
        os.write(Files.readAllBytes(p));
        os.close();
    }
}