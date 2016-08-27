package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.MyIncomeDetailBean;

import java.util.List;

/**
 * 我的收益明细列表接口响应response
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MyIncomeDetailResponse extends BaseResponse{

    List<MyIncomeDetailBean> data;

    public List<MyIncomeDetailBean> getData() {
        return data;
    }

    public void setData(List<MyIncomeDetailBean> data) {
        this.data = data;
    }
}
