package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 理财产品的标签bean，新手专享等
 *
 * Created by xiao.hongliang on 2016/8/22.
 */
public class FinancingLabelBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String labelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
