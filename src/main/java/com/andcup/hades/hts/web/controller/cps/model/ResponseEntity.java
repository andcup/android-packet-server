package com.andcup.hades.hts.web.controller.cps.model;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/3 15:54.
 * Description:
 */
public class ResponseEntity<T> {

    public static final int SUCCESS   = 0;
    public static final int ERR_PARAM = -1;
    public static final int ERR_FILE  = -2;

    /**
     * < 0 失败.
     * >= 0 成功.
     * 错误列表: -1 参数错误.  -2 文件不存在.
     * */
    @JsonProperty("code")
    public int     code;

    /**
     * 失败原因.
     * */
    @JsonProperty("message")
    public String  message;

    /**
     * 服务器地址. 端口号.
     * */
    @JsonProperty("server")
    public String  server;

    @JsonProperty("body")
    public T body;

    public ResponseEntity(){

    }

    public ResponseEntity(int code, String message, String server){
        this.code       = code;
        this.message    = message;
        this.server     = server;
    }

    public ResponseEntity(int code, String message){
        this.code       = code;
        this.message    = message;
    }

    public int getCode() {
        return code;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return JsonConvertTool.toString(this);
    }
}
