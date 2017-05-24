package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.compress.ICompress;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;

import java.io.File;
import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/5/23 13:54.
 * Description:
 */

@Consumer(topic = Topic.COMPRESS, bind = Topic.DECOMPILING, match = Task.TYPE_QUICK)
public class CompressConsumer extends MqConsumer {
    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {

        if(message.getLastState() == State.SUCCESS){
            Task task = message.getData();
            if(task.type == Task.TYPE_QUICK){
                /**
                 * 开始压缩.
                 * */
                File file = new File(Task.Helper.getRulePath(task));
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new ConsumeException(e.getMessage());
                }
                return ICompress.ZT.pack(Task.Helper.getApkPath(task),
                                  Task.Helper.getChannelPath(task),
                                    file) ?
                        State.SUCCESS : State.FAILED;
            }
        }

        return message.getLastState();
    }
}
