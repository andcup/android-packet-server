package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqBroker;
import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Amos
 * Date : 2017/5/2 15:04.
 * Description: 生产-消费 中间件.
 */
public class MqBroker implements IMqBroker {

    final static Logger logger              = LoggerFactory.getLogger(MqBroker.class);
    static final MqBroker S_TASK_BROKER     = new MqBroker();

    MqManager newQueueManager    = new MqManager();
    MqManager finishQueueManager = new MqManager();
    MqManager runQueueManager    = new MqManager();

    Executor   executor  = Executors.newCachedThreadPool();
    MqConsumer consumer;

    private MqBroker(){
        logger.info(MqBroker.class.getName() + " created. ");
    }

    public static MqBroker getInstance() {
        return S_TASK_BROKER;
    }

    public void setConsumer(MqConsumer consumer) {
        this.consumer = consumer;
    }

    public void produce(IMqFactory factory) throws FileNotFoundException {
        if(!factory.checkFileIsExist()){
            throw new FileNotFoundException("file is not exist!");
        }
        if(!factory.checkFileIsLatest()){
            abort(factory.getGroupId());
        }
        newQueueManager.push(factory);
    }

    public void complete(MqMessage<Message> msg) {
        finishQueueManager.push(msg);
    }

    public void abort(String groupId) {
        newQueueManager.remove(groupId);
        runQueueManager.remove(groupId);
    }

    public void start(){

        /**
         * 将消息加入到执行队列中. 等待执行.
         * */
        executor.execute(new Runnable() {
            public void run() {
                while (true){
                    MqMessage<Message> message = newQueueManager.pop();
                    if( null != message){
                        /**设置任务状态.*/
                        message.setCreateTime(System.currentTimeMillis());
                        message.setState(MqMessage.State.ING);
                        message.setMsg("name : " + message.getName() + " id : " + message.getId() + " is ready.");
                        /**中断相同的任务.*/
                        consumer.abort(message);
                        /**开始处理任务.*/
                        consumer.consume(message);
                        /**加入到运行队列.*/
                        runQueueManager.push(message);
                        logger.info(JsonConvertTool.formatString(message));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
                }
            }
        });
    }
}
