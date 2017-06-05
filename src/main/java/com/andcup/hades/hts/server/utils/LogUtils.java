package com.andcup.hades.hts.server.utils;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.R;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/26 18:07.
 * Description:
 */
public class LogUtils {

    public static void info(Class<?> tag, String message){
        LoggerFactory.getLogger(tag).info(tag1() + message);
    }

    public static void error(Class<?> tag, String message){
        LoggerFactory.getLogger(tag).error(tag1() + message);
    }

    private static String tag1(){
        String port = null != Hades.sInstance ? String.valueOf(R.port):"";
        return "["+port+"] - ";
    }
}
