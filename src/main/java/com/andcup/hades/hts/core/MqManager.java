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
    final LinkedBlockingQueue<MqMessage<Message>> queue = new LinkedBlockingQueue<MqMessage<Message>>();


    public LinkedBlockingQueue<MqMessage<Message>> getQueue() {
        return queue;
    }

    public void push(IMqFactory mqFactory) {
        queue.addAll(mqFactory.create());
    }

    public void push(MqFactory mqFactory) {
        push(mqFactory.create());
    }

    public void push(List<MqMessage<Message>> message) {
        queue.addAll(message);
    }

    public void push(MqMessage<Message> message) {
        queue.add(message);
    }

    public MqMessage<Message> pop() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
