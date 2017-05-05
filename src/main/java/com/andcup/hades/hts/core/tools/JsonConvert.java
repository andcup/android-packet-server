package com.andcup.hades.hts.core.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.nio.charset.Charset;

/**
 * Created by Amos
 * Date : 2017/5/3 14:54.
 * Description:
 */
public class JsonConvert {

    public static ObjectMapper sObjectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    static {
        sObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return sObjectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> T toJson(String value, JavaType type){
        try {
            return sObjectMapper.readValue(value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T toJson(String value, Class<T> clazz){
        try {
            return sObjectMapper.readValue(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String toString(T t){
        try {
            return sObjectMapper.writeValueAsString(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String formatString(T t){
        try {
            byte[] bytes = sObjectMapper.writeValueAsBytes(t);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
