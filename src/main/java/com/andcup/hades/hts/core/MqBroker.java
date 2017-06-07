package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqBroker;
import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.httpserver.utils.LogUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Amos
 * Date : 2017/5/2 15:04.
 * Description: 生产-消费 中间件.
 */
public class MqBroker implements IMqBroker {

    static final MqBroker sBroker  = new MqBroker();

    MqManager<IMqFactory>    mqFactoryManager   = new MqManager();
    MqManager<Message<Task>> runQueueManager    = new MqManager();
    /**
     * 数据持久化.
     * */
    MqCacheFactory mqCacheFactory = new MqCacheFactory();

    Executor   executor  = Executors.newCachedThreadPool();
    MqConsumer consumer;

    private MqBroker(){
        LogUtils.info(MqBroker.class,MqBroker.class.getName() + " created. ");
        produce(mqCacheFactory);
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
        /**
         * 从运行队列删除.
         * */
        runQueueManager.remove(msg);
        /**
         * 从缓存中删除.
         * */
        mqCacheFactory.remove(msg);

        LogUtils.info(MqBroker.class," 任务: " + msg.getName() + " 状态 : " + msg.getState() + " 消息 = " + msg.getMsg() + " 用时 : " + msg.useTime() + " 剩余 : " + runQueueManager.size());
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
                        List<Message<Task>> received = factory.create();
                        if( null == received || received.size() <= 0){
                            continue;
                        }
                        LogUtils.info(MqBroker.class," received task count = " + received.size() + "/" + runQueueManager.size());
                        //任务去重.
                        List<Message<Task>> append  = runQueueManager.merge(received);
                        //添加到打包任务.
                        runQueueManager.push(append);
                        LogUtils.info(MqBroker.class," merge task count = " + append.size() + "/" + runQueueManager.size());
                        //开始消费.
                        consumer.consume(append);
                        //数据持久化.
                        mqCacheFactory.addAll(append);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
