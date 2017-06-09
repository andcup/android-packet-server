package com.andcup.hades.hts;

import com.andcup.hades.hts.boot.HadesApplication;
import com.andcup.hades.hts.boot.controller.TaskController;
import com.andcup.hades.hts.core.MqBroker;
import com.andcup.hades.httpserver.utils.LogUtils;


/**
 * Created by Amos
 * Date : 2017/5/5 16:23.
 * Description:
 */
public class MockBrokerApplication {

    public static void main(String[] args) {
        /**
         * 启动程序.
         * */
        HadesApplication.init(args[0], args[1])
                .start(TaskController.class.getPackage().getName())
                .garbage();

        LogUtils.info(MqBroker.class, " start server ok. ");
    }
}
