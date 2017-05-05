package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.model.Message;
import com.andcup.hades.hts.model.MqMessage;

/**
 * Created by Amos
 * Date : 2017/5/5 11:44.
 * Description:
 */
public interface IMqBroker {

    /**
     * 收到消息.
     * */
    void  produce(IMqFactory factory);

    /**
     * 消息消费完成.
     * */
    void  consumeComplete(MqMessage<Message> msg);

}
