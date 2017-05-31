package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.ConsumerAnnotationScanTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 14:24.
 * Description:
 */
public interface IMqConsumer{

    interface Callback{
        /**
         * 消费成功.
         * */
        void onSuccess(Message<Task> message);
        /**
         * 消费失败.
         * */
        void onFailed(Message<Task> message);
    }

    /**
     * 消费消息.
     * */
    void consume(List<Message<Task>> messages);

    /**
     * 消费消息.
     * */
    void consume(Message<Task> message);

    interface Executor{

        /**
         * 执行任务.
         * */
        State execute(Message<Task> message) throws ConsumeException;
        /**
         * 中断当前任务.
         * */
        void abort();

        /**
         * 中断任务.
         * */
        void abort(String groupId);
    }

    class Factory{

        static String packageName = "com.andcup.hades.hts.core.services";

        static List<MqConsumer> sConsumerList;

        public static synchronized MqConsumer getConsumer(){

            if( null != sConsumerList){
                return getConsumerByTopic(Topic.DOWNLOADING);
            }
            sConsumerList = build(ConsumerAnnotationScanTool.getClazzFromPackage(packageName));
            MqConsumer start = getConsumerByTopic(Topic.DOWNLOADING);

            for(MqConsumer consumer : sConsumerList){
                Consumer annotation = consumer.getClass().getAnnotation(Consumer.class);
                Topic topic = annotation.topic();
                Topic bind  = annotation.bind();
                if(topic != bind){
                    MqConsumer next = getConsumerByTopic(bind);
                    consumer.flow(next);
                }
            }
            return start;
        }

        public static MqConsumer getConsumerByTopic(Topic target){
            for(MqConsumer consumer : sConsumerList){
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
