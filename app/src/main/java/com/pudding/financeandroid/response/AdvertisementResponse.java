package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.AdvertisementBean;

/**
 * 首页广告区图片的详情内容response
 *
 * Created by xiao.hongliang on 2016/9/4.
 */
public class AdvertisementResponse extends BaseResponse{

    private AdvertisementBean data;

    public AdvertisementBean getData() {
        return data;
    }

    public void setData(AdvertisementBean data) {
        this.data = data;
    }
}
