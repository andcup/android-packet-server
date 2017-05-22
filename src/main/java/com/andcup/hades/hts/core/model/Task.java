package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

public class Task {

    public static final int TYPE_QUICK = 1;
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
    @JsonProperty("channelDir")
    public String channelDir;
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
    /**
     * 当前消息产生的文件路径
     */
    @JsonProperty("localDir")
    public String localDir;

    public String getRule() {
        return String.format(type == Task.TYPE_QUICK ? RULE_QUICK : RULE_COMPILE, id, sourceId, other);
    }

    String RULE_QUICK = "yl_introduction_%s_sourceid_%s_other_%s";

    String RULE_COMPILE = "<meta-data\n" +
            "            android:name=\"introduction\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"sourceid\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"other\"\n" +
            "            android:value=\"%s\" />";
}
