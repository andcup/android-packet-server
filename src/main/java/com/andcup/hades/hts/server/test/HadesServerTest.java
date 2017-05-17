package com.andcup.hades.hts.server.test;

import com.andcup.hades.hts.server.HadesHttpServer;

/**
 * Created by Amos
 * Date : 2017/5/15 17:03.
 * Description:
 */
public class HadesServerTest {
    public static void main(String[] args){
        HadesHttpServer server = new HadesHttpServer();

        server.bind(8818)
                .setController("com.andcup.hades.hts.boot.core.test")
                .start();

        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
