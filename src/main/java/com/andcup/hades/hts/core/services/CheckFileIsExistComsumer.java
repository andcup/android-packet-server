package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.config.HadesRootConfig;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
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

    public synchronized MqMessage.State execute(MqMessage<Message> message) {
        this.message = message;
        FileInfo fileInfo = new FileInfo();
        fileInfo.sourcePath = message.getData().sourcePath;
        //获取文件信息.
        ResponseEntity<FileInfo> fileInfoEntity = new OKHttpClient(HadesRootConfig.sInstance.remote.file.url)
                .call(JsonConvertTool.toString(fileInfo), ResponseEntity.class);
        //判断文件是否存在.
        if (fileInfoEntity.getCode() != ResponseEntity.SUCCESS) {
            return MqMessage.State.FAILED;
        }

        return null;
    }

    public void abort(MqMessage<Message> target) {

    }

}
