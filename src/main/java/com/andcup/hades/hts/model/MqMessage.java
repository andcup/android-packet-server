package com.andcup.hades.hts.model;

/**
 * Created by Amos
 * Date : 2017/5/5 14:09.
 * Description:
 */
public class MqMessage<T> {

    int         id;
    String      name;           // 消息名称
    T           data;           // 消息数据, Map<String, String>对象系列化的JSON字符串
    long        timeout;        // 任务执行超时时间;
    long        addTime;        // 创建时间
    long        updateTime;     // 更新时间
    State       state;         // 消息状态: NEW=新消息、ING=消费中、SUCCESS=消费成功、FAIL=消费失败
    String      msg;            // 历史流转日志
    Topic       topic;          // 消息主题

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public State getState() {
        return state;
    }

    public enum State{
        ING,        //ING=消费中
        SUCCESS,    //SUCCESS=消费成功
        FAILED      //FAIL=消费失败
    }
}
