package com.andcup.hades.hts;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.MakeDirTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/8 12:13.
 * Description:
 */
public class HadesRootConfigure {

    public static HadesRootConfigure sInstance;

    public HadesRootConfigure() {
    }

    public static void init(String path) {
        /**
         * 转换配置文件.
         * */
        sInstance = JsonConvertTool.toJson(new File(path), HadesRootConfigure.class);
        /**
         * 初始化keystore.
         * */
        sInstance.keyStore = JsonConvertTool.toJson(new File(sInstance.keyStoreConfig), KeyStore.class);

        sInstance.keyStore.path = sInstance.keyStoreConfig.replace(
                new File(sInstance.keyStoreConfig).getName(),
                sInstance.keyStore.path);
        /**
         * 创建文件夹.
         * */
        MakeDirTool.mkdir(sInstance.getApkTempDir());
        /**
         * 日志文件路径.
         * */
        MakeDirTool.mkdir(sInstance.getLogTempDir());

        MakeDirTool.mkdirByPath(sInstance.db);
    }

    /**
     * 远程服务器信息配置.
     */
    @JsonProperty("remote")
    public Remote remote;
    /**
     * 服务器端口配置.
     */
    @JsonProperty("port")
    public int port;
    /**
     * 签名文件信息.
     */
    @JsonProperty("keyStore")
    public KeyStore keyStore;
    /**
     * 临时存放路径配置.
     */
    @JsonProperty("temp")
    public String temp = "../temp";
    @JsonProperty("keyStorePath")
    String keyStoreConfig = "../tools/youlong.json";
    /**
     * apk工具.
     */
    @JsonProperty("apkTool")
    public String apktool = "../tools/apktool.jar";
    @JsonProperty("db")
    public String db = "db/messageCache.db";

    public String getApkTempDir() {
        return temp + "/apk/" + port + "/";
    }

    public String getLogTempDir() {
        return temp + "/log/" + port;
    }

    public static class Remote {
        /**
         * CDN 子包储存位置.
         */
        @JsonProperty("cdn")
        public Server cdn;
        /**
         * FTP 源文件储存位置.
         */
        @JsonProperty("ftp")
        public Server ftp;
        /**
         * FTP 文件服务器地址.
         */
        @JsonProperty("file")
        public Server file;
    }

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

    public static class KeyStore {
        @JsonProperty("path")
        public String path;
        @JsonProperty("alias")
        public String alias;
        @JsonProperty("pass")
        public String pass;
    }
}
