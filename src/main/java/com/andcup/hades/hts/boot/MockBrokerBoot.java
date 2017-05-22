package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.server.HadesHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 20:34.
 * Description:
 */
public class MockBrokerBoot {

    final static Logger logger              = LoggerFactory.getLogger(MqBroker.class);

    public static void start(String path){
        try {
            logger.info(" start config file : " + path);
            /**
             * 配置文件初始化.
             * */
            HadesRootConfig.init(path);
            /**
             * 核心代码初始化.
             * */
            MqBroker.getInstance().start();
            MqBroker.getInstance().setConsumer(MqConsumer.Factory.getConsumer());
            logger.info(" listen port : " + HadesRootConfig.sInstance.port);

            new HadesHttpServer().bind(HadesRootConfig.sInstance.port)
                    .scan("com.andcup.hades.hts.boot.mock")
                    .start();
//            /**
//             * 启动Mock服务器.
//             * */
//            HttpServerProvider provider = HttpServerProvider.provider();
//            HttpServer server = provider.createHttpServer(new InetSocketAddress(HadesRootConfig.sInstance.port), 5000);
//
//            /**
//             * 注册打包接口.
//             * */
//            MockOldController mockController = new MockOldController();
//            server.createContext(mockController.api(), mockController);
//            server.start();

            logger.info(" start server : " + HadesRootConfig.sInstance.port + " success. ");

            while (true){
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
