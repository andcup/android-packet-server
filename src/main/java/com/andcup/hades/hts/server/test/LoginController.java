package com.andcup.hades.hts.server.test;

import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.bind.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/15 14:18.
 * Description:
 */

@Controller("/api/user")
public class LoginController  {

    final static Logger sLogger = LoggerFactory.getLogger(LoginController.class);

    @Request(value = "/login",  method = Request.Method.POST)
    public void login(@Var("username") String username, @Var("password") String password){
        sLogger.info("username = " + username + " password = " + password);
    }
}
