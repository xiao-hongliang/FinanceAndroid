package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.MyFinancingBean;

import java.util.List;

/**
 * 解析我的理财列表接口的json
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class MyFinancingResponse extends BaseResponse{

    private List<MyFinancingBean> data;

    public List<MyFinancingBean> getData() {
        return data;
    }

    public void setData(List<MyFinancingBean> data) {
        this.data = data;
    }
}
