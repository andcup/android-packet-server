package com.andcup.hades.hts.config;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.MakeDirTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/6/7 14:07.
 * Description:
 */
public class WARConfigure {

    @JsonProperty("r")
    public R r;
    @JsonProperty("f")
    public F f;

    private WARConfigure(){

    }

    public static WARConfigure load(String port){
        /**
         * 设置端口.
         * */
        R.port = Integer.valueOf(port);
        /**
         * 准备工作.
         * */
        F.prepare(port);
        R.prepare();
        /**
         * 初始化下载路径、工作路径.
         * */
        MakeDirTool.mkdir(F.WORK);
        /**
         * 日志文件路径.
         * */
        MakeDirTool.mkdir(F.LOG);
        /**
         * 创建缓存日志.
         * */
        MakeDirTool.mkdirByPath(F.CACHE);
        /**
         * 创建模板文件路径.
         * */
        MakeDirTool.mkdirByPath(R.TEMPLATE);
        /**
         * 创建tools 路径.
         * */
        MakeDirTool.mkdirByPath(R.APK_TOOL);

        /**
         * 转换配置文件.
         * */
        return JsonConvertTool.toJson(new File(R.CONFIG), WARConfigure.class);
    }
}
