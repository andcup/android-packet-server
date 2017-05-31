package com.andcup.hades.hts.test;

import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.MD5;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import com.andcup.hades.hts.server.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/31 17:15.
 * Description:
 */
public class BrokerStressTest {

    static String MOCK_DATA =
            "{\n" +
                    "        \"groupId\": 120,\n" +
                    "        \"channelId\": 4,\n" +
                    "        \"name\": \"SDKDEMO_0\",\n" +
                    "        \"priority\": 10,\n" +
                    "        \"feedback\": \"http://localhost:706/api/task/feedback\",\n" +
                    "        \"type\": 0,\n" +
                    "        \"md5\": \"amos_test11\",\n" +
                    "        \"other\": \"platId=13-extraChannel=1\",\n" +
                    "        \"sourceId\": 0,\n" +
                    "        \"sourcePath\": \"files/5m_0.apk\",\n" +
                    "        \"channelPath\": \"files/amos/5m_123.apk\"\n" +
                    "    }";

    static final String formatChannel = "files/amos/5m_%s.apk";

    public static void main(String[] args) {
        new StressThread("demo").start();
    }

    public static class StressThread extends Thread {

        final int testCount = 10000;

        Task   task = JsonConvertTool.toJson(MOCK_DATA, Task.class);
        String name;

        OKHttpClient okHttpClient;

        public StressThread(String value) {
            this.name = value;
            okHttpClient = new OKHttpClient("http://192.168.28.103:706/api/task/start");
        }

        @Override
        public void run() {
            for (int i = 0; i < testCount; i++) {
                try {
                    okHttpClient.call(getData(String.valueOf(i), MD5.toMd5(name + i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LogUtils.info(StressThread.class, " i = " + i);
            }
        }

        private String getData(String channelId, String md5) {
            List<Task> list = new ArrayList();
            task.name = name;
            task.id = channelId;
            task.md5 = md5;
            task.channelPath = String.format(formatChannel, channelId);
            list.add(task);
            return JsonConvertTool.toString(list);
        }
    }
}
