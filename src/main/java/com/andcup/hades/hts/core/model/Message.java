package com.andcup.hades.hts.core.model;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/5 14:09.
 * Description:
 */

public class Message<T extends Task> {

    @JsonProperty("id")
    String      id;
    @JsonProperty("name")
    String      name;           // 消息名称
    @JsonProperty("data")
    Task task;
    @JsonProperty("timeout")
    long        timeout;        // 任务执行超时时间;
    @JsonProperty("createTime")
    long        createTime;     // 创建时间
    @JsonProperty("updateTime")
    long        updateTime;     // 更新时间
    @JsonProperty("state")
    State       state;          // 消息状态: NEW=新消息、ING=消费中、SUCCESS=消费成功、FAIL=消费失败
    @JsonProperty("lastState")
    State       lastState;      // 消息状态: NEW=新消息、ING=消费中、SUCCESS=消费成功、FAIL=消费失败
    @JsonProperty("msg")
    String      msg;            // 历史流转日志
    @JsonProperty("topic")
    Topic       topic;          // 消息主题

    /**
     * Consumer消费的消息级别.
     * */
    @JsonProperty("level")
    Consumer.Level level = Consumer.Level.LEVEL_ALL;

    public boolean equals(Message<Task> target) {
        return getId() == target.getId() && target.task.id.equals(task.id);
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setLevel(Consumer.Level level) {
        this.level = level;
    }

    public Consumer.Level getLevel() {
        return level;
    }

    public Topic getTopic() {
        return topic;
    }

    public State getState() {
        return state;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setData(T data) {
        this.task = data;
    }

    public Task getData() {
        return task;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public enum State{
        ING,        //ING=消费中
        SUCCESS,    //SUCCESS=消费成功
        FAILED      //FAIL=消费失败
    }
}
