package com.andcup.hades.hts.controller.cps;

import com.andcup.hades.hts.controller.cps.model.CpsTaskEntity;
import com.andcup.hades.hts.core.MqFactory;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.MqMessage;

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

    public List<MqMessage<Message>> create() {
        List<MqMessage<Message>> list = new ArrayList<MqMessage<Message>>();
        List<CpsTaskEntity.Channel> channels = body.channels;
        for (CpsTaskEntity.Channel channel : channels) {
            MqMessage msg = new MqMessage();
            msg.setId(String.valueOf(channel.gamePid));
            msg.setName(body.originPackLocalPath);
            msg.setState(MqMessage.State.ING);

            Message data = new Message();
            data.id = String.valueOf(channel.id);
            data.sourcePath = body.originPackLocalPath;
            data.channleDir = body.channelPackRemoteDir;
            data.priority = channel.priority;
            data.type = Integer.valueOf(body.packType);
            data.rule = String.format(getRule(data), channel.id, channel.sourceId, channel.other);

            msg.setData(data);

            list.add(msg);
        }
        return list;
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
