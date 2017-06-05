package com.andcup.hades.hts.core.services;

import com.andcup.hades.hts.core.MqConsumer;
import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.model.*;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/23 11:45.
 * Description:
 */

@Consumer(topic = Topic.COMPLETE, bind = Topic.COMPLETE, last = State.DEFAULT)
public class CompleteConsumer extends MqConsumer{

    final String groupId    = "groupId";
    final String id         = "id";
    final String code       = "code";
    final String msg        = "message";

    @Override
    public State doInBackground(Message<Task> message) throws ConsumeException {
        message.setUpdateTime(System.currentTimeMillis());
        Task task = message.getData();
        Map<String, String> maps = new HashMap<>();
        maps.put(groupId, task.groupId);
        maps.put(id, task.id);
        maps.put(code, String.valueOf(message.getLastState() == State.SUCCESS ? 0 : -1));
        try {
            maps.put(msg, URLEncoder.encode(message.getMsg(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String result = new OKHttpClient(task.feedback).call(maps);
            Response response = JsonConvertTool.toJson(result,Response.class);
            if(response.getCode() < 0){
                throw new ConsumeException(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        }
        return State.SUCCESS;
    }
}
