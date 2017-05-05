package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.MqManager;
import com.andcup.hades.hts.core.base.IMqConsumer;
import com.andcup.hades.hts.model.Message;
import com.andcup.hades.hts.model.MqMessage;
import com.andcup.hades.hts.model.Topic;

/**
 * Created by Amos
 * Date : 2017/5/5 14:43.
 * Description:
 */
public abstract class MqConsumer implements IMqConsumer {

    IMqConsumer flowConsumer;
    MqManager   mqManager = new MqManager();

    public IMqConsumer flow(IMqConsumer consumer) {
        this.flowConsumer = consumer;
        return this;
    }

    public abstract Topic getTopic();

    public void consume(MqMessage<? extends Message> message, Callback callback){

    }

    protected void flowConsume(MqMessage<? extends Message> message) throws Exception {

    }
}
