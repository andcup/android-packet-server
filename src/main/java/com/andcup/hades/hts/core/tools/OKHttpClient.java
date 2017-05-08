package com.andcup.hades.hts.core.tools;


import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/5/8 12:00.
 * Description:
 */
public class OKHttpClient {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    String       host;
    OkHttpClient client;

    public OKHttpClient(String host){
        this.host = host;
        client = new OkHttpClient();
    }

    public <T> ResponseEntity<T> call(String bodyString, Class<ResponseEntity<T>> clazz){
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder().url(host).post(body).build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
            String result = response.body().string();
            return JsonConvertTool.toJson(result, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
