package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqManager;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amos
 * Date : 2017/5/5 14:42.
 * Description:
 */
public class MqManager<T> implements IMqManager<T> {

    /**
     * 新收到任务队列.
     * */
    final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();


    public LinkedBlockingQueue<T> getQueue() {
        return queue;
    }

    public void push(List<T> message) {
        queue.addAll(message);
    }

    public void push(T message) {
        queue.add(message);
    }

    public T peek(){
        return queue.peek();
    }

    public T pop() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
