package com.andcup.hades.hts.boot.core.test;

import com.andcup.hades.hts.boot.core.bind.Body;
import com.andcup.hades.hts.boot.core.bind.Controller;
import com.andcup.hades.hts.boot.core.bind.Request;
import com.andcup.hades.hts.boot.core.bind.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/15 14:18.
 * Description:
 */

@Controller(name = "/api/user")
public class LoginController  {

    final static Logger sLogger = LoggerFactory.getLogger(LoginController.class);

    @Request(value = "/login",  method = Request.Method.GET)
    public void login(@Var("username") String username, @Var("password") String password){
        sLogger.info("username = " + username + " password = " + password);
    }

    @Request(value = "/logout", method = Request.Method.GET)
    public void logout(@Var("username") String username, @Var("password") String password){
        sLogger.info("username = " + username + " password = " + password);
    }

    public static class Logout{

    }
}
