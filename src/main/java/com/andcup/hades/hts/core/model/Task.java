package com.andcup.hades.hts.core.model;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.tools.MakeDirTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

public class Task {

    public static final int TYPE_QUICK   = 1;
    public static final int TYPE_COMPILE = 0;

    public Task(){}

    /**
     * 同一个母包GroupId 一致. ID.
     */
    @JsonProperty("groupId")
    public String groupId;
    /**
     * 任务ID.
     */
    @JsonProperty("channelId")
    public String id;

    /**
     * 任务名称.
     */
    @JsonProperty("name")
    public String name;
    /**
     * 母包路径.
     */
    @JsonProperty("sourcePath")
    public String sourcePath;
    /**
     * 母包MD5
     * */
    @JsonProperty("md5")
    public String md5;
    /**
     * 子包存储路径.
     */
    @JsonProperty("channelPath")
    public String channelPath;
    /**
     * 写入的数据.
     */
    @JsonProperty("other")
    public String other;
    /**
     * 来源ID.
     */
    @JsonProperty("sourceId")
    public String sourceId;
    /**
     * 打包类型.
     */
    @JsonProperty("type")
    public int type = TYPE_COMPILE;
    /**
     * 优先级.
     */
    @JsonProperty("priority")
    public int priority;
    /**
     * 接口反馈地址.
     */
    @JsonProperty("feedback")
    public String feedback;

    public static class Helper{
        /**
         * 每个APK包的工作路径.
         * */
        private static String getWorkDir(Task task){
            String dir = HadesRootConfig.sInstance.getApkTempDir() + task.name + "_" + task.md5;
            MakeDirTool.mkdir(dir);
            return  dir;
        }

        /**
         * 每个APK包的下载路径.
         * */
        public static String getApkPath(Task task){
            return getWorkDir(task) + "/" + task.name + ".apk";
        }

        /**
         * 获取渠道包路径.
         * */
        public static String getChannelPath(Task task){
            return getWorkDir(task) + "/" + new File(task.channelPath).getName();
        }

        public static String getRulePath(Task task){
            return getWorkDir(task) + "/" + getRule(task);
        }

        /**
         * 存储的manifest.xml
         * */
        public static String getAndroidManifest(Task task){
            return getWorkDir(task) + "/" + "AndroidManifest.xml";
        }

        /**
         * 下载存储文件.
         * */
        public static String getDownloadConfig(Task task){
            return getWorkDir(task) + "/" + task.name + ".json";
        }

        /**
         * 获取写入的文件.
         * */
        private static String getRule(Task task) {
            return String.format(task.type == Task.TYPE_QUICK ? RULE_QUICK : RULE_COMPILE, task.id, task.sourceId, task.other);
        }
    }

    static String RULE_QUICK = "yl_introduction_%s_sourceid_%s_other_%s";

    static String RULE_COMPILE = "<meta-data\n" +
            "            android:name=\"introduction\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"sourceid\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"other\"\n" +
            "            android:value=\"%s\" />";
}
