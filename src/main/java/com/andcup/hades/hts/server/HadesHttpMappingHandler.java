package com.andcup.hades.hts.server;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.utils.IOUtils;
import com.andcup.hades.hts.server.utils.LogUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/10 18:37.
 * Description:
 */
class HadesHttpMappingHandler implements HttpHandler {


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
                String encodeValue = JsonConvertTool.toString(result);
//                encodeValue = URLEncoder.encode(encodeValue, "UTF-8");
                byte[] data = encodeValue.getBytes();
                httpExchange.sendResponseHeaders(200, data.length);
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
        //找到对应的method.
        RequestInvoker invoker = methodMap.get(path);
        try {
            List<Object> values = null;
            Headers headers = httpExchange.getRequestHeaders();
            LogUtils.info(HadesHttpMappingHandler.class,httpExchange.getRequestURI().toASCIIString() );
            if(invoker.request.method() == Request.Method.POST){
                String contentType = headers.get(CONTENT_TYPE).get(0);
                if(contentType.contains(CONTENT_TYPE_JSON)){
                    values = RequestParamAdapter.BODY_APP_JSON.adapter(invoker, httpExchange);
                }else if(contentType.contains(CONTENT_TYPE_FORM_URL_ENCODED)){
                    values = RequestParamAdapter.XWWW.adapter(invoker, httpExchange);
                }
            }else{
                values = RequestParamAdapter.PARAM.adapter(invoker, httpExchange);
            }
            return (HadesHttpResponse) invoker.method.invoke(invoker.clazz.newInstance(), values.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new HadesHttpResponse(-1, " request param error." + e.getMessage());
        }
    }
}
