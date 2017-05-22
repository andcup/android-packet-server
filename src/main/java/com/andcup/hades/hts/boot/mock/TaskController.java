package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.server.HadesHttpResponse;
import com.andcup.hades.hts.server.bind.Body;
import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/19 17:14.
 * Description:
 */

@Controller("/api/task")
public class TaskController {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(MqConsumer.class);

    @Request("/start")
    public HadesHttpResponse start(@Body(List.class) List<Task> taskList){
        logger.info(JsonConvertTool.toString(taskList));
        return new HadesHttpResponse(200);
    }
}
