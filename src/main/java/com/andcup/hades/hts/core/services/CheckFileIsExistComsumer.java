package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.config.HadesRootConfig;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.core.tools.FileUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/5 17:31.
 * Description: 获取远程服务器信息.
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
        //文件信息存储位置.
        String fileInfoPath = HadesRootConfig.sInstance.getApkTempDir() + message.getId() + ".json";
        FileInfo localFileInfo = JsonConvertTool.toJson(new File(fileInfoPath), FileInfo.class);
        if( null != localFileInfo ){
            if(fileInfoEntity.body.lastEditTime != localFileInfo.lastEditTime){
                //需要中断现有的打包任务.
                abort(message.getId());
            }else{
                fileInfoEntity.body.hasCompile = localFileInfo.hasCompile;
                fileInfoEntity.body.downloaded = localFileInfo.downloaded;
            }
        }
        //保存配置文件.
        FileUtils.store(fileInfoPath, fileInfoEntity.body);
        return MqMessage.State.SUCCESS;
    }

}
