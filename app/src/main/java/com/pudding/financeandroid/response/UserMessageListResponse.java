package com.pudding.financeandroid.response;

import com.pudding.financeandroid.bean.UserMessageBean;

import java.util.List;

/**
 * 用户消息列表接口的解析response
 *
 * Created by xiao.hongliang on 2016/8/30.
 */
public class UserMessageListResponse extends BaseResponse{

    private List<UserMessageBean> data;

    public List<UserMessageBean> getData() {
        return data;
    }

    public void setData(List<UserMessageBean> data) {
        this.data = data;
    }
}
