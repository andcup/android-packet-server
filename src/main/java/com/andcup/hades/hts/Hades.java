package com.andcup.hades.hts;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.MakeDirTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/8 12:13.
 * Description:
 * Email:amos@sayboy.com
 * Github: https://github.com/andcup
 */
public class Hades {

    public static Hades sInstance;

    private Hades() {
    }

    public static void init(String port) {
        /**
         * 设置端口.
         * */
        R.port = Integer.valueOf(port);
        /**
         * 准备工作.
         * */
        R.prepare();
        /**
         * 转换配置文件.
         * */
        sInstance = JsonConvertTool.toJson(new File(R.CONFIG), Hades.class);

        /**
         * 初始化下载路径、工作路径.
         * */
        F.WORK_SPACE = F.WORK_SPACE + port + "/";
        MakeDirTool.mkdir(F.WORK_SPACE);
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
    }

    @JsonProperty("r")
    public R r;
    @JsonProperty("f")
    public F f;
}
