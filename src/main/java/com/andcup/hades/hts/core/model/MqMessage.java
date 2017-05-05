package com.andcup.hades.hts.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos
 * Date : 2017/5/5 14:09.
 * Description:
 */

public class MqMessage<T> {

    @JsonProperty("id")
    String      id;
    @JsonProperty("name")
    String      name;           // 消息名称
    @JsonProperty("data")
    T           data;           // 消息数据, Map<String, String>对象系列化的JSON字符串
    @JsonProperty("timeout")
    long        timeout;        // 任务执行超时时间;
    @JsonProperty("createTime")
    long        createTime;        // 创建时间
    @JsonProperty("updateTime")
    long        updateTime;     // 更新时间
    @JsonProperty("state")
    State       state;         // 消息状态: NEW=新消息、ING=消费中、SUCCESS=消费成功、FAIL=消费失败
    @JsonProperty("msg")
    String      msg;            // 历史流转日志
    @JsonProperty("topic")
    Topic       topic;          // 消息主题

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public State getState() {
        return state;
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
        this.data = data;
    }

    public T getData() {
        return data;
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
