package com.andcup.hades.hts.model;

import org.hibernate.annotations.Entity;

/**
 * Created by Amos
 * Date : 2017/4/28 10:39.
 * Description:
 */

@Entity
public class Message {

    /**任务ID. */
    String      groupId;

    /**
     * 任务名称.
     * */
    String      name;
    /**
     * 子包信息.
     * */
    Child       child;
    /**
     * 母包路径.
     * */
    String      sourceFile;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Child getChild() {
        return child;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getGroupId() {
        return groupId;
    }

    public static class Server{
        int    type;
        String username;
        String password;
        String url;
        int    port;
    }

    public static class Child{
        /**子包ID.*/
        String id;
        /**写入的数据.*/
        String rule;
        /**打包类型.*/
        int    type;
        /**优先级. */
        int    priority;

        public int    getPriority() {
            return priority;
        }

        public String getId() {
            return id;
        }

        public String getRule() {
            return rule;
        }

        public int  getType() {
            return type;
        }
    }
}
