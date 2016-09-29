package com.pudding.financeandroid.bean;

/**
 * 图片的尺寸大小bean
 *
 * Created by xiao.hongliang on 2016/9/29.
 */
public class ImgSizeBean {

    private Integer width;
    private Integer height;

    public ImgSizeBean() {
    }

    public ImgSizeBean(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
