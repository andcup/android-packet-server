package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqBroker;
import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Amos
 * Date : 2017/5/2 15:04.
 * Description: 生产-消费 中间件.
 */
public class MqBroker implements IMqBroker {

    final static Logger logger     = LoggerFactory.getLogger(MqBroker.class);
    static final MqBroker sBroker  = new MqBroker();

    MqManager<IMqFactory>    mqFactoryManager   = new MqManager();
    MqManager<Message<Task>> finishQueueManager = new MqManager();
    MqManager<Message<Task>> runQueueManager    = new MqManager();

    Executor   executor  = Executors.newCachedThreadPool();
    MqConsumer consumer;

    private MqBroker(){
        logger.info(MqBroker.class.getName() + " created. ");
    }

    public static MqBroker getInstance() {
        return sBroker;
    }

    public void setConsumer(MqConsumer consumer) {
        this.consumer = consumer;
    }

    public void produce(IMqFactory factory){
        mqFactoryManager.push(factory);
    }

    public void complete(Message<Task> msg) {
        logger.info(" complete " + msg.getName() + " state : " + msg.getState() + " msg = " + msg.getMsg());
        /**
         * 从运行队列删除.
         * */
        runQueueManager.remove(msg);
        /**
         * 添加到完成队列.
         * */
        finishQueueManager.push(msg);
    }

    @Override
    public void abort(String abortId) {

    }

    public void start(){
        /**
         * 将消息加入到执行队列中. 等待执行.
         * */
        executor.execute(new Runnable() {
            public void run() {
                while (true){
                    IMqFactory factory = mqFactoryManager.pop();
                    if( null != factory){
                        //判断文件是否存在.
                        List<Message<Task>> message = factory.create();
                        /**添加到打包任务.*/
                        runQueueManager.push(message);
                        /**
                         * 开始消费.
                         * */
                        consumer.consume(message);
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
                while (true){

                }
            }
        });
    }
}
