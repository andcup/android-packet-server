package com.andcup.hades.hts.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Amos
 * Date : 2017/5/10 18:36.
 * Description:
 */

public class HadesHttpServer {

    int port            = 80;
    String contextPath  = "/";
    String packageName;

    public HadesHttpServer bind(int port){
        this.port = port;
        return this;
    }

    public HadesHttpServer setContextPath(String contextPath){
        this.contextPath = contextPath;
        return this;
    }

    public HadesHttpServer scan(String packageName){
        this.packageName = packageName;
        return this;
    }

    public void start(  ){
        try {
            HttpServerProvider provider = HttpServerProvider.provider();
            HttpServer server = provider.createHttpServer(new InetSocketAddress(port), 5000);

            HadesHttpMappingHandler mappingHandler = new HadesHttpMappingHandler(packageName);
            server.createContext(contextPath, mappingHandler);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
