package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.base.IMqManager;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amos
 * Date : 2017/5/5 14:42.
 * Description:
 */
public class MqManager implements IMqManager {

    /**
     * 新收到任务队列.
     * */
    final LinkedBlockingQueue<MqMessage<? extends Message>> queue = new LinkedBlockingQueue<MqMessage<? extends Message>>();


    public LinkedBlockingQueue<MqMessage<? extends Message>> getQueue() {
        return queue;
    }

    public void push(IMqFactory mqFactory) {

    }

    public void push(MqFactory mqFactory) {
        push(mqFactory.create());
    }

    public void push(List<MqMessage<? extends Message>> message) {

    }

    public void push(MqMessage<? extends Message> message) {

    }

    public MqMessage<? extends Message> pop() {
        return null;
    }
}
