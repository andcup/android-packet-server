package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.Transfer;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.UPLOADING, bind = Topic.COMPLETE)
public class UploadComsumer extends MqConsumer {

    Transfer transfer;

    public Message.State doInBackground(Message<Task> message) {
        return message.getLastState();
    }

    public void abort(Message<Task> message) {
        if( null != transfer){
            transfer.abort();
        }
    }
}
