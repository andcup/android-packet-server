package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/8 11:59.
 * Description:
 */
public class FileInfo {

    public final int DOWNLOAD_WAIT   = 0;   /**文件等待下载.*/
    public final int DOWNLOAD_START  = 1;   /**文件正在下载.*/
    public final int DOWNLOAD_FINISH = 2;   /**文件下载完成.*/
    /**
     * 母包路径. 相对于FTP根目录.
     */
    @JsonProperty("originPackLocalPath")
    public String  sourcePath;
    /**
     * 文件最后修改时间. 判断文件是否已经修改.
     */
    @JsonProperty("lastEditTime")
    public long    lastEditTime;
    /**
     * 文件大小. 判断文件是否已经修改.
     */
    @JsonProperty("fileSize")
    public long     fileSize;
    @JsonProperty("downloaded")
    public int      downloaded = DOWNLOAD_WAIT;
    @JsonProperty("hasCompile")
    public boolean  hasCompile = false;
}
