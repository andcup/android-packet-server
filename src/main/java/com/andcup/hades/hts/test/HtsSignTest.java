package com.andcup.hades.hts.test;

import com.andcup.hades.httpserver.utils.LogUtils;
import com.thoughtworks.studios.javaexec.CommandExecutor;
import com.thoughtworks.studios.javaexec.CommandExecutorException;

import java.util.Arrays;

/**
 * Created by Amos
 * Date : 2017/5/27 17:18.
 * Description:
 */
public class HtsSignTest {

    public static void main(String[] args){

        String qianming1 =  printfSign(args[0]);
        LogUtils.info(HtsSignTest.class, " \r\n" + qianming1);

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String printfSign(String path){
        String value = exec("jar tf " +path + " " + "META-INF/");
        String[] rsaFiles = value.split("\r\n");
        for(String rsaFile : rsaFiles){
            if(rsaFile.contains("RSA")){
                exec("jar xf " + path + " " + rsaFile);
                String renamePath = System.currentTimeMillis() + "";
                String temp = exec("ren " + "META-INF" + " " + renamePath);

                LogUtils.info(HtsSignTest.class, " \r\n" + temp);

                return exec("keytool -printcert -file " + rsaFile.replace("META-INF", renamePath));
            }
        }
        return null;
    }

    private static void sleep2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String exec(String command) {
        LogUtils.info(HtsSignTest.class,command);
        CommandExecutor executor = new CommandExecutor(Arrays.asList(command.split(" ")));
        try {
             return executor.run();
        } catch (CommandExecutorException e) {
        }
        return null;
    }
}
