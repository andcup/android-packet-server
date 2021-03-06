package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.FtpTransfer;
import com.andcup.hades.hts.core.transfer.Transfer;
import org.springframework.stereotype.Service;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.DOWNLOADING, bind = Topic.DOWNLOADING)
@Service
public class DownloadComsumer extends MqConsumer {

    Message<Task> message;
    Transfer transfer;

    public Message.State execute(Message<Task> message) {
        this.message = message;
        transfer = new FtpTransfer(HadesRootConfig.sInstance.remote.ftp);
        transfer.dlFromRemote(message.getData().sourcePath, message.getData().localDir + message.getName());
        transfer = null;
        return Message.State.SUCCESS;
    }

    public void abort(Message<Task> message) {
        if( null != transfer){
            transfer.abort();
        }
    }
}
