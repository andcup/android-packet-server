package com.andcup.hades.hts.web.controller.cps;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.tools.FileUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import com.andcup.hades.hts.web.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.core.MqFactory;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;
import com.andcup.hades.hts.web.controller.cps.model.ResponseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 20:12.
 * Description:
 */
public class CpsMqFactory extends MqFactory<CpsTaskEntity> {

    FileInfo fileInfo;

    public CpsMqFactory(CpsTaskEntity entity) {
        super(entity);
    }

    public boolean checkFileIsExist() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.sourcePath = getFilePath();
        //获取文件信息.
        ResponseEntity<FileInfo> fileInfoEntity = new OKHttpClient(HadesRootConfig.sInstance.remote.file.url)
                .call(JsonConvertTool.toString(fileInfo),
                        JsonConvertTool.getCollectionType(ResponseEntity.class, FileInfo.class));
        //判断文件是否存在.
        if (fileInfoEntity.getCode() != ResponseEntity.SUCCESS) {
            return false;
        }
        this.fileInfo = fileInfoEntity.body;
        return true;
    }

    public boolean checkFileIsLatest() {
        //文件信息存储位置.
        String fileInfoPath = HadesRootConfig.sInstance.getApkTempDir() + getGroupId() + ".json";
        FileInfo localFileInfo = JsonConvertTool.toJson(new File(fileInfoPath), FileInfo.class);
        if( null != localFileInfo ){
            if(fileInfo.lastEditTime == localFileInfo.lastEditTime){
                //恢复本地文件状态.
                fileInfo.hasCompile = localFileInfo.hasCompile;
                fileInfo.downloaded = localFileInfo.downloaded;
            }else{
                return false;
            }
        }
        //保存配置文件.
        FileUtils.store(fileInfoPath, fileInfo);
        return true;
    }

    public String getGroupId() {
        return body.getId();
    }

    public List<Message<Task>> create() {
        List<Message<Task>> list = new ArrayList<Message<Task>>();
        List<CpsTaskEntity.Channel> channels = body.channels;
        for (CpsTaskEntity.Channel channel : channels) {
            Message msg = new Message();
            msg.setId(body.getId());
            msg.setName(body.getName());
            msg.setState(Message.State.ING);
            msg.setTopic(Topic.DOWNLOADING);

            Task data = new Task();
            data.id = String.valueOf(channel.id);
            data.sourcePath = body.originPackLocalPath + "_0.apk";
            data.channelDir = body.channelPackRemoteDir;
            data.priority = channel.priority;
            data.type = Integer.valueOf(body.packType);
            data.sourceId = String.valueOf( channel.sourceId);
            data.other = channel.other;
            data.feedback = body.feedbackApiAddress;
            data.localDir = HadesRootConfig.sInstance.getApkTempDir() + body.getName() + "/";

            new File(data.localDir).mkdir();

            msg.setData(data);
            list.add(msg);
        }
        return list;
    }

    protected String getFilePath() {
        return body.originPackLocalPath;
    }
}
