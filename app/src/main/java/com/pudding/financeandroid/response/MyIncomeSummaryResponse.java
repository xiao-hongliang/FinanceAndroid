package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.MyIncomeDetailBean;
import com.pudding.financeandroid.bean.MyIncomeSummaryBean;

import java.util.List;

/**
 * 我的收益概况接口响应response
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MyIncomeSummaryResponse extends BaseResponse{

    private MyIncomeSummaryBean data;

    public MyIncomeSummaryBean getData() {
        return data;
    }

    public void setData(MyIncomeSummaryBean data) {
        this.data = data;
    }
}
