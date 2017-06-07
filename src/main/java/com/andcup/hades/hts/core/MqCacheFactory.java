package com.andcup.hades.hts.core;

import com.andcup.hades.hts.config.F;
import com.andcup.hades.hts.core.model.Message;
import com.andcup.hades.hts.core.model.Task;
import com.andcup.hades.hts.core.tools.FileUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/31 14:57.
 * Description:
 */
public class MqCacheFactory extends MqFactory<MqCacheFactory.MqCache> {

    /**
     * 30S秒更新一次.
     * */
    final long updateInterval = 30 * 1000;
    long lastUpdate;

    public MqCacheFactory() {
        super(new MqCache());
    }

    public void remove(Message<Task> message){
        body.tasks.remove(message);
        update();
    }

    public void addAll(List<Message<Task>> tasks){
        body.tasks.addAll(tasks);
        update();
    }

    private void update(){
        long nowTime = System.currentTimeMillis();
        /**
         * 任务小于30个或者更新周期大于updateInterval 时。立即写入.
         * */
        if(nowTime - lastUpdate > updateInterval || body.tasks.size() <= 30){
            lastUpdate = nowTime;
            FileUtils.store(F.CACHE, JsonConvertTool.toString(body));
        }
    }

    @Override
    public List<Message<Task>> create() {
        try{
            MqCache mqCache = JsonConvertTool.toJson(new File(F.CACHE), MqCache.class);
            if( null != mqCache){
                return mqCache.tasks;
            }
        }catch (Exception e){

        }
        return null;
    }

    static class MqCache{
        @JsonProperty("list")
        public List<Message<Task>> tasks = new ArrayList<>();
    }
}
