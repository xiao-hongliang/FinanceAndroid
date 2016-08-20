package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.LoanBean;

/**
 * 解析贷款详情接口的json
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanDetailResponse extends BaseResponse{
    private LoanBean data;

    public LoanBean getData() {
        return data;
    }

    public void setData(LoanBean data) {
        this.data = data;
    }
}
