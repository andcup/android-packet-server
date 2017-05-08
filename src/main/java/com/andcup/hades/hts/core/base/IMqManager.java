package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amos
 * Date : 2017/5/2 16:29.
 * Description:
 */
public interface IMqManager {

    /**
     *获取消息队列.
     * */
    LinkedBlockingQueue<MqMessage<Message>> getQueue();


    void push(IMqFactory mqFactory);

    void push(List<MqMessage<Message>> message);
    /**
     * 添加消息到消息队列
     * */
    void push(MqMessage<Message> message);

    /**
     * 获取最新的消息并从队列中删除.
     * */
    MqMessage<Message> pop();
}
