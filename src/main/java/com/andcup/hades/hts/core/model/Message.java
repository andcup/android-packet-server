package com.andcup.hades.hts.core.model;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

public class Message {

    public static final int TYPE_QUICK   = 1;
    public static final int TYPE_COMPILE = 0;

    /**消息ID.*/
    public String  id;
    /** 母包路径.*/
    public String  sourcePath;
    /** 子包存储路径.*/
    public String  channleDir;
    /** 写入的数据.*/
    public String rule;
    /** 打包类型.*/
    public int    type = TYPE_COMPILE;
    /** 优先级. */
    public int    priority;


}
