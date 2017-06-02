package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.ftp4j.Ftp4JTransfer;
import com.andcup.hades.hts.core.transfer.Transfer;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.UPLOADING, bind = Topic.GARBAGE_CLEAN)
public class UploadConsumer extends MqConsumer {

    public State doInBackground(Message<Task> message) throws ConsumeException{

        Transfer transfer = new Ftp4JTransfer(Hades.sInstance.f.to);
        Task task = message.getData();
        String signedApk = Task.Helper.getChannelPath(task);
        transfer.upToRemote(signedApk, task.channelPath);

        return State.SUCCESS;
    }
}
