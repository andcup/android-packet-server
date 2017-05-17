package com.andcup.hades.hts.boot.core;

import com.andcup.hades.hts.boot.core.utils.IOUtils;
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

    public static Map<String, String> parseRequestGetParams(HttpExchange httpExchange) throws UnsupportedEncodingException {
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

    public static String parseRequestPostParams(HttpExchange httpExchange) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        URI requestedUri = httpExchange.getRequestURI();
        return IOUtils.convertStreamToString(httpExchange.getRequestBody());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
