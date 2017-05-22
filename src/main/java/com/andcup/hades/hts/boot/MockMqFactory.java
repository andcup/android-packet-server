package com.andcup.hades.hts.boot;

import com.andcup.hades.hts.core.MqFactory;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/22 15:54.
 * Description:
 */
public class MockMqFactory extends MqFactory<List<Task>>{

    public MockMqFactory(List<Task> tasks) {
        super(tasks);
    }

    @Override
    public List<Message<Task>> create() {
        List<Message<Task>> messages = new ArrayList<>();
        for(Task task : body){
            Message message = new Message();
            message.setId(task.groupId + task.id);
            message.setName(task.name);
            message.setState(Message.State.ING);
            message.setTopic(Topic.DOWNLOADING);

            message.setData(task);
            messages.add(message);
        }
        return messages;
    }
}
