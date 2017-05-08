package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 14:43.
 * Description:
 */
public abstract class MqConsumer implements IMqConsumer, IMqConsumer.Executor {

    final static Logger logger              = LoggerFactory.getLogger(MqConsumer.class);

    IMqConsumer flowConsumer;
    MqManager   mqManager = new MqManager();
    ConsumerExecutorThread mqExecutorThread;

    public MqConsumer(){
        mqExecutorThread = new ConsumerExecutorThread();
        mqExecutorThread.start();
    }

    public IMqConsumer flow(IMqConsumer consumer) {
        this.flowConsumer = consumer;
        return this;
    }

    public final void consume(MqMessage<Message> message){
        mqManager.push(message);
    }

    class ConsumerExecutorThread extends Thread{

        @Override
        public void run() {
            try{
                while (true){
                    MqMessage<Message> message = mqManager.pop();
                    if( null != message){
                        //初始化MqMessage.
                        message.setState(MqMessage.State.ING);
                        MqMessage.State state =  executor(message);
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
