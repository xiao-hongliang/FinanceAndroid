package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.form.FinancingApplyForm;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 申请投资购买信息填报页面
 *
 * Created by xiao.hongliang on 2016/8/19.
 */
public class ApplyInvestActivity extends AbActivity{
    private static final String TAG = ApplyInvestActivity.class.getName();
    private Context mContext;
    private TextView investNameTv;
    private TextView investPhoneTv;
    private TextView investIdCardTv;
    private TextView investNumTv;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.apply_invest);
        setStatusBar(R.color.title_bg);
        mContext = ApplyInvestActivity.this;
        ri = new RequestImpl(mContext);
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
                Intent intent = getIntent();
                String productId = intent.getStringExtra("productId");
                FinancingApplyForm form = new FinancingApplyForm(productId, investName, investPhone, investIdCard, investNum);
                httpSendLoanApplyPost(form);
            }
        });
    }

    private void httpSendLoanApplyPost(FinancingApplyForm form) {
        ri.financingApplySend(form, new AbStringHttpResponseListener() {
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
                    CommonResponse bean = (CommonResponse) AbJsonUtil.fromJson(content, CommonResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        AbToastUtil.showToast(mContext, "申请提交成功");
                        finish();
                    } else {
                        if(bean.getCode() == -100) {
                            Intent intentLogin = new Intent();
                            intentLogin.setClass(mContext, UserLoginActivity.class);
                            startActivity(intentLogin);
                        }else {
                            AbToastUtil.showToast(mContext, bean.getMsg());
                        }
                    }
                }catch(Exception e) {
                    Log.v(TAG, "Home加载数据异常！" + e.getMessage());
                }
            }
        });
    }
}
