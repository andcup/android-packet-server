package com.andcup.hades.hts.config;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/8 12:13.
 * Description:
 */
public class HadesRootConfig {

    public static HadesRootConfig sInstance;

    public HadesRootConfig(){
    }

    public static void init(){
        sInstance = JsonConvertTool.toJson(new File("config.json"), HadesRootConfig.class);
    }

    /**远程服务器信息配置.*/
    @JsonProperty("remote")
    public Remote remote;
    /**服务器端口配置.*/
    @JsonProperty("port")
    public int    port;
    /**临时存放路径配置.*/
    @JsonProperty("temp")
    String temp = "../temp/";

    public String getApkTempDir() {
        return temp + port + "/apk/";
    }

    public String getLogTempDir() {
        return temp + port + "/log/";
    }

    public static class Remote{
        /** CDN 子包储存位置. */
        @JsonProperty("cdn")
        public Server cdn;
        /** FTP 源文件储存位置. */
        @JsonProperty("ftp")
        public Server ftp;
        /** FTP 文件服务器地址. */
        @JsonProperty("file")
        public Server file;
    }

    public static class Server{
        @JsonProperty("username")
        public String username;
        @JsonProperty("password")
        public String password;
        @JsonProperty("url")
        public String url;
        @JsonProperty("port")
        public int    port;
    }

}
