package com.andcup.hades.hts.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/6/2 16:12.
 * Description:
 */
public class F {
    /**
     * 临时文件工作路径.
     * */
    public static String TEMP = "../temp/";
    /**
     * 未完成任务配置.
     * */
    public static String CACHE = TEMP + "cache/message_undo.db";

    /**
     * 日志保存文件路径.
     * */
    public static String LOG = TEMP + "logs/";
    /**
     * 工作路径.
     * */
    public static String WORK = TEMP + "work/";

    public static void prepare(String port){
        F.WORK = F.WORK + port + "/";
    }

    @JsonProperty("from")
    public Server from;
    @JsonProperty("to")
    public Server to;


    public static class Server {
        @JsonProperty("username")
        public String username;
        @JsonProperty("password")
        public String password;
        @JsonProperty("url")
        public String url;
        @JsonProperty("port")
        public int port;
    }
}
