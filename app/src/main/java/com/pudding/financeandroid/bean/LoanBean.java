package com.pudding.financeandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 贷款列表的数据bean
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String logo;
    private String name;
    private LoanTypeBean loanType;
    //月利率
    private String monthRateInfo;
    //贷款详情内容
    private List<LoanContentBean> richTextContent;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthRateInfo() {
        return monthRateInfo;
    }

    public void setMonthRateInfo(String monthRateInfo) {
        this.monthRateInfo = monthRateInfo;
    }

    public LoanTypeBean getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanTypeBean loanType) {
        this.loanType = loanType;
    }

    public List<LoanContentBean> getRichTextContent() {
        return richTextContent;
    }

    public void setRichTextContent(List<LoanContentBean> richTextContent) {
        this.richTextContent = richTextContent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
