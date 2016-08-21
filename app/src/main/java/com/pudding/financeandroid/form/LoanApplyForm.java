package com.pudding.financeandroid.form;

/**
 * 申请贷款提交页面的form
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class LoanApplyForm {

    private String productId;
    private String name;
    private String mobile;
    private String idNo;
    private String amount;
    private String stageDicItemId;

    public LoanApplyForm() {
    }

    public LoanApplyForm(String productId, String name, String mobile, String idNo, String amount, String stageDicItemId) {
        this.productId = productId;
        this.name = name;
        this.mobile = mobile;
        this.idNo = idNo;
        this.amount = amount;
        this.stageDicItemId = stageDicItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStageDicItemId() {
        return stageDicItemId;
    }

    public void setStageDicItemId(String stageDicItemId) {
        this.stageDicItemId = stageDicItemId;
    }
}
