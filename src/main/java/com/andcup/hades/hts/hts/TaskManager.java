package com.andcup.hades.hts.hts;

import com.andcup.hades.hts.model.TaskEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/2 15:04.
 * Description:
 */
public class TaskManager {

    /**
     * 打包队列.
     * */
    static  final Map<String, List<TaskEntity>> taskList = new HashMap<String, List<TaskEntity>>();
}
