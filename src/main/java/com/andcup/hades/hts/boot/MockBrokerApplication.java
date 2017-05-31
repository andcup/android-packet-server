package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/5 16:23.
 * Description:
 */
public class MockBrokerApplication {

    public static void main(String[] args){
        MockBrokerBoot.start(args[0], args[1]);

        /**
         * 临时打包文件保留5个小时.
         * */
        new GarbageCleaner(HadesRootConfigure.sInstance.getApkTempDir(), 5 * 60 * 60 * 1000).start();
        /**
         * 日志文件保留5天.
         * */
        new GarbageCleaner(HadesRootConfigure.sInstance.getLogTempDir(), 5 * 24 * 60 * 60 * 1000).start();

        LogUtils.info(MqBroker.class, " start server ok. ");
    }


    public static class GarbageCleaner extends Thread{

        String dir;
        long   time;

        public GarbageCleaner(String dir, long time){
            this.dir = dir;
            this.time = time;
        }

        @Override
        public void run() {
            while (true){
                //每隔10分钟遍历一次.
                try {
                    Thread.sleep(10 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File[] files = new File(dir).listFiles();
            }
        }
    }
}
