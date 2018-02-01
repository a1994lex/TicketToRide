package com.groupryan.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
        import com.sun.net.httpserver.HttpExchange;

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
      //  ReturnResult lcr=new ReturnResult("Could not get string to lower from Handler");
        Gson g=new Gson();
        String s="";
      //  String response= g.toJson(lcr);
        if(sentence.equals("/lower")) {
            try {
                InputStream is=httpExchange.getRequestBody();
                InputStreamReader reader=new InputStreamReader(is);
         //       response=g.toJson(new ReturnResult(StringProcessor.getInstance().toLowerCase((g.fromJson(reader, LowerCaseRequest.class)).getSentence())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os=httpExchange.getResponseBody();
        OutputStreamWriter osw=new OutputStreamWriter(os);

     //   osw.write(response);
        osw.close();
        os.close();
    }
}