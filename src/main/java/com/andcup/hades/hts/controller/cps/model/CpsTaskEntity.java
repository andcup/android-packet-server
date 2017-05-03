package com.andcup.hades.hts.controller.cps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/2 15:16.
 * Description:
 */
public class CpsTaskEntity {

    @JsonProperty("originPackLocalPath")
    public String originPackLocalPath;
    @JsonProperty("channelPackRemoteDir")
    public String channelPackRemoteDir;
    @JsonProperty("attachData")
    public String attachData;
    @JsonProperty("feedbackApiAddress")
    public String feedbackApiAddress;
    @JsonProperty("channels")
    public List<Channel> channels;
    @JsonProperty("packType")
    public String packType = "1";      //默认使用快速打包方式

    public static class Channel {
        @JsonProperty("id")
        public int     id;
        @JsonProperty("priority")
        public int     priority;
        @JsonProperty("gamePid")
        public int     gamePid;
        @JsonProperty("sourceId")
        public int     sourceId;
        @JsonProperty("other")
        public String  other;
    }
}
