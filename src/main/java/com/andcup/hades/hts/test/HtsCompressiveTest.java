package com.andcup.hades.hts.test;

import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import org.slf4j.LoggerFactory;


/**
 * Created by Amos
 * Date : 2017/5/9 10:01.
 * Description:
 */
public class HtsCompressiveTest {
    final static org.slf4j.Logger logger              = LoggerFactory.getLogger(MqBroker.class);
    public static void main(String[] args ){
        test2();
        test3();
        test4();
        test5();

        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void test(){
        logger.info(HtsCompressiveTest.class.getName(), "--------------- test start. ----------------- \n");
        for(int i = 0; i< 20000; i++){
            HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/test/19196PT/xinshenqu/xinshenqu");
            HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/test/GEDOU/GEDOUDAOHUN");
            HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/19196PINGTAI/HUAQIANGU");
            HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/123/TIANJIANGXIONGSHI");
        }
        logger.info(HtsCompressiveTest.class.getName(), "--------------- test end. ----------------- \n ");
    }

    private static void test5(){
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< 1000; i++){
                    HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/123/TIANJIANGXIONGSHI");
//                    try {
//                        Thread.sleep(i%3 == 0? 1000:100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    logger.info( "---------------" + testCount++ + " ----------------- \n ");
                }
            }
        }.start();
    }

    private static void test4(){
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< 1000; i++){
                    HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/19196PINGTAI/HUAQIANGU");
                    logger.info( "---------------" + testCount++ + " ----------------- \n ");
                }
                logger.info( "--------------- test end. ----------------- \n ");
            }
        }.start();
    }

    static int testCount = 0;

    private static void test3(){
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i< 1000; i++){
                    HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/test/GEDOU/GEDOUDAOHUN");
//                    try {
//                        Thread.sleep(i%3 == 0? 1000:100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    logger.info( "---------------" + testCount++ + " ----------------- \n ");
                }
                logger.info( "--------------- test end. ----------------- \n ");
            }
        }.start();
    }

    private static void test2(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i = 0; i< 1000; i++){
                    HtsCompressiveTest(String.valueOf(i), "platId=13-extraChannel=200", "/test/19196PT/xinshenqu/xinshenqu");
//                    try {
//                        Thread.sleep(i%3 == 0? 1000:100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    logger.info( "---------------" + testCount++ + " ----------------- \n ");
                }

            }
        }.start();
    }

    private static void HtsCompressiveTest(String id, String other, String game){
        CpsTaskEntity task = JsonConvertTool.toJson(DATA, CpsTaskEntity.class);
        task.channels.get(0).id = id;
        task.channels.get(0).other = other;
        task.originPackLocalPath = game;
        OKHttpClient okHttpClient = new OKHttpClient("http://localhost:607/api/pack");
        okHttpClient.call(JsonConvertTool.toString(task));
    }

    public static String DATA = "{\n" +
            "    \"feedbackApiAddress\": \"http://218.66.43.205:8089/yycpsfora/interface/packChildsCallback.html\",\n" +
            "    \"packType\": 0,\n" +
            "    \"channels\": [\n" +
            "        {\n" +
            "            \"id\": 110,\n" +
            "            \"other\": \"platId=13-extraChannel=1\",\n" +
            "            \"priority\": 10,\n" +
            "            \"gamePid\": 261,\n" +
            "            \"sourceId\": 0\n" +
            "        }\n" +
            "    ],\n" +
            "    \"originPackLocalPath\": \"LIEWANGDEFENZHENG/LIEWANGDEFENZHENG\",\n" +
            "    \"channelPackRemoteDir\": \"/test/amos\",\n" +
            "    \"attachData\": \"{\\\"gamePid\\\":109, \\\"gameVersion\\\":\\\"0\\\", \\\"dir\\\":\\\"/test/59YX/QIANNVYOUHUNYLJH\\\", \\\"fileName\\\":\\\"H5LUOBOWAN2\\\"}\"\n" +
            "}";

}
