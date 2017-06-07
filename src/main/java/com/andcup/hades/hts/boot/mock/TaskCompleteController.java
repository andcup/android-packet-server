package com.andcup.hades.hts.boot.mock;


import com.andcup.hades.httpserver.HadesHttpResponse;
import com.andcup.hades.httpserver.RequestController;
import com.andcup.hades.httpserver.bind.Controller;
import com.andcup.hades.httpserver.bind.Request;
import com.andcup.hades.httpserver.bind.Var;
import com.andcup.hades.httpserver.utils.LogUtils;

/**
 * Created by Amos
 * Date : 2017/5/25 13:30.
 * Description:
 */
@Controller("/api/task")
public class TaskCompleteController extends RequestController {


    @Request(value = "/feedback", method = Request.Method.POST)
    public HadesHttpResponse feedback(@Var("groupId")String groupId,
                                      @Var("id") String id,
                                      @Var("code") String code,
                                      @Var("message") String message){

        LogUtils.info(TaskCompleteController.class,"groupId = " + groupId + " id = " + id + " code = " + code + " message = " + message);

        return new HadesHttpResponse(0, " task complete.");
    }
}
