package com.andcup.hades.httpserver;


import com.andcup.hades.httpserver.bind.Controller;
import com.andcup.hades.httpserver.bind.Request;

import java.lang.reflect.Method;

/**
 * Created by Amos
 * Date : 2017/5/15 16:01.
 * Description:
 */
class RequestInvoker {
    public String  path;
    public Request request;
    public Controller controller;
    public Method  method;
    public Class   clazz;
}
