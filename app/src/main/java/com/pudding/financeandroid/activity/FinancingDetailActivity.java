package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 我的理财详情页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class FinancingDetailActivity extends AbActivity{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.financing_detail);
        setStatusBar(R.color.title_bg);
        mContext = FinancingDetailActivity.this;
        AbTitleBar mAbTitleBar = this.getTitleBar();
        String title = "云南白药供应链贷款";
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, title);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
