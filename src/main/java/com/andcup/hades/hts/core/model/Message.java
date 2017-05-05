package com.andcup.hades.hts.core.model;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

public class Message {
    /**消息ID.*/
    String  id;
    /** 母包路径.*/
    String  sourcePath;
    /** 子包存储路径.*/
    String  chanaleDir;
    /** 写入的数据.*/
    String rule;
    /** 打包类型.*/
    int    type;
    /** 优先级. */
    int    priority;
}
