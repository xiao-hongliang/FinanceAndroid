package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.InfoBean;

import java.util.List;

/**
 * 资讯的响应response
 *
 * Created by xiao.hongliang on 2016/8/24.
 */
public class InfoResponse extends BaseResponse{

    private List<InfoBean> data;

    public List<InfoBean> getData() {
        return data;
    }

    public void setData(List<InfoBean> data) {
        this.data = data;
    }
}
