package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView percentageLeftTv;
    private TextView percentageRightTv;

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

        percentageLeftTv = (TextView) this.findViewById(R.id.percentage_left);
        percentageRightTv = (TextView) this.findViewById(R.id.percentage_right);
        //LayoutParams参数依次为，宽度，高度，权重占比
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, 12, (float) 64);
        percentageLeftTv.setLayoutParams(p);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0, 12, (float) 36);
        percentageRightTv.setLayoutParams(p2);

        //客服点击事件绑定
        this.findViewById(R.id.tel_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "13700691190";
                Intent phoneIntent = new Intent( "android.intent.action.CALL", Uri.parse("tel:"+ phone));
                startActivity(phoneIntent);
            }
        });
        //投资购买点击事件绑定
        this.findViewById(R.id.apply_invest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ApplyInvestActivity.class);
                startActivity(intent);
            }
        });
    }
}
