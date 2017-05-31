package com.andcup.hades.hts.test;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.boot.MockBrokerApplication;

/**
 * Created by Amos
 * Date : 2017/5/31 16:28.
 * Description:
 */
public class GarbageCleanerThreadTest {
    public static void main(String[] args){
        HadesRootConfigure.init(args[0]);
        new MockBrokerApplication.GarbageCleanerThread("F:\\android-auto-pack\\fileserver\\123", 5 * 60 * 60 * 1000).start();
    }
}
