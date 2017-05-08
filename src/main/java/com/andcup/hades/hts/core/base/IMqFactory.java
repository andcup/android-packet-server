package com.andcup.hades.hts.core.base;

import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 16:43.
 * Description:
 */
public interface IMqFactory<T> {

    String getGroupId();

    /**
     * 检测文件吃否存在.
     * */
    boolean checkFileIsExist();

    /**
     * 检测文件吃否存在.
     * */
    boolean checkFileIsLatest();

    /**
     * 构建消息.
     * */
    List<MqMessage<Message>> create();

}
