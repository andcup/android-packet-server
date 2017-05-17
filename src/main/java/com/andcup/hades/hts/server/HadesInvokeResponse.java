package com.andcup.hades.hts.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by Amos
 * Date : 2017/5/17 19:11.
 * Description:
 */
public class HadesInvokeResponse {

    public void response(HttpExchange httpExchange, HadesHttpResponse result){
        onResponse(httpExchange, result.message);
    }

    private void onResponse(HttpExchange httpExchange, String responseBuffer){
        try {
            byte[] data = responseBuffer.getBytes();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, data.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
