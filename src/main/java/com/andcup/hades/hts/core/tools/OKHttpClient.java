package com.andcup.hades.hts.core.tools;


import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqBroker;
import com.fasterxml.jackson.databind.JavaType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/5/8 12:00.
 * Description:
 */
public class OKHttpClient {

    final static Logger logger              = LoggerFactory.getLogger(OKHttpClient.class);

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    String       host;
    OkHttpClient client;

    public OKHttpClient(String host){
        this.host = host;
        client = new OkHttpClient();
    }

    public ResponseEntity call(String bodyString){
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder().url(host).post(body).build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.info(result);
            return JsonConvertTool.toJson(result, ResponseEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> ResponseEntity<T> call(String bodyString, JavaType clazz){
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder().url(host).post(body).build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.info(result);
            return JsonConvertTool.toJson(result, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
