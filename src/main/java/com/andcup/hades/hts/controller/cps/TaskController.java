package com.andcup.hades.hts.controller.cps;

import com.andcup.hades.hts.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Amos
 * Date : 2017/4/13 17:57.
 * Description: 第三方授权任务来源.需要特殊处理
 */

@Controller
@RequestMapping("/api")
public class TaskController extends BaseController {

    @ResponseBody
    @RequestMapping(value = {"/pack"}, method = RequestMethod.POST)
    public String start(){
        return "start";
    }
}
