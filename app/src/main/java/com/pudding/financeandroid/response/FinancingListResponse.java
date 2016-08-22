package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.FinancingBean;

import java.util.List;

/**
 * 解析理财列表接口的json
 *
 * Created by xiao.hongliang on 2016/8/22.
 */
public class FinancingListResponse extends BaseResponse{

    private List<FinancingBean> data;

    public List<FinancingBean> getData() {
        return data;
    }

    public void setData(List<FinancingBean> data) {
        this.data = data;
    }
}
