package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.LoanBean;

import java.util.List;

/**
 * 解析贷款列表接口的json
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanListResponse extends BaseResponse{
    private List<LoanBean> data;

    public List<LoanBean> getData() {
        return data;
    }

    public void setData(List<LoanBean> data) {
        this.data = data;
    }
}
