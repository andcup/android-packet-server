package com.andcup.hades.hts;

import com.andcup.hades.hts.core.zip.ZipProcessor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/6/2 15:55.
 * Description:
 */
public class R {

    /**
     * 配置文件路径.
     * */
    public static String CONFIG = "config.json";
    /**
     * 资源文件路径
     * */
    public static String RESOURCE = "./r/";

    /**
     * 生成模板路径.
     * */
    public static String TEMPLATE = RESOURCE + "template/";

    /**
     * apkTool 路径.
     * */
    public static String APK_TOOL = RESOURCE + "tools/";

    @JsonProperty("apkTool")
    String apkTool;
    @JsonProperty("apkSignKeyPath")
    String apkSignKeyPath;

    public String getApkSignKeyPath() {
        return apkSignKeyPath;
    }

    public String getAlias(){
        String name = new File(apkSignKeyPath).getName().replace(".jks", "");
        return name.split("_")[0];
    }

    public String getPassword(){
        String name = new File(apkSignKeyPath).getName().replace(".jks", "");
        return name.split("_")[1];
    }

    public String getApkTool() {
        return apkTool;
    }

    public static void prepare(){
        String jarPath = F.class.getClassLoader().getResource("").getFile();
        if(!new File(CONFIG).exists()){
            //配置文件不存在.
            ZipProcessor.PREPARE.onProcessor(jarPath, CONFIG, CONFIG);
        }
        if(!new File(APK_TOOL).exists() || !new File(TEMPLATE).exists()){
            //签名文件不存在.
            ZipProcessor.PREPARE.onProcessor(jarPath, "./temp/", "r/");
        }
    }
}
