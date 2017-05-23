package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.transfer.FtpTransfer;
import com.andcup.hades.hts.core.transfer.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.DOWNLOADING, bind = Topic.COMPRESS)
public class DownloadComsumer extends MqConsumer {

    Transfer        transfer;

    public DownloadComsumer(){
        transfer = new FtpTransfer(HadesRootConfig.sInstance.remote.ftp);
    }

    /**
     * 下载文件.
     * */
    public Message.State doInBackground(Message<Task> message) throws ConsumeException{
        transfer.dlFromRemote(message.getData().sourcePath, Task.Helper.getApkPath(message.getData()));
        return Message.State.SUCCESS;
    }

    public void abort(Message<Task> message) {
        if( null != transfer){
            transfer.abort();
        }
    }
}
