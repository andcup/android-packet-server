package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.config.F;
import com.andcup.hades.hts.config.R;
import com.andcup.hades.hts.config.WARConfigure;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.httpserver.HadesHttpServer;
import com.andcup.hades.httpserver.utils.CacheClear;
import com.andcup.hades.httpserver.utils.LogUtils;

import java.io.File;


/**
 * Created by Amos
 * Date : 2017/5/8 12:13.
 * Description:
 * Email:amos@sayboy.com
 * Github: https://github.com/andcup
 */
public class HadesApplication {

    public static HadesApplication sInstance;

    WARConfigure configure;

    private HadesApplication(String port, String keyName) {
        LogUtils.init(port);
        configure = WARConfigure.load(port, keyName);
    }

    public static synchronized HadesApplication init(String port, String keyName) {
        if( null == sInstance){
            sInstance = new HadesApplication(port, keyName);
        }
        return sInstance;
    }

    public HadesApplication start(String packageName){
        /**
         * 核心代码初始化.
         * */
        MqBroker.getInstance()
                .setConsumer(MqConsumer.Factory.getConsumer())
                .start();
        LogUtils.info(HadesApplication.class," listen port : " + R.port);
        /**
         * 启动服务器.
         * */
        new HadesHttpServer().bind(R.port).scan(packageName).start();
        LogUtils.info(HadesApplication.class," start server : " + R.port + " success. ");

        return this;
    }

    public HadesApplication garbage(){
        /**
         * 临时打包文件保留5个小时.
         * */
        new GarbageCleanerThread(F.WORK, 5 * 60 * 60 * 1000).start();
        /**
         * 日志文件保留5天.
         * */
        new GarbageCleanerThread(F.LOG, 5 * 24 * 60 * 60 * 1000).start();
        return this;
    }

    public R r() {
        return configure.r;
    }

    public F f() {
        return configure.f;
    }

    public static class GarbageCleanerThread extends Thread {

        String dir;
        long time;

        public GarbageCleanerThread(String dir, long time) {
            this.dir = dir;
            this.time = time;
        }

        @Override
        public void run() {
            while (true) {
                //每隔10分钟遍历一次.
                try {
                    File[] files = new File(dir).listFiles();
                    for (File file : files) {
                        long modifyTime = file.isDirectory() ? getLastModifyTime(file) : file.lastModified();
                        if (System.currentTimeMillis() - modifyTime > time) {
                            //LogUtils.info(GarbageCleanerThread.class, " delete file start :  " + file.getAbsolutePath());
                            try {
                                CacheClear.delete(file);
                            } catch (Exception e) {

                            }
                            //LogUtils.info(GarbageCleanerThread.class, " delete file end :  " + file.getAbsolutePath());
                        }
                    }
                    Thread.sleep(10 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }
            }
        }

        public long getLastModifyTime(File dirFile) {
            long modifyTime = dirFile.lastModified();
            try {
                for (File file : dirFile.listFiles()) {
                    modifyTime = file.lastModified() > modifyTime ? file.lastModified() : modifyTime;
                }
            } catch (Exception e) {

            }
            return modifyTime;
        }
    }
}
