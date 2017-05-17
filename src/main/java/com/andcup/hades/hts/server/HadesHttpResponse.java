package com.andcup.hades.hts.server;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/17 19:29.
 * Description:
 */
public class HadesHttpResponse {

    @JsonProperty("code")
    public int code;

    @JsonProperty("message")
    public String message;
}
