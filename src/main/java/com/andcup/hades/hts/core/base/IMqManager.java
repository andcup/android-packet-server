package com.andcup.hades.hts.core.base;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amos
 * Date : 2017/5/2 16:29.
 * Description:
 */
public interface IMqManager<T> {

    /**
     *获取消息队列.
     * */
    LinkedBlockingQueue<T> getQueue();

    void push(List<T> message);
    /**
     * 添加消息到消息队列
     * */
    void push(T message);

    /**
     * 获取最新的消息并从队列中删除.
     * */
    T pop();
}
