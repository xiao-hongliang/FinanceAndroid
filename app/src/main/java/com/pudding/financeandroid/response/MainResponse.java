package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.MainBean;

/**
 * 首页接口的响应response
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MainResponse extends BaseResponse{

    private MainBean data;

    public MainBean getData() {
        return data;
    }

    public void setData(MainBean data) {
        this.data = data;
    }
}
