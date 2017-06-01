package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.server.HadesHttpServer;
import com.andcup.hades.hts.server.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 20:34.
 * Description:
 */
public class MockBrokerBoot {

    public static void start(String path, String port){
        /**
         * 配置文件初始化.
         * */
        HadesRootConfigure.init(path);

        HadesRootConfigure.sInstance.port = Integer.valueOf(port);

        LogUtils.info(MockBrokerBoot.class, " start config file : " + path + " port = " + port);


        /**
         * 核心代码初始化.
         * */
        MqBroker.getInstance().start();
        MqBroker.getInstance().setConsumer(MqConsumer.Factory.getConsumer());

        LogUtils.info(MockBrokerBoot.class," listen port : " + HadesRootConfigure.sInstance.port);
        /**
         * 启动服务器.
         * */
        new HadesHttpServer().bind(HadesRootConfigure.sInstance.port).scan("com.andcup.hades.hts.boot.mock").start();

        LogUtils.info(MockBrokerBoot.class," start server : " + HadesRootConfigure.sInstance.port + " success. ");
    }
}
