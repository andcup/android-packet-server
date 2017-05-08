package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract class Controller<T extends CpsTaskEntity> implements HttpHandler {

    final static Logger logger              = LoggerFactory.getLogger(MqBroker.class);

    public final void handle(HttpExchange httpExchange) throws IOException {
        synchronized (Controller.this){
            ResponseEntity response = onHandle(parse(httpExchange, getModel()));
            onResponse(httpExchange, response);
        }
    }

    private void onResponse(HttpExchange httpExchange, ResponseEntity response){
        String responseBuffer = response.toString();
        logger.info( responseBuffer);
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

    protected abstract ResponseEntity onHandle(T data);
    protected abstract Class<T>       getModel();

    private T parse(HttpExchange httpExchange, Class<T> clazz){
        try {
            InputStreamReader is = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader rd = new BufferedReader(is);
            StringBuilder bodyData = new StringBuilder();
            boolean found = false;
            String line;
            while((line = rd.readLine()) != null){
                int index = line.indexOf('{');
                if(index != -1 && !found){
                    found = true;
                    bodyData.append(line.substring(index));
                }else if(found){
                    bodyData.append(line);
                }
            }
            logger.info(bodyData.toString());
            return JsonConvertTool.toJson(bodyData.toString(), clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String api() {
        RequestMapping mapping = getClass().getAnnotation(RequestMapping.class);
        return mapping.name();
    }

    protected String getIp(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
