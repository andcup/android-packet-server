package com.andcup.hades.hts.server.test;

import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.bind.Var;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/15 14:18.
 * Description:
 */

@Controller("/api/user")
public class LoginController  {

    @Request(value = "/login",  method = Request.Method.POST)
    public void login(@Var("username") String username, @Var("password") String password){
        LogUtils.info(LoginController.class, "username = " + username + " password = " + password);
    }
}
