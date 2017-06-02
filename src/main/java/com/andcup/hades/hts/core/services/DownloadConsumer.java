package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.Transfer;
import com.andcup.hades.hts.core.transfer.commonnet.CommonNetFtpTransfer;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.DOWNLOADING, bind = Topic.IPA_COMPRESS, last = State.DEFAULT)
public class DownloadConsumer extends MqConsumer {

    /**
     * 下载文件.
     * */
    public State doInBackground(Message<Task> message) throws ConsumeException{

        Transfer transfer = new CommonNetFtpTransfer(Hades.sInstance.f.from);
        Task task = message.getData();
        if(!Task.Global.hasDownloaded(task)){
            transfer.dlFromRemote(message.getData().sourcePath, Task.Helper.getDownloadPath(message.getData()));
            Task.Global.setHasDownloaded(task, true);
        }
        return State.SUCCESS;
    }

    public void abort(Message<Task> message) {
    }
}
