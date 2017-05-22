package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/22 11:06.
 * Description:
 */
public class Channel {
    /**
     * 渠道ID.
     */
    @JsonProperty("id")
    String id;
    /**
     * 来源ID.
     */
    @JsonProperty("sourceId")
    String sourceId;
    /**
     * 保留字段
     */
    @JsonProperty("other")
    String other;

    public Channel(String id, String sourceId, String other) {
        this.id = id;
        this.sourceId = sourceId;
        this.other = other;
    }
}
