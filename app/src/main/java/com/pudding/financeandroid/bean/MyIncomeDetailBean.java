package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 我的收益明细列表bean
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MyIncomeDetailBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String amount;
    private String dataTitle;
    private String typeName;
    private String tradeTimeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTradeTimeStr() {
        return tradeTimeStr;
    }

    public void setTradeTimeStr(String tradeTimeStr) {
        this.tradeTimeStr = tradeTimeStr;
    }
}
