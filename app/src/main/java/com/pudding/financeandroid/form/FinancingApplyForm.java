package com.pudding.financeandroid.form;

/**
 * 投资申请提交页面的form
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class FinancingApplyForm {

    private String productId;
    private String name;
    private String mobile;
    private String idNo;
    private String count;

    public FinancingApplyForm() {
    }

    public FinancingApplyForm(String productId, String name, String mobile, String idNo, String count) {
        this.productId = productId;
        this.name = name;
        this.mobile = mobile;
        this.idNo = idNo;
        this.count = count;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
