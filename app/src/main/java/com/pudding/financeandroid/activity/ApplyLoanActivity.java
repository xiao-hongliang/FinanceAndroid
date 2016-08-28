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
import com.pudding.financeandroid.util.CommonUtil;
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
    private List<LoanStageBean> loanProductListBean;
    private int chooseBean = 0;
    private String productId = "";
    private ArrayAdapter<String> stageListAdapter = null;
    private Spinner spinner;

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
        Boolean isMainEnter = intent.getBooleanExtra("isMainEnter", Boolean.FALSE);
        if(isMainEnter) {
            //如果是首页的预约贷款
            httpPostForProductList();
        }else {
            productId = intent.getStringExtra("productId");
            httpPost(productId);
        }

        initSubmitView();
    }

    /**
     * 初始化首页贷款预约时，贷款产品的下拉列表选项
     *
     * @param loanProductListBean     贷款产品的数组bean
     */
    private void initProductListView(List<LoanStageBean> loanProductListBean) {
        this.loanProductListBean = loanProductListBean;
        Spinner spinnerProduct = (Spinner) findViewById(R.id.spinner_product);
        //将可选内容与ArrayAdapter连接起来
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, CommonUtil.parseNameList(loanProductListBean));
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中s
        spinnerProduct.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinnerProduct.setOnItemSelectedListener(new SpinnerProductSelectedListener());
        spinnerProduct.setVisibility(View.VISIBLE);
    }

    //使用数组形式操作
    public class SpinnerProductSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //选择的第几个选项
            LoanStageBean productBean = loanProductListBean.get(arg2);
            productId = productBean.getId();
            httpPost(productBean.getId());
        }
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    /**
     * 初始化贷款产品的贷款年限的下拉列表数据
     *
     * @param stageBeen     贷款产品的贷款年限列表
     */
    private void initStageListView(List<LoanStageBean> stageBeen) {
        this.stageBeen = stageBeen;
        spinner = (Spinner) findViewById(R.id.Spinner01);
        //将可选内容与ArrayAdapter连接起来
        stageListAdapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, CommonUtil.parseNameList(stageBeen));
        //设置下拉列表的风格
        stageListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中s
        spinner.setAdapter(stageListAdapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    //使用数组形式操作
    public class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //选择的第几个选项
            chooseBean = arg2;
        }
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    private void httpPostForProductList() {
        ri.getProductListForLoanApply(new AbStringHttpResponseListener() {
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
                            initProductListView(bean.getData());
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

    private void initSubmitView() {
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
                if("".equals(productId)) {
                    AbToastUtil.showToast(mContext, R.string.apply_product_null);
                    return;
                }
                //可以做点提交的事情了
                LoanApplyForm loanApplyForm = new LoanApplyForm(productId, loanName, loanPhone, loanIdCard,
                        loanPrice, stageBeen.get(chooseBean).getId());
                httpSendLoanApplyPost(loanApplyForm);
            }
        });
    }
}
