package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.SIGN, bind = Topic.UPLOADING, level = Consumer.Level.LEVEL_1)
public class ApkSignComsumer extends MqConsumer{
    @Override
    public Message.State doInBackground(Message<Task> message) {
        return Message.State.SUCCESS;
    }
}
