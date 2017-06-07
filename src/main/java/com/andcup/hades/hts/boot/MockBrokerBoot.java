package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.R;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.httpserver.HadesHttpServer;
import com.andcup.hades.httpserver.utils.LogUtils;

/**
 * Created by Amos
 * Date : 2017/5/5 20:34.
 * Description:
 */
public class MockBrokerBoot {

    public static void start(String port){
        /**
         * 配置文件初始化.
         * */
        Hades.init(port);
        LogUtils.info(MockBrokerBoot.class, " start config file : " + R.CONFIG + " port = " + port);
        /**
         * 核心代码初始化.
         * */
        MqBroker.getInstance().start();
        MqBroker.getInstance().setConsumer(MqConsumer.Factory.getConsumer());
        LogUtils.info(MockBrokerBoot.class," listen port : " + R.port);
        /**
         * 启动服务器.
         * */
        new HadesHttpServer().bind(R.port).scan("com.andcup.hades.hts.boot.mock").start();
        LogUtils.info(MockBrokerBoot.class," start server : " + R.port + " success. ");
    }
}
