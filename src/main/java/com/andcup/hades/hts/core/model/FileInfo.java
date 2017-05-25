package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/8 11:59.
 * Description:
 */
public class FileInfo {

    /**
     * 母包路径. 相对于FTP根目录.
     */
    @JsonProperty("filesize")
    public long  fileSize;
    /**
     * 文件最后修改时间. 判断文件是否已经修改.
     */
    @JsonProperty("updateTime")
    public long    lastEditTime;
    /**
     * 文件大小. 判断文件是否已经修改.
     */
    @JsonProperty("md5")
    public String     md5;
}
