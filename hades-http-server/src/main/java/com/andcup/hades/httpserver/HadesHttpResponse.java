package com.andcup.hades.httpserver;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/17 19:29.
 * Description:
 */
public class HadesHttpResponse<T> {

    public static final int HTTP_OK =  200;

    @JsonProperty("code")
    public int code = 0;

    @JsonProperty("message")
    public String message = "成功";

    @JsonProperty("body")
    public T body;

    public HadesHttpResponse(){

    }

    public HadesHttpResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public HadesHttpResponse(int code, T t){
        this.code = code;
        this.body = t;
    }
}
