package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.InfoBean;

/**
 * 资讯详情的接口内容响应解析response
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class InfoDetailResponse extends BaseResponse{

    private InfoBean data;

    public InfoBean getData() {
        return data;
    }

    public void setData(InfoBean data) {
        this.data = data;
    }
}
