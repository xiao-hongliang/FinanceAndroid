package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.InfoBean;
import com.pudding.financeandroid.bean.LoanContentBean;
import com.pudding.financeandroid.response.InfoDetailResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

import java.util.List;

/**
 * 公司模块，资讯的详情页面
 *
 * Created by xiao.hongliang on 2016/8/25.
 */
public class InfoDetailActivity extends AbActivity{
    private static final String TAG = InfoDetailActivity.class.getName();
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private AbImageLoader mAbImageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.info_detail);
        setStatusBar(R.color.title_bg);
        mContext = InfoDetailActivity.this;
        ri = new RequestImpl(mContext);
        this.mAbImageLoader = AbImageLoader.getInstance(mContext);
        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.info_detail);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = this.getIntent();
        String infoId = intent.getStringExtra("infoId");
        httpPost(infoId);
    }

    private void initInfoDetailView(InfoBean infoBean) {
        TextView infoTitleTv = (TextView) this.findViewById(R.id.info_detail_title);
        infoTitleTv.setText(infoBean.getTitle());
        TextView infoCreateTimeTv = (TextView) this.findViewById(R.id.info_detail_createTime);
        infoCreateTimeTv.setText(infoBean.getCreateTimeStr());
//        ImageView infoLogoIv = (ImageView) this.findViewById(R.id.info_detail_logo);
//        this.mAbImageLoader.display(infoLogoIv, infoBean.getLogo());

        //迭代加载显示资讯详情的内容
        LinearLayout contentView = (LinearLayout) this.findViewById(R.id.info_detail_content);
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        List<LoanContentBean> contentBeanList = infoBean.getRichTextContent();
        for(LoanContentBean contentBean : contentBeanList) {
            if("img".equals(contentBean.getType())) {
                ImageView imageView = (ImageView) mInflater.inflate(R.layout.loan_detail_img, null);
                mAbImageLoader.display(imageView, contentBean.getContent());
                contentView.addView(imageView);
            }else {
                TextView textView = (TextView) mInflater.inflate(R.layout.loan_detail_text, null);
                textView.setText(contentBean.getContent());
                contentView.addView(textView);
            }
        }
    }

    private void httpPost(String infoId) {
        ri.companyInfoDetail(infoId, new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
//                 AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(mContext);
                Log.d(TAG, "onFinish");
            }
            // 失败，调用
            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                AbToastUtil.showToast(mContext, error.getMessage());
                Log.v(TAG, "onFailure");
            }
            // 获取数据成功会调用这里
            public void onSuccess(int statusCode, String content) {
                try{
                    InfoDetailResponse bean = (InfoDetailResponse) AbJsonUtil.fromJson(content, InfoDetailResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        //加载贷款详情数据
                        if(bean.getData() != null){
                            initInfoDetailView(bean.getData());
                        }
                    } else {
                        AbToastUtil.showToast(mContext, bean.getMsg());
                    }
                }catch(Exception e) {
                    Log.v(TAG, "Home加载数据异常！" + e.getMessage());
                }
            }
        });
    }
}
