package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

/**
 * Created by Amos
 * Date : 2017/5/5 14:24.
 * Description:
 */
public interface IMqConsumer {

    interface Callback{
        /**
         * 消费成功.
         * */
        void onSuccess(MqMessage<? extends Message> message);
        /**
         * 消费失败.
         * */
        void onFailed(MqMessage<? extends Message> message);
    }

    /**
     * 消费消息.
     * */
    void consume(MqMessage<? extends Message> message);
}
