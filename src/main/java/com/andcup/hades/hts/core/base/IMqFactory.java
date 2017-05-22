package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 16:43.
 * Description:
 */
public interface IMqFactory<T> {
    /**
     * 构建消息.
     * */
    List<Message<Task>> create();

}
