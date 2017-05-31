package com.andcup.hades.hts.test;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.boot.MockBrokerApplication;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by Amos
 * Date : 2017/5/31 16:28.
 * Description:
 */
public class GarbageCleanerThreadTest {
    public static void main(String[] args){
        MockBrokerApplication.GarbageCleanerThread thread = new MockBrokerApplication.GarbageCleanerThread("F:", 2* 30 * 24 * 60 * 60 * 1000);
//        HadesRootConfigure.init(args[0]);
//        long modifiedTime = new File("F:\\sign").lastModified();
//        LogUtils.info(GarbageCleanerThreadTest.class, new Date(modifiedTime).toString());
//        LogUtils.info(GarbageCleanerThreadTest.class, new Date(thread.getLastModifyTime(new File("F:\\sign"))).toString());
        thread.start();
    }
}
