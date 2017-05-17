package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Request;
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
        Map<String, String>  params = RequestParamsParser.parseUrlParams(httpExchange);
        if(invoker.request.method() == Request.Method.GET){
            try {
                invoker.method.invoke(invoker.clazz.newInstance(), RequestParamAdapter.REQUEST.adapter(invoker, params).toArray());
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }else if(invoker.request.method() == Request.Method.POST){

        }
        return null;
    }


}
