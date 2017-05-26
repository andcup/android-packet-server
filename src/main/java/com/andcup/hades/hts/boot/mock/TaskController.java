package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.boot.MockMqFactory;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.server.HadesHttpResponse;
import com.andcup.hades.hts.server.RequestController;
import com.andcup.hades.hts.server.bind.Body;
import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/19 17:14.
 * Description:
 */

@Controller("/api/task")
public class TaskController extends RequestController {


    @Request(value = "/start0", method = Request.Method.POST)
    public HadesHttpResponse single(@Body(Task.class) Task taskList){
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskList);
        return start(tasks);
    }

    @Request(value = "/start", method = Request.Method.POST)
    public HadesHttpResponse start(@Body(Task.class) List<Task> taskList){
        LogUtils.info(TaskController.class,JsonConvertTool.toString(taskList));
        MqBroker.getInstance().produce(new MockMqFactory(taskList));
        return new HadesHttpResponse(HadesHttpResponse.HTTP_OK, "commit task success.");
    }
}
