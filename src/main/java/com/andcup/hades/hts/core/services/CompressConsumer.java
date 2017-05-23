package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;

/**
 * Created by Amos
 * Date : 2017/5/23 13:54.
 * Description:
 */

@Consumer(topic = Topic.COMPRESS, bind = Topic.DECOMPILING)
public class CompressConsumer extends MqConsumer {
    @Override
    public Message.State execute(Message<Task> message) {
        return Message.State.SUCCESS;
    }
}
