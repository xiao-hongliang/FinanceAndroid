package com.pudding.financeandroid.form;

/**
 * 用户注册提交页面的form
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class UserRegisterForm {

    private String pushId;
    private String code;
    private String mobile;
    private String password;
    private String referrerMobile;

    public UserRegisterForm() {
    }

    public UserRegisterForm(String pushId, String code, String mobile, String password, String referrerMobile) {
        this.pushId = pushId;
        this.code = code;
        this.mobile = mobile;
        this.password = password;
        this.referrerMobile = referrerMobile;
    }

    public UserRegisterForm(String pushId, String code, String mobile, String password) {
        this.pushId = pushId;
        this.code = code;
        this.mobile = mobile;
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferrerMobile() {
        return referrerMobile;
    }

    public void setReferrerMobile(String referrerMobile) {
        this.referrerMobile = referrerMobile;
    }
}
