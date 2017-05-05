package com.andcup.hades.hts.test;

import com.andcup.hades.hts.core.MqBroker;

/**
 * Created by Amos
 * Date : 2017/5/5 16:23.
 * Description:
 */
public class TestBroker {

    public static void main(String[] args){
        MqBroker.getInstance().start();
    }
}
