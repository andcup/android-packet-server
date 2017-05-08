package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

import java.io.FileNotFoundException;

/**
 * Created by Amos
 * Date : 2017/5/5 11:44.
 * Description:
 */
public interface IMqBroker {

    /**
     * 收到消息.
     * */
    void  produce(IMqFactory factory)  throws FileNotFoundException;

    /**
     * 消息消费完成.
     * */
    void  complete(MqMessage<Message> msg);

    /**
     * 消息中断完成.
     * */
    void  abort(String abortId);
}
