package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Request;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/10 18:37.
 * Description:
 */
class HadesHttpMappingHandler implements HttpHandler {

    final static Logger sLogger = LoggerFactory.getLogger(HadesHttpMappingHandler.class);

    final String CONTENT_TYPE_JSON = "application/json";
    final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    final String CONTENT_TYPE = "Content-type";

    Map<String, RequestInvoker> methodMap;

    public HadesHttpMappingHandler(String controllerPackageName){
        methodMap = new HadesAnnotationLoader().loadMethod(controllerPackageName);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HadesHttpResponse result = new HadesHttpResponse();
        try {
            result = invoke(httpExchange);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            /**
             * HTTP应答.
             * */
            try {
                byte[] data = result.message.getBytes();
                httpExchange.sendResponseHeaders(result.code, data.length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(data);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HadesHttpResponse invoke(HttpExchange httpExchange) throws UnsupportedEncodingException, InvocationTargetException, IllegalAccessException {
        String path = httpExchange.getRequestURI().getPath();
        sLogger.info(path);
        Headers headers = httpExchange.getRequestHeaders();
        //找到对应的method.
        RequestInvoker invoker = methodMap.get(path);
        List<Object> values = RequestParamAdapter.PARAM.adapter(invoker, httpExchange);
        if(invoker.request.method() == Request.Method.POST && headers.get(CONTENT_TYPE).contains(CONTENT_TYPE_JSON)){
            values = RequestParamAdapter.BODY_APP_JSON.adapter(invoker, httpExchange);
        }else if(invoker.request.method() == Request.Method.POST && headers.get(CONTENT_TYPE).contains(CONTENT_TYPE_FORM_URL_ENCODED)){
            values = RequestParamAdapter.XWWW.adapter(invoker, httpExchange);
        }
        try {
            return (HadesHttpResponse) invoker.method.invoke(invoker.clazz.newInstance(), values.toArray());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return new HadesHttpResponse();
    }
}
