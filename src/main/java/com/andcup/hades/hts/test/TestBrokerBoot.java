package com.andcup.hades.hts.test;

import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.test.mock.MockController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Amos
 * Date : 2017/5/5 20:34.
 * Description:
 */
public class TestBrokerBoot {

    public static void start(){
        try {
            MqBroker.getInstance().start();
            MqBroker.getInstance().setConsumer(MqConsumer.Factory.getConsumer());

            HttpServerProvider provider = HttpServerProvider.provider();
            HttpServer server = provider.createHttpServer(new InetSocketAddress(605), 1000);

            /**
             * 注册打包接口.
             * */
            MockController mockController = new MockController();
            server.createContext(mockController.api(), mockController);
            server.start();

            while (true){
                Thread.sleep(60000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
