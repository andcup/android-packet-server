package com.andcup.hades.hts.controller.cps;

import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.hts.TaskProvider;
import com.andcup.hades.hts.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/3 13:40.
 * Description:
 */
public class CpsTaskProvider implements TaskProvider<CpsTaskEntity> {

    CpsTaskEntity cpsTaskEntity;

    public CpsTaskProvider(CpsTaskEntity taskEntity){
        this.cpsTaskEntity = taskEntity;
    }

    public List<TaskEntity> getTasks() {

        List<TaskEntity> taskEntityList = new ArrayList<TaskEntity>();

        TaskEntity taskEntity = new TaskEntity();

        return null;
    }
}
