package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.LoanStageBean;

import java.util.List;

/**
 * 解析申请贷款先期列表接口的json
 *
 * Created by xiao.hongliang on 2016/8/21.
 */
public class LoanStageListResponse extends BaseResponse{

    private List<LoanStageBean> data;

    public List<LoanStageBean> getData() {
        return data;
    }

    public void setData(List<LoanStageBean> data) {
        this.data = data;
    }
}
