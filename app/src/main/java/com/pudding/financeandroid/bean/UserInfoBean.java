package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 个人用户信息
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class UserInfoBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String pushId;
    private String userName;
    private String id;
    private String phone;
    private String nickname;
    private String logo;
    private String status;
    private String name;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
