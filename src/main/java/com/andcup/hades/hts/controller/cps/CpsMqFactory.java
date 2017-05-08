package com.andcup.hades.hts.controller.cps;

import com.andcup.hades.hts.config.HadesRootConfig;
import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.core.MqFactory;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;
import com.andcup.hades.hts.core.model.Topic;

import java.io.File;
import java.io.FileNotFoundException;
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

    public List<MqMessage<Message>> create() {
        List<MqMessage<Message>> list = new ArrayList<MqMessage<Message>>();
        List<CpsTaskEntity.Channel> channels = body.channels;
        for (CpsTaskEntity.Channel channel : channels) {
            MqMessage msg = new MqMessage();
            msg.setId(body.getId());
            msg.setName(body.getName());
            msg.setState(MqMessage.State.ING);
            msg.setTopic(Topic.CHECK_FILE_EXIST);

            Message data = new Message();
            data.id = String.valueOf(channel.id);
            data.sourcePath = body.originPackLocalPath + "_0.apk";
            data.channleDir = body.channelPackRemoteDir;
            data.priority = channel.priority;
            data.type = Integer.valueOf(body.packType);
            data.rule = String.format(getRule(data), channel.id, channel.sourceId, channel.other);
            data.body = body.attachData;
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

    private String getRule(Message message) {
        return message.type == Message.TYPE_QUICK ? RULE_QUICK : RULE_COMPILE;
    }

    String RULE_QUICK = "yl_introduction_%s_sourceid_%s_other_%s";

    String RULE_COMPILE = "<meta-data\n" +
            "            android:name=\"introduction\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"sourceid\"\n" +
            "            android:value=\"%s\" />\n" +
            "        <meta-data\n" +
            "            android:name=\"other\"\n" +
            "            android:value=\"%s\" />";
}
