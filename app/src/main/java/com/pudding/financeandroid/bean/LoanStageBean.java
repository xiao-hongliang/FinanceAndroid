package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 贷款申请页面的期限列表bean
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class LoanStageBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
