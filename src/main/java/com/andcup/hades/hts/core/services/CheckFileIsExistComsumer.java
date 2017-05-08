package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import org.springframework.stereotype.Service;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.CHECK_FILE_EXIST, bind = Topic.DOWNLOADING)
@Service
public class CheckFileIsExistComsumer extends MqConsumer {

    MqMessage<? extends Message> message;

    public MqMessage.State executor(MqMessage<Message> message) {
        return null;
    }

    public void abort(MqMessage<Message> target){

    }
}
