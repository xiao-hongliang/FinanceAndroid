package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TelNumMatch;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 申请投资购买信息填报页面
 *
 * Created by xiao.hongliang on 2016/8/19.
 */
public class ApplyInvestActivity extends AbActivity{

    private Context mContext;
    private TextView investNameTv;
    private TextView investPhoneTv;
    private TextView investIdCardTv;
    private TextView investNumTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.apply_invest);
        setStatusBar(R.color.title_bg);
        mContext = ApplyInvestActivity.this;
        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.apply_invest);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        investNameTv = (TextView) this.findViewById(R.id.invest_name);
        investPhoneTv = (TextView) this.findViewById(R.id.invest_phone);
        investIdCardTv = (TextView) this.findViewById(R.id.invest_idCard);
        investNumTv = (TextView) this.findViewById(R.id.invest_num);

        //点击提交事件绑定
        this.findViewById(R.id.submit_apply_invest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String investName = investNameTv.getText().toString().trim();
                if("".equals(investName)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_name);
                    return;
                }
                String investPhone = investPhoneTv.getText().toString().trim();
                if("".equals(investPhone)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_phone);
                    return;
                }
                String investIdCard = investIdCardTv.getText().toString().trim();
                if("".equals(investIdCard)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_idCard);
                    return;
                }
                String investNum = investNumTv.getText().toString().trim();
                if("".equals(investNum)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_num);
                    return;
                }
                //可以做点提交的事情了
                AbToastUtil.showToast(mContext, "可以提交了");
            }
        });
    }
}
