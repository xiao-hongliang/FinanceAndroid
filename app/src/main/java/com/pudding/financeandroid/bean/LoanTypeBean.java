package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 贷款类型的bean
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanTypeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
