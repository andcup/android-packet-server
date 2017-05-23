package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.COMPLETE, bind = Topic.COMPLETE)
public class CompleteComsumer extends MqConsumer{
    @Override
    public Message.State execute(Message<Task> message) throws ConsumeException {
        return Message.State.SUCCESS;
    }
}
