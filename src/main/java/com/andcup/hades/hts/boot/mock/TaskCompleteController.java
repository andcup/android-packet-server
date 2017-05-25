package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.server.HadesHttpResponse;
import com.andcup.hades.hts.server.RequestController;
import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.bind.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/25 13:30.
 * Description:
 */
@Controller("/api/task")
public class TaskCompleteController extends RequestController {

    Logger logger = LoggerFactory.getLogger(TaskCompleteController.class.getName());

    @Request(value = "/feedback", method = Request.Method.POST)
    public HadesHttpResponse feedback(@Var("groupId")String groupId,
                                      @Var("id") String id,
                                      @Var("code") int code,
                                      @Var("message") String message){

        logger.info("groupId = " + groupId + " id = " + id + " code = " + code + " message = " + message);

        return new HadesHttpResponse(0, " task complete.");
    }
}
