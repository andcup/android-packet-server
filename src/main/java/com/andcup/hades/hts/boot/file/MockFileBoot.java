package com.andcup.hades.hts.boot.file;

import com.andcup.hades.hts.boot.mock.FileInfoOldController;
import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.MqBroker;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Amos
 * Date : 2017/5/5 20:34.
 * Description:
 */
public class MockFileBoot {

    final static Logger logger              = LoggerFactory.getLogger(MqBroker.class);

    public static void start(String path){
        try {
            logger.info(" start config file : " + path);
            /**
             * 配置文件初始化.
             * */
            HadesRootConfig.init( path );
            logger.info(" start listen port : " + HadesRootConfig.sInstance.port);
            /**
             * 启动Mock服务器.
             * */
            HttpServerProvider provider = HttpServerProvider.provider();
            HttpServer server = provider.createHttpServer(new InetSocketAddress(HadesRootConfig.sInstance.port), 1000);

            /**
             * 注册文件信息获取接口.
             * */
            FileInfoOldController fileInfoController = new FileInfoOldController();
            server.createContext(fileInfoController.api(), fileInfoController);
            server.start();
            logger.info(" start server : " + HadesRootConfig.sInstance.port + " success. ");
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
