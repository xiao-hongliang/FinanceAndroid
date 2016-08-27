package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.UserInfoBean;

/**
 * 个人信息接口响应response
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class UserInfoResponse extends BaseResponse{

    private UserInfoBean data;

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }
}
