package com.andcup.hades.hts.web.controller.base;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Amos
 * Date : 2017/4/13 17:59.
 * Description:
 */
public abstract class BaseController {

    /** 主题. */
    private static final String THEME = "/";

    public BaseController(){
        LoggerFactory.getLogger(this.getClass().getName());
    }

    protected ModelAndView getTheme(String viewName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected String getIp(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
