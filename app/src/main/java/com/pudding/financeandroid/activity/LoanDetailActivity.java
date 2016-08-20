package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.image.AbImageLoader;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.LoanBean;
import com.pudding.financeandroid.response.LoanDetailResponse;
import com.pudding.financeandroid.response.LoanListResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 贷款详情页面
 *
 * Created by xiao.hongliang on 2016/8/20.
 */
public class LoanDetailActivity extends AbActivity{

    private Context mContext;
    private AbImageLoader mAbImageLoader = null;
    private static final String TAG = LoanDetailActivity.class.getName();
    /** 连接对象 */
    private RequestImpl ri = null;
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.loan_detail);
        setStatusBar(R.color.title_bg);
        mContext = LoanDetailActivity.this;
        AbTitleBar mAbTitleBar = this.getTitleBar();

        Intent intent = this.getIntent();
        LoanBean loanBean = (LoanBean) intent.getSerializableExtra("bean");
        this.mAbImageLoader = AbImageLoader.getInstance(mContext);

        String title = loanBean.getName();
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

        httpPost(loanBean.getId());

        //客服点击事件绑定
        this.findViewById(R.id.tel_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent phoneIntent = new Intent( "android.intent.action.CALL", Uri.parse("tel:"+ phone));
            startActivity(phoneIntent);
            }
        });

        //申请贷款的点击绑定事件
        this.findViewById(R.id.apply_loan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applyIntent = new Intent();
                applyIntent.setClass(mContext, ApplyLoanActivity.class);
                startActivity(applyIntent);
            }
        });
    }

    private void setDetailView(LoanBean loanBean) {
        ImageView logoIv = (ImageView)this.findViewById(R.id.loan_item_logo);
        this.mAbImageLoader.display(logoIv, loanBean.getLogo());
        ((TextView)this.findViewById(R.id.loan_item_name)).setText(loanBean.getName());
        ((TextView)this.findViewById(R.id.loan_item_month_rate)).setText(loanBean.getMonthRateInfo());
        this.phone = loanBean.getPhone();

//        ((TextView)this.findViewById(R.id.loan_content)).setText(loanBean.getContent());
    }

    private void httpPost(int loanId) {
        ri.loanDetail(loanId, new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
                 AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
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
                    LoanDetailResponse bean = (LoanDetailResponse) AbJsonUtil.fromJson(content, LoanDetailResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
//                        AbToastUtil.showToast(mContext, "数据获取成功!");
                        //加载贷款详情数据
                        if(bean.getData() != null){
                            setDetailView(bean.getData());
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
