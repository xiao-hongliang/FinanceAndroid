package com.pudding.financeandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 理财列表的bean
 *
 * Created by xiao.hongliang on 2016/8/22.
 */
public class FinancingBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    //年化收益
    private String yearRateStr;
    //起投金额
    private Integer minAmount;
    //投资期限
    private String investTimeName;
    private String name;
    private FinancingLabelBean labelData;

    private String phone;
    //理财详情内容
    private List<LoanContentBean> richTextContent;
    //总金额
    private String totalAmount;
    //已经完成金额
    private String finishAmount;
    //可购金额对应
    private String maxAmount;
    //还款方式
    private FinancingInterestPolicyBean interestPolicy;
    //完成进度
    private String finishRateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYearRateStr() {
        return yearRateStr;
    }

    public void setYearRateStr(String yearRateStr) {
        this.yearRateStr = yearRateStr;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public String getInvestTimeName() {
        return investTimeName;
    }

    public void setInvestTimeName(String investTimeName) {
        this.investTimeName = investTimeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FinancingLabelBean getLabelData() {
        return labelData;
    }

    public void setLabelData(FinancingLabelBean labelData) {
        this.labelData = labelData;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<LoanContentBean> getRichTextContent() {
        return richTextContent;
    }

    public void setRichTextContent(List<LoanContentBean> richTextContent) {
        this.richTextContent = richTextContent;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(String finishAmount) {
        this.finishAmount = finishAmount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public FinancingInterestPolicyBean getInterestPolicy() {
        return interestPolicy;
    }

    public void setInterestPolicy(FinancingInterestPolicyBean interestPolicy) {
        this.interestPolicy = interestPolicy;
    }

    public String getFinishRateStr() {
        return finishRateStr;
    }

    public void setFinishRateStr(String finishRateStr) {
        this.finishRateStr = finishRateStr;
    }

}
