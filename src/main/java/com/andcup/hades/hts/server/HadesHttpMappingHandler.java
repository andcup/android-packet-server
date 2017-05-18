package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.utils.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
            new HadesInvokeResponse().response(httpExchange, result);
        }
    }

    private HadesHttpResponse invoke(HttpExchange httpExchange) throws UnsupportedEncodingException, InvocationTargetException, IllegalAccessException {
        String path = httpExchange.getRequestURI().getPath();
        sLogger.info(path);
        //找到对应的method.
        RequestInvoker invoker = methodMap.get(path);
        if(invoker.request.method() == Request.Method.GET){
            try {
                invoker.method.invoke(invoker.clazz.newInstance(), RequestParamAdapter.PARAM.adapter(invoker, httpExchange).toArray());
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }else if(invoker.request.method() == Request.Method.POST){
            Headers headers = httpExchange.getRequestHeaders();
            if(headers.get("Content-type").contains(CONTENT_TYPE_JSON)){
                try {
                    invoker.method.invoke(invoker.clazz.newInstance(), RequestParamAdapter.BODY_APP_JSON.adapter(invoker, httpExchange).toArray());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }else if(headers.get("Content-type").contains(CONTENT_TYPE_FORM_URL_ENCODED)){
                try {
                    invoker.method.invoke(invoker.clazz.newInstance(), RequestParamAdapter.XWWW.adapter(invoker, httpExchange).toArray());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }else{
            }
        }
        return null;
    }
}
