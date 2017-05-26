package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.core.MqBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 16:23.
 * Description:
 */
public class MockBrokerApplication {
    final static Logger logger  = LoggerFactory.getLogger(MqBroker.class);
    public static void main(String[] args){
        MockBrokerBoot.start(args[0]);
        logger.info(" start server ok");
    }
}
