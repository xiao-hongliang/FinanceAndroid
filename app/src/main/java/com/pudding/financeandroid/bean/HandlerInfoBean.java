package com.pudding.financeandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 资讯列表Handler传递的数据bean
 *
 * Created by xiao.hongliang on 2016/8/24.
 */
public class HandlerInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private List<InfoBean> handlerData;

    public HandlerInfoBean() {
    }

    public HandlerInfoBean(String type, List<InfoBean> handlerData) {
        this.type = type;
        this.handlerData = handlerData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<InfoBean> getHandlerData() {
        return handlerData;
    }

    public void setHandlerData(List<InfoBean> handlerData) {
        this.handlerData = handlerData;
    }
}
