package com.andcup.hades.hts.controller.base;

import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Amos
 * Date : 2017/4/13 17:59.
 * Description:
 */
public abstract class BaseController {

    private static final String THEME = "/";

    protected ModelAndView getTheme(String viewName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
