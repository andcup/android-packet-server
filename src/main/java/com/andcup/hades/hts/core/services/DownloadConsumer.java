package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.transfer.FtpTransfer;
import com.andcup.hades.hts.core.transfer.Transfer;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.DOWNLOADING, bind = Topic.COMPRESS, last = State.DEFAULT)
public class DownloadConsumer extends MqConsumer {

    /**
     * 下载文件.
     * */
    public State doInBackground(Message<Task> message) throws ConsumeException{

        Transfer transfer = new FtpTransfer(HadesRootConfigure.sInstance.remote.ftp);
        Task task = message.getData();
        if(!Task.Global.hasDownloaded(task)){
            transfer.dlFromRemote(message.getData().sourcePath, Task.Helper.getApkPath(message.getData()));
            Task.Global.setHasDownloaded(task, true);
        }
        return State.SUCCESS;
    }

    public void abort(Message<Task> message) {
    }
}
