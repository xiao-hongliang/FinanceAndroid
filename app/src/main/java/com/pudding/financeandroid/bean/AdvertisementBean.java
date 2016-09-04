package com.pudding.financeandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 首页广告区图片的详情内容bean
 *
 * Created by xiao.hongliang on 2016/9/4.
 */
public class AdvertisementBean extends AdvsListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createTimeStr;
    //广告详情内容
    private List<LoanContentBean> richTextContent;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<LoanContentBean> getRichTextContent() {
        return richTextContent;
    }

    public void setRichTextContent(List<LoanContentBean> richTextContent) {
        this.richTextContent = richTextContent;
    }
}
