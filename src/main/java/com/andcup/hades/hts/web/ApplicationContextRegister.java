package com.andcup.hades.hts.web;

import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Amos
 * Date : 2017/5/5 17:37.
 * Description:
 */
public class ApplicationContextRegister implements ApplicationContextAware{

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        /**
         * 启动Broker.
         * */
        MqBroker.getInstance().start();

        /**
         * 初始化Consumer.
         * */
        MqBroker.getInstance().setConsumer(MqConsumer.Factory.getConsumer());
    }
}
