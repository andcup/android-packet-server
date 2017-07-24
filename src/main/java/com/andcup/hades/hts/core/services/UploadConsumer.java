package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.boot.HadesApplication;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.State;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.OssUtil;
import com.andcup.hades.hts.core.transfer.ftp4j.Ftp4JTransfer;
import com.andcup.hades.hts.core.transfer.Transfer;
import com.andcup.hades.httpserver.utils.LogUtils;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description:
 */

@Consumer(topic = Topic.UPLOADING, bind = Topic.GARBAGE_CLEAN)
public class UploadConsumer extends MqConsumer {

    public State doInBackground(Message<Task> message) throws ConsumeException{
        Task task = message.getData();
        Transfer transfer = new Ftp4JTransfer(HadesApplication.sInstance.f().to);
        String signedApk = Task.Helper.getChannelPath(task);
        transfer.upToRemote(signedApk, task.channelPath);
        /*
        if(task.type == Task.TYPE_IOS_QUICK){
            String localPath = Task.Helper.getChannelPath(task);
            String remotePath = task.channelPath;
            LogUtils.info(OssUtil.class, " 开始上传 : " + localPath + " 到 " + remotePath + " 服务器:" + task.oss.endpoint);
            try{
                OssUtil.uploadFile(task.oss, localPath, remotePath);
                LogUtils.info(OssUtil.class, " 上传 : " + localPath + " 到 " + remotePath + " 成功 " + " 服务器:" + task.oss.endpoint);
                return State.SUCCESS;
            }catch (Exception e){
                LogUtils.info(OssUtil.class, " 上传 : " + localPath + " 到 " + remotePath + " error : " + e.getMessage() + " 服务器:" + task.oss.endpoint);
            }
        }else{
            Transfer transfer = new Ftp4JTransfer(HadesApplication.sInstance.f().to);
            String signedApk = Task.Helper.getChannelPath(task);
            transfer.upToRemote(signedApk, task.channelPath);
        }
        */
        return State.SUCCESS;
    }
}
