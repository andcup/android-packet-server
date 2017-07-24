package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/7/20 14:37.
 * Description:
 */
public class OSS {
    @JsonProperty("endpoint")
    public String endpoint;

    @JsonProperty("bucketName")
    public String bucketName;

    @JsonProperty("accessKeyId")
    public String accessKeyId;

    @JsonProperty("accessKeySecret")
    public String accessKeySecret;
}
