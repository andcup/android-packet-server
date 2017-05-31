package com.andcup.hades.hts.core;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.tools.FileUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/31 14:57.
 * Description:
 */
public class MqCacheFactory extends MqFactory<MqCacheFactory.MqCache> {

    public MqCacheFactory() {
        super(new MqCache());
    }

    public void remove(Message<Task> message){
        body.tasks.remove(message);
        FileUtils.store(HadesRootConfigure.sInstance.db, JsonConvertTool.toString(body));
    }

    public void addAll(List<Message<Task>> tasks){
        body.tasks.addAll(tasks);
        FileUtils.store(HadesRootConfigure.sInstance.db, JsonConvertTool.toString(body));
    }

    @Override
    public List<Message<Task>> create() {
        MqCache mqCache = JsonConvertTool.toJson(new File(HadesRootConfigure.sInstance.db), MqCache.class);
        if( null != mqCache){
            return mqCache.tasks;
        }
        return null;
    }

    static class MqCache{
        @JsonProperty("list")
        public List<Message<Task>> tasks = new ArrayList<>();
    }
}
