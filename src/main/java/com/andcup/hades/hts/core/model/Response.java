package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/25 10:43.
 * Description:
 */
public class Response {

    @JsonProperty("code")
    int code;
    @JsonProperty("message")
    String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
