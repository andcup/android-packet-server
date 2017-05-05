package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 16:43.
 * Description:
 */
public interface IMqFactory<T> {

    List<MqMessage<Message>> create();

}
