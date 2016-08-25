package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 我的理财订单bean
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class MyFinancingBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    //购买份数
    private String count;
    //产品名称
    private String productName;
    //投资总额
    private String amount;
    //投资总额
    private String yearRateStr;
    //投资总额
    private String minAmount;
    //投资总额
    private String investTimeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getYearRateStr() {
        return yearRateStr;
    }

    public void setYearRateStr(String yearRateStr) {
        this.yearRateStr = yearRateStr;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getInvestTimeName() {
        return investTimeName;
    }

    public void setInvestTimeName(String investTimeName) {
        this.investTimeName = investTimeName;
    }
}
