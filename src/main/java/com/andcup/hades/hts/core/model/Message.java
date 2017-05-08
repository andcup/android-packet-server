package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

public class Message<T> {

    public static final int TYPE_QUICK   = 1;
    public static final int TYPE_COMPILE = 0;

    /**消息ID.*/
    @JsonProperty("id")
    public String  id;
    /** 母包路径.*/
    @JsonProperty("sourcePath")
    public String  sourcePath;
    /** 子包存储路径.*/
    @JsonProperty("channleDir")
    public String  channleDir;
    /** 写入的数据.*/
    @JsonProperty("rule")
    public String rule;
    /** 打包类型.*/
    @JsonProperty("type")
    public int    type = TYPE_COMPILE;
    /** 优先级. */
    @JsonProperty("priority")
    public int    priority;
    @JsonProperty("data")
    public T body;
    @JsonProperty("feedback")
    public String feedback;

    /**当前消息产生的文件路径*/
    @JsonProperty("localDir")
    public String localDir;

}
