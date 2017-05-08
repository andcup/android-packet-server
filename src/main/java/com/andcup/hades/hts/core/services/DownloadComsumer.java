package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.config.HadesRootConfig;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.FtpTransfer;
import org.springframework.stereotype.Service;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.DOWNLOADING, bind = Topic.DOWNLOADING)
@Service
public class DownloadComsumer extends MqConsumer {

    MqMessage<Message> message;

    public MqMessage.State execute(MqMessage<Message> message) {
        this.message = message;

        FtpTransfer ftpTransfer = new FtpTransfer(HadesRootConfig.sInstance.remote.ftp);
        ftpTransfer.dlFromRemote(message.getData().sourcePath, message.getData().localDir + message.getName());
        return MqMessage.State.SUCCESS;
    }

    public void abort(MqMessage<Message> message) {

    }
}
