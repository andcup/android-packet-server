package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.ConsumerAnnotationScanTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 14:24.
 * Description:
 */
public interface IMqConsumer {

    interface Callback{
        /**
         * 消费成功.
         * */
        void onSuccess(MqMessage<Message> message);
        /**
         * 消费失败.
         * */
        void onFailed(MqMessage<Message> message);
    }

    /**
     * 消费消息.
     * */
    void consume(MqMessage<Message> message);

    interface Executor{

        /**
         * 执行任务.
         * */
        MqMessage.State execute(MqMessage<Message> message);
        /**
         * 中断任务.
         * */
        void abort(MqMessage<Message> message);
    }

    class Factory{

        static String packageName = "com.andcup.hades.hts.core.services";
        public static MqConsumer getConsumer(){
            List<MqConsumer> consumerList = build(ConsumerAnnotationScanTool.getClazzFromPackage(packageName));
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
