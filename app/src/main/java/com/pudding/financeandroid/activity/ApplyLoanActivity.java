package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.LoanStageBean;
import com.pudding.financeandroid.form.LoanApplyForm;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.response.LoanStageListResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

import java.util.List;

/**
 * 申请贷款的页面
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class ApplyLoanActivity extends AbActivity{
    private static final String TAG = ApplyLoanActivity.class.getName();
    private Context mContext;
    private TextView loanNameTv;
    private TextView loanPhoneTv;
    private TextView loanIdCardTv;
    private TextView loanPriceTv;
    /** 连接对象 */
    private RequestImpl ri = null;
    private List<LoanStageBean> stageBeen;
    private int chooseBean = 0;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.apply_loan);
        setStatusBar(R.color.title_bg);
        mContext = ApplyLoanActivity.this;
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

        loanNameTv = (TextView) this.findViewById(R.id.loan_name);
        loanPhoneTv = (TextView) this.findViewById(R.id.loan_phone);
        loanIdCardTv = (TextView) this.findViewById(R.id.loan_idCard);
        loanPriceTv = (TextView) this.findViewById(R.id.loan_price);

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        httpPost(productId);

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
                LoanApplyForm loanApplyForm = new LoanApplyForm(productId, loanName, loanPhone, loanIdCard,
                        loanPrice, stageBeen.get(chooseBean).getId());
                httpSendLoanApplyPost(loanApplyForm);
            }
        });
    }

    private void initStageListView(List<LoanStageBean> stageBeen) {
        this.stageBeen = stageBeen;
        Spinner spinner = (Spinner) findViewById(R.id.Spinner01);
        //将可选内容与ArrayAdapter连接起来
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, parseNameList(stageBeen));
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中s
        spinner.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    private String[] parseNameList(List<LoanStageBean> stageBeen) {
        String[] beans = new String[stageBeen.size()];
        for(int i=0; i<stageBeen.size(); i++) {
            beans[i] = stageBeen.get(i).getName();
        }
        return beans;
    }

    //使用数组形式操作
    public class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            //选择的第几个选项
            chooseBean = arg2;
        }
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    private void httpPost(String productId) {
        ri.loanStageList(productId, new AbStringHttpResponseListener() {
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
                    LoanStageListResponse bean = (LoanStageListResponse) AbJsonUtil.fromJson(content, LoanStageListResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        //加载贷款详情数据
                        if(bean.getData().size() > 0){
                            initStageListView(bean.getData());
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

    private void httpSendLoanApplyPost(LoanApplyForm form) {
        ri.loanApplySend(form, new AbStringHttpResponseListener() {
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
