package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 我的收益明细列表bean
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MyIncomeSummaryBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String investAmount;
    private String incomeAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}
