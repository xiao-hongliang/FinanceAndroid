package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 用户消息列表bean
 *
 * Created by xiao.hongliang on 2016/8/30.
 */
public class UserMessageBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String content;
    private String status;
    private String createTimeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
