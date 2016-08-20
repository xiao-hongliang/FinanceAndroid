package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 贷款详情内容的bean
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanContentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
