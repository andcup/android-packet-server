package com.andcup.hades.hts.core.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Amos
 * Date : 2017/5/3 14:54.
 * Description:
 */
public class JsonConvertTool {

    static final String UTF8        = "utf-8";
    static final int    BUFFER_SIZE = 8192;

    public static ObjectMapper sObjectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    static {
        sObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return sObjectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> T toJson(String value, JavaType type) throws IOException {
        return sObjectMapper.readValue(value, type);
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

    public static <T> T toJsonByType(File file, JavaType type){
        try {
            InputStream inputStream = new FileInputStream(file);
            String pc = readToString(inputStream);
            return toJson(pc, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toJson(File file, Class<T> clazz){
        try {
            InputStream inputStream = new FileInputStream(file);
            String pc = readToString(inputStream);
            return toJson(pc, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readToString(InputStream is) throws IOException {
        byte[] data = readToByteArray(is);
        return new String(data, UTF8);
    }

    private static byte[] readToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];

        int len;
        while((len = is.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
