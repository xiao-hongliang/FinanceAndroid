package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 申请贷款的页面
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class ApplyLoanActivity extends AbActivity{

    private Context mContext;
    private TextView loanNameTv;
    private TextView loanPhoneTv;
    private TextView loanIdCardTv;
    private TextView loanPriceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.apply_loan);
        setStatusBar(R.color.title_bg);
        mContext = ApplyLoanActivity.this;
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

        loanNameTv = (TextView) this.findViewById(R.id.loan_name);
        loanPhoneTv = (TextView) this.findViewById(R.id.loan_phone);
        loanIdCardTv = (TextView) this.findViewById(R.id.loan_idCard);
        loanPriceTv = (TextView) this.findViewById(R.id.loan_price);

        //点击提交事件绑定
        this.findViewById(R.id.submit_apply_loan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loanName = loanNameTv.getText().toString().trim();
                if("".equals(loanName)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_name);
                    return;
                }
                String loanPhone = loanPhoneTv.getText().toString().trim();
                if("".equals(loanPhone)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_phone);
                    return;
                }
                String loanIdCard = loanIdCardTv.getText().toString().trim();
                if("".equals(loanIdCard)) {
                    AbToastUtil.showToast(mContext, R.string.apply_invest_idCard);
                    return;
                }
                String loanPrice = loanPriceTv.getText().toString().trim();
                if("".equals(loanPrice)) {
                    AbToastUtil.showToast(mContext, R.string.apply_loan_price);
                    return;
                }
                //可以做点提交的事情了
                AbToastUtil.showToast(mContext, "可以提交了");
            }
        });
    }
}
