package com.andcup.hades.hts.boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/7/25.
 */
public class FileSyncModel {

    @JsonProperty("localFilePath")
    public String localFilePath;
    @JsonProperty("remoteFilePath")
    public String remoteFilePath;
    @JsonProperty("feedbackApiAddress")
    public String feedbackApiAddress;
    @JsonProperty("attachData")
    public String attachData;
    @JsonProperty("deleteLocal")
    public boolean deleteLocal;
    @JsonProperty("priority")
    public int     priority;

    public boolean equals(FileSyncModel obj) {
        return this.localFilePath.equals(obj.localFilePath);
    }
}
