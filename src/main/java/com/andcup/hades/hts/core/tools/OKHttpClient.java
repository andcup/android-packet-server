package com.andcup.hades.hts.core.tools;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/8 12:00.
 * Description:
 */
public class OKHttpClient {

    final static Logger logger              = LoggerFactory.getLogger(OKHttpClient.class);

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType XWWW
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    String       host;
    OkHttpClient client;

    public OKHttpClient(String host){
        this.host = host;
        client = new OkHttpClient();
    }

    public String call(String value) throws IOException {
        RequestBody body = RequestBody.create(JSON, value);
        return call(body);
    }

    public String call(Map<String, String> maps) throws IOException {
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key: maps.keySet()) {
            sb.append(key+"="+maps.get(key)+"&");
        }
        String params = sb.toString();
        if(params.endsWith("&")){
            params = params.substring(0, sb.length() - 1);
        }
        RequestBody body = RequestBody.create(XWWW, params);
        host += "?" + sb.toString();
        return call(body);
    }

    private String call(RequestBody body ) throws IOException {
        logger.info(host);
        Request request = new Request.Builder().url(host).post(body).build();
        okhttp3.Response response = client.newCall(request).execute();
        String result = response.body().string();
        logger.info(result);
        return result;
    }
}
