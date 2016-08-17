package com.pudding.financeandroid.bean;

/**
 * 首页六个格子的数据bean
 *
 * Created by xiao.hongliang on 2016/8/17.
 */
public class ApplicationBean {

    private int name;
    //小图标
    private int icon;
    //背景颜色
    private int color;
    //右上角红色标签的图标
    private int flagIcon;
    //右上角红色标签的文字
    private String flagName;

    public ApplicationBean(int name, int icon, int color, int flagIcon, String flagName) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.flagIcon = flagIcon;
        this.flagName = flagName;
    }

    public ApplicationBean(int name, int icon, int color) {
        this.name = name;
        this.icon = icon;
        this.color = color;
    }

    public ApplicationBean() {
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFlagIcon() {
        return flagIcon;
    }

    public void setFlagIcon(int flagIcon) {
        this.flagIcon = flagIcon;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}
