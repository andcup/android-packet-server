package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 14:43.
 * Description:
 */
public abstract class MqConsumer implements IMqConsumer, IMqConsumer.Executor {

    final static Logger logger = LoggerFactory.getLogger(MqConsumer.class);

    MqConsumer flowConsumer;
    MqManager<Message<Task>>  mqManager = new MqManager();
    ConsumerExecutorThread mqExecutorThread;

    public MqConsumer(){
        mqExecutorThread = new ConsumerExecutorThread();
        mqExecutorThread.start();
    }

    public IMqConsumer flow(MqConsumer consumer) {
        this.flowConsumer = consumer;
        return this;
    }

    public final void consume(Message<Task> message){
        /**
         * 更新状态.
         * */
        message.setTopic(getConsumer().topic());
        /**
         * 添加到队列.
         * */
        mqManager.push(message);
    }

    public void abort(Message<Task> target) {
        //mqManager.remove(target);
    }

    public void abort(String groupId) {
        //mqManager.remove(groupId);
        flowConsumer.abort(groupId);
    }

    private Consumer getConsumer(){
        return getClass().getAnnotation(Consumer.class);
    }

    class ConsumerExecutorThread extends Thread{

        @Override
        public void run() {
            try{
                while (true){
                    Message<Task> message = mqManager.pop();
                    if( null != message){
                        //初始化MqMessage.
                        message.setState(Message.State.ING);
                        Message.State state =  execute(message);
                        message.setState(state);
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
