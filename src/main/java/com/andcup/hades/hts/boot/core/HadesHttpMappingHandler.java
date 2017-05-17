package com.andcup.hades.hts.boot.core;

import com.andcup.hades.hts.boot.core.bind.Request;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/10 18:37.
 * Description:
 */
public class HadesHttpMappingHandler implements HttpHandler {

    final static Logger sLogger = LoggerFactory.getLogger(HadesHttpMappingHandler.class);

    Map<String, RequestInvoker> methodMap;

    public HadesHttpMappingHandler(String controllerPackageName){
        methodMap = new HadesAnnotationLoader().loadMethod(controllerPackageName);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HadesInvokeResult result = new HadesInvokeResult();
        try {
            result = invoke(httpExchange);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            switch (result.code){
                case 200:
                    break;
                case 404:
                    break;
            }
            onResponse(httpExchange, "ok");
        }
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

    private HadesInvokeResult invoke(HttpExchange httpExchange) throws UnsupportedEncodingException, InvocationTargetException, IllegalAccessException {
        String path = httpExchange.getRequestURI().getPath();
        sLogger.info(path);
        //找到对应的method.
        RequestInvoker invoker = methodMap.get(path);
        if(invoker.request.method() == Request.Method.GET){
            Map<String, String>  params = RequestParamsParser.parseRequestGetParams(httpExchange);
            try {
                invoker.method.invoke(invoker.clazz.newInstance(), RequestParamAdapter.adapter(invoker, params).toArray());
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        //invoker.method.invoke(JsonConvertTool.toJson())
        return null;
    }


}
