package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 理财还款方式
 *
 * Created by xiao.hongliang on 2016/8/22.
 */
public class FinancingInterestPolicyBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
