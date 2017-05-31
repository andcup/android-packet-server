package com.andcup.hades.hts.test;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.server.utils.LogUtils;

/**
 * Created by Amos
 * Date : 2017/5/31 11:25.
 * Description:
 */
public class ThreadAliveTest {

    public static void main(String[] args){
        TestThread thread = new TestThread();
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        thread.start();
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
        LogUtils.info(TestThread.class, "  thread is start " + thread.isAlive());
    }

    static class TestThread extends Thread{
        @Override
        public void run() {
            LogUtils.info(TestThread.class, " TestThread start");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LogUtils.info(TestThread.class, " TestThread stop : " + isAlive());
        }
    }
}
