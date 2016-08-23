package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 公司咨询bean
 *
 * Created by xiao.hongliang on 2016/8/23.
 */
public class InfoBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String logo;
    private String createTimeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
