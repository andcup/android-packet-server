package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.ConsumerTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 14:43.
 * Description:
 */
public abstract class MqConsumer implements IMqConsumer {

    IMqConsumer flowConsumer;
    MqManager   mqManager = new MqManager();

    public IMqConsumer flow(IMqConsumer consumer) {
        this.flowConsumer = consumer;
        return this;
    }

    public void consume(MqMessage<? extends Message> message, Callback callback){

    }

    protected void flowConsume(MqMessage<? extends Message> message) throws Exception {

    }

    public static class Factory{

        static String packageName = "com.andcup.hades.hts.core.services";

        public static MqConsumer getConsumer(){

            List<MqConsumer> consumerList = build(ConsumerTools.getClazzFromPackage(packageName));
            MqConsumer start = findByTopic(consumerList, Topic.CHECK_FILE_EXIST);

            for(MqConsumer consumer : consumerList){
                Consumer annotation = consumer.getClass().getAnnotation(Consumer.class);
                Topic topic = annotation.topic();
                Topic bind  = annotation.bind();
                if(topic != bind){
                    MqConsumer next = findByTopic(consumerList, bind);
                    consumer.flow(next);
                }
            }
            return start;
        }

        private static MqConsumer findByTopic(List<MqConsumer> list, Topic target){
            for(MqConsumer consumer : list){
                Consumer annotation = consumer.getClass().getAnnotation(Consumer.class);
                Topic topic = annotation.topic();
                if(topic == target){
                    return consumer;
                }
            }
            return null;
        }

        private static List<MqConsumer> build(List<Class> classes){
            List<MqConsumer> consumers = new ArrayList<MqConsumer>();
            for(Class clazz : classes){
                try {
                    MqConsumer consumer = (MqConsumer) clazz.newInstance();
                    consumers.add(consumer);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return consumers;
        }
    }
}
