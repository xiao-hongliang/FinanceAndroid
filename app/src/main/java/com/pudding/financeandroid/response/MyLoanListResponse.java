package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.MyFinancingBean;
import com.pudding.financeandroid.bean.MyLoanBean;

import java.util.List;

/**
 * 解析我的贷款列表接口的json
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class MyLoanListResponse extends BaseResponse{

    private List<MyLoanBean> data;

    public List<MyLoanBean> getData() {
        return data;
    }

    public void setData(List<MyLoanBean> data) {
        this.data = data;
    }
}
