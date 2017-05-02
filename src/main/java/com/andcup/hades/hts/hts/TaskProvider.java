package com.andcup.hades.hts.hts;

import com.andcup.hades.hts.model.TaskEntity;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/2 16:29.
 * Description:
 */
public interface TaskProvider<T> {

    List<TaskEntity> getTasks();
}
