package com.andcup.hades.hts.web.controller.cps.model;

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
        public String     id;
        @JsonProperty("priority")
        public int     priority;
        @JsonProperty("gamePid")
        public String     gamePid;
        @JsonProperty("sourceId")
        public String     sourceId;
        @JsonProperty("other")
        public String  other;
    }

    public String getId(){
        return String.valueOf(originPackLocalPath.hashCode());
    }

    public String getName(){
        String name = originPackLocalPath;
        String[] names = name.split("/");
        return names[names.length - 1];
    }
}
