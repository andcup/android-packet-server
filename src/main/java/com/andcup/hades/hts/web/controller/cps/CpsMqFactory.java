package com.andcup.hades.hts.web.controller.cps;

import com.andcup.hades.hts.HadesRootConfig;
import com.andcup.hades.hts.core.model.Channel;
import com.andcup.hades.hts.web.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.core.MqFactory;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Topic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 20:12.
 * Description:
 */
public class CpsMqFactory extends MqFactory<CpsTaskEntity> {

    public CpsMqFactory(CpsTaskEntity entity) {
        super(entity);
    }

    public String getGroupId() {
        return body.getId();
    }

    public boolean checkFileIsLatest() {
        return false;
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
            data.channel = new Channel(channel.id, channel.sourceId, channel.other);
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
