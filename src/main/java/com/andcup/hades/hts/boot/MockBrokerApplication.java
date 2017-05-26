package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.server.utils.LogUtils;
/**
 * Created by Amos
 * Date : 2017/5/5 16:23.
 * Description:
 */
public class MockBrokerApplication {
    public static void main(String[] args){
        MockBrokerBoot.start(args[0], args[1]);
        LogUtils.info(MqBroker.class, " start server ok. ");
    }
}
