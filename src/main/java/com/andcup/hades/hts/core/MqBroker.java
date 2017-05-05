package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqBroker;
import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.model.Message;
import com.andcup.hades.hts.model.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Amos
 * Date : 2017/5/2 15:04.
 * Description: 生产-消费 中间件.
 */
public class MqBroker implements IMqBroker {

    final static Logger logger              = LoggerFactory.getLogger(MqBroker.class);
    static final MqBroker S_TASK_BROKER     = new MqBroker();

    MqManager newQueueManager    = new MqManager();
    MqManager runQueueManager    = new MqManager();
    MqManager finishQueueManager = new MqManager();

    Executor  executor  = Executors.newCachedThreadPool();

    private MqBroker(){
        logger.info(MqBroker.class.getName() + " created. ");
    }

    public static MqBroker getInstance() {
        return S_TASK_BROKER;
    }

    public void produce(IMqFactory factory) {
        newQueueManager.push(factory);
    }

    public void consumeComplete(MqMessage<Message> msg) {

    }

    public void start(){

        /**
         * 将消息加入到执行队列中. 等待执行.
         * */
        executor.execute(new Runnable() {
            public void run() {

            }
        });

        /**
         * 处理已完成的消息.
         * */
        executor.execute(new Runnable() {
            public void run() {

            }
        });

        /**
         * 超时消息处理.
         * */
        executor.execute(new Runnable() {
            public void run() {
                while (true){
                    try {
                        newQueueManager.getQueue().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}