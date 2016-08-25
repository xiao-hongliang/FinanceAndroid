package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 我的贷款订单bean
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class MyLoanBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String logo;
    private String productName;
    private String monthRateInfo;
    private String stageStr;
    private String amount;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMonthRateInfo() {
        return monthRateInfo;
    }

    public void setMonthRateInfo(String monthRateInfo) {
        this.monthRateInfo = monthRateInfo;
    }

    public String getStageStr() {
        return stageStr;
    }

    public void setStageStr(String stageStr) {
        this.stageStr = stageStr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
