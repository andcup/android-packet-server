package com.andcup.hades.hts.controller.cps.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/2 15:16.
 * Description:
 */
public class CpsTaskEntity {

    @JsonProperty("originPackLocalPath")
    String originPackLocalPath;
    @JsonProperty("channelPackRemoteDir")
    String channelPackRemoteDir;
    @JsonProperty("attachData")
    String attachData;
    @JsonProperty("feedbackApiAddress")
    String feedbackApiAddress;
    @JsonProperty("channels")
    List<Channel> channels;
    @JsonProperty("packType")
    String packType = "1";      //默认使用快速打包方式

    public static class Channel {
        @JsonProperty("id")
        int     id;
        @JsonProperty("priority")
        int     priority;
        @JsonProperty("gamePid")
        int     gamePid;
        @JsonProperty("sourceId")
        int     sourceId;
        @JsonProperty("other")
        String  other;
    }
}
