package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 14:43.
 * Description:
 */
public abstract class MqConsumer implements IMqConsumer, IMqConsumer.Executor {

    MqConsumer flowConsumer;
    MqManager<Message<Task>>  mqManager = new MqManager();
    ConsumerExecutorThread    consumerExecutorSafe = new ConsumerExecutorThread();

    public IMqConsumer flow(MqConsumer consumer) {
        this.flowConsumer = consumer;
        return this;
    }

    @Override
    public final State execute(Message<Task> message) throws ConsumeException {
        int match = getConsumer().match();
        int msgMatch = message.getMatch();

        State consumer = getConsumer().last();
        State last = message.getLastState();

        if(match == Integer.MAX_VALUE || (match == msgMatch)){
            if(consumer == State.DEFAULT || (consumer == last)){
                String log ="task name : " + message.getName() + " task id : " + message.getId() + " step :" + getConsumer().topic().getName();
                LogUtils.info(MqConsumer.class," start " + log);
                State state = doInBackground(message);
                LogUtils.info(MqConsumer.class," end " + log + " state : " + state);
                return state;
            }
        }
        return message.getLastState();
    }

    protected abstract State doInBackground(Message<Task> message);

    @Override
    public void consume(List<Message<Task>> messages) {
        for(Message<Task> message : messages){
            message.setTopic(getConsumer().topic());
        }
        mqManager.push(messages);
        /**
         * 每次添加任务时，启动工作线程.
         * */
        if(!consumerExecutorSafe.isAlive()){
            consumerExecutorSafe.start();
        }
    }

    public final void consume(Message<Task> message){
        message.setState(State.ING);
        /**
         * 更新状态.
         * */
        message.setTopic(getConsumer().topic());
        /**
         * 添加到队列.
         * */
        mqManager.push(message);
    }

    public void abort() {
    }

    public void abort(String groupId) {
        //mqManager.remove(groupId);
        flowConsumer.abort(groupId);
    }

    private Consumer getConsumer(){
        return getClass().getAnnotation(Consumer.class);
    }

    protected long getTimeOut(){
        return getClass().getAnnotation(Consumer.class).timeout();
    }

    class ConsumerExecutorThread extends Thread{

        @Override
        public void run() {
            while (true){
                Message<Task> message = mqManager.pop();
                if( null != message){
                    /**
                     * 开始处理消息.
                     * */
                    String log ="task name : " + message.getName() + " task id : " + message.getId() + " step :" + getConsumer().topic().getName();
                    com.andcup.hades.hts.core.model.State state;
                    try{
                        state =  execute(message);
                    }catch (Exception e){
                        state = com.andcup.hades.hts.core.model.State.FAILED;
                        message.setMsg(e.getMessage());
                        LogUtils.info(MqConsumer.class, " end " + log + " state : " + state + " error : " + e.getMessage());
                    }
                    message.setState(state);
                    message.setLastState(state);
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
                }else{
                    LogUtils.info(MqConsumer.class, " not found new task exit.");
                    break;
                }
            }
        }
    }
}
