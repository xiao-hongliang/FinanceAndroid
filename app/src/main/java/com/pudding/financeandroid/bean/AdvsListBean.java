package com.pudding.financeandroid.bean;

import java.io.Serializable;

/**
 * 首页广告区的图片bean
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class AdvsListBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String logo;

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
}
