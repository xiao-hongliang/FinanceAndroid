package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.FinancingBean;

import java.util.List;

/**
 * 解析理财详情接口的json
 *
 * Created by xiao.hongliang on 2016/8/22.
 */
public class FinancingDetailResponse extends BaseResponse{

    private FinancingBean data;

    public FinancingBean getData() {
        return data;
    }

    public void setData(FinancingBean data) {
        this.data = data;
    }
}
