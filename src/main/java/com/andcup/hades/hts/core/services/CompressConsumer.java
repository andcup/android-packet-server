package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.zip.ZipProcessor;

import java.io.File;
import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/5/23 13:54.
 * Description:
 */

@Consumer(topic = Topic.COMPRESS, bind = Topic.DECOMPILING, match = Task.TYPE_ANDROID_QUICK)
public class CompressConsumer extends MqConsumer {
    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {
        Task task = message.getData();
        /**
         * 保存到未签名文件.
         * */
        boolean state = ZipProcessor.APK.process(Task.Helper.getDownloadPath(task),
                Task.Helper.getChannelPath(task),
                Task.Helper.getRulePath(task));

//        if(state){
//            state = ZipProcessor.RSA.process(Task.Helper.getChannelPath(task),
//                    Task.Helper.getChannelPath(task),
//                    null);
//        }
        return state? State.SUCCESS : State.FAILED;
    }
}
