package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    @Override
    public final Message.State execute(Message<Task> message) throws ConsumeException {
        Consumer.Level level = getConsumer().level();
        Consumer.Level msgLevel = message.getLevel();
        if(level == Consumer.Level.LEVEL_ALL || (level == msgLevel)){
            String log ="task name : " + message.getName() + " task id : " + message.getId() + " step :" + getConsumer().topic().getName();
            logger.info(" start " + log);
            Message.State state = doInBackground(message);
            logger.info(" end " + log + " state : " + state);
            return state;
        }
        return message.getLastState();
    }

    protected abstract Message.State doInBackground(Message<Task> message);

    @Override
    public void consume(List<Message<Task>> messages) {
        for(Message<Task> message : messages){
            message.setTopic(getConsumer().topic());
        }
        mqManager.push(messages);
    }

    public final void consume(Message<Task> message){
        message.setState(Message.State.ING);
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
                        /**
                         * 开始处理消息.
                         * */
                        String log ="task name : " + message.getName() + " task id : " + message.getId() + " step :" + getConsumer().topic().getName();
                        Message.State state;
                        try{
                            state =  execute(message);
                            message.setLastState(state);
                        }catch (Exception e){
                            state = Message.State.FAILED;
                            message.setLastState(state);
                            logger.error(" end " + log + " state : " + state + " error : " + e.getMessage());
                        }
                        /**
                         * 下一个消费者
                         * */
                        if( null != flowConsumer && flowConsumer != MqConsumer.this){
                            flowConsumer.consume(message);
                        }else{
                            /**
                             * 消息消费完成.
                             * */
                            MqBroker.getInstance().complete(message);
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
