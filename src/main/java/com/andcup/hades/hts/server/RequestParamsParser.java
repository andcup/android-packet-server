package com.andcup.hades.hts.server;
import com.andcup.hades.hts.server.utils.IOUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/15 16:12.
 * Description:
 */
class RequestParamsParser {

    /**
     * 解析URL参数.
     * */
    public static Map<String, String> parseUrlParams(HttpExchange httpExchange) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        URI requestedUri = httpExchange.getRequestURI();
        String queryGet  = requestedUri.getRawQuery();
        String query = "";
        if (!isEmpty(queryGet)) {
            query = queryGet;
        }
        if (isEmpty(query)) {
            return map;
        }

        for (String kv : query.split("&")) {
            String[] temp = kv.split("=");
            map.put(temp[0], URLDecoder.decode(temp[1], "utf-8"));
        }
        return map;
    }

    /**
     * 解析头部信息.
     * */
    public static Map<String, String> parseHeader(HttpExchange httpExchange) throws UnsupportedEncodingException {
        return null;
    }

    /**
     * 解析body.
     * */
    public static <T> T parseBody(HttpExchange httpExchange, Class<T> clazz) throws UnsupportedEncodingException {
        String value = IOUtils.convertStreamToString(httpExchange.getRequestBody());
        return JsonConvertTool.toJson(value, clazz);
    }

    /**
     * 判断字符串是否为空.
     * */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
