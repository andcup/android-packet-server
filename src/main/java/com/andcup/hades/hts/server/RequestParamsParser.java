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
        URI requestedUri = httpExchange.getRequestURI();
        String queryGet  = requestedUri.getRawQuery();
        return parseEqualFormatString(queryGet);
    }

    /**
     *
     * */
    public static <T> T parseApplicationJson(HttpExchange httpExchange, Class<T> clazz) throws UnsupportedEncodingException {
        return null;
    }

    /**
     * 解析body.
     * */
    public static Map<String, String> parseXWWWFormUrlEncoded(HttpExchange httpExchange) throws UnsupportedEncodingException {
        String value = IOUtils.convertStreamToString(httpExchange.getRequestBody());
        return parseEqualFormatString(value);
    }

    private static Map<String, String> parseEqualFormatString(String value) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String query = "";
        if (!isEmpty(value)) {
            query = value;
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
     * 判断字符串是否为空.
     * */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
