package com.pudding.financeandroid.form;

/**
 * 用户登录的from
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class UserLoginForm {
    private String pushId;
    private String userName;
    private String password;

    public UserLoginForm() {
    }

    public UserLoginForm(String pushId, String userName, String password) {
        this.pushId = pushId;
        this.userName = userName;
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
