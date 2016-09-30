package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.ContentListAdapter;
import com.pudding.financeandroid.api.BaseApi;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.FinancingBean;
import com.pudding.financeandroid.response.FinancingDetailResponse;
import com.pudding.financeandroid.util.TitleBarUtil;
import com.pudding.financeandroid.view.MyListView;

/**
 * 理财详情页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class FinancingDetailActivity extends AbActivity{
    private static final String TAG = FinancingDetailActivity.class.getName();
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private FinancingBean financingBean;
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.financing_detail);
        setStatusBar(R.color.title_bg);
        mContext = FinancingDetailActivity.this;
        ri = new RequestImpl(mContext);
        Intent intent = this.getIntent();
        financingBean = (FinancingBean) intent.getSerializableExtra("bean");

        AbTitleBar mAbTitleBar = this.getTitleBar();
        String title;
        if(financingBean.getName().contains("-")) {
            title = (financingBean.getName().split("-"))[0];
        }else {
            title = financingBean.getName();
        }
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

        httpPost(financingBean.getId());

        //客服点击事件绑定
        this.findViewById(R.id.tel_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接拨打电话
//                Intent phoneIntent = new Intent( "android.intent.action.CALL", Uri.parse("tel:"+ phone));
//                startActivity(phoneIntent);
                //跳转到拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //投资购买点击事件绑定
        this.findViewById(R.id.apply_invest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ApplyInvestActivity.class);
                intent.putExtra("productId", financingBean.getId());
                startActivity(intent);
            }
        });
        //收益计算器的点击事件绑定
        this.findViewById(R.id.financing_calculator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String url = BaseApi.BASE_URL + BaseApi.finance_calculator;
                url += "?productId=" + financingBean.getId();
                intent.putExtra("url", url);
                intent.putExtra("title", "理财计算器");
                intent.setClass(mContext, UserAgreementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(FinancingBean bean) {
        this.phone = bean.getPhone();
        //剩余多少
        TextView surplusTv = (TextView) this.findViewById(R.id.surplus_tv);
        surplusTv.setText("(剩" + bean.getMaxAmount() + "/" + bean.getTotalAmount() + "万)");
        TextView financingName = (TextView) this.findViewById(R.id.financing_name);
        financingName.setText(bean.getName());
        TextView financingYearRate = (TextView) this.findViewById(R.id.financing_year_rate);
        financingYearRate.setText(bean.getYearRateStr());
        //百分比的进度
        TextView financingPercentage = (TextView) this.findViewById(R.id.financing_percentage);
        financingPercentage.setText(bean.getFinishRateStr());
        TextView financingMinAmount = (TextView) this.findViewById(R.id.financing_minAmount);
        financingMinAmount.setText(bean.getMinAmount()+"");
        TextView financingInterestPolicy = (TextView) this.findViewById(R.id.financing_interestPolicy);
        financingInterestPolicy.setText(bean.getInterestPolicy().getName());
        TextView financingMaxAmount = (TextView) this.findViewById(R.id.financing_maxAmount);
        financingMaxAmount.setText(bean.getMaxAmount());
        TextView financingTotalAmount = (TextView) this.findViewById(R.id.financing_totalAmount);
        financingTotalAmount.setText(bean.getTotalAmount());
        TextView financingInvestTimeName = (TextView) this.findViewById(R.id.financing_investTimeName);
        financingInvestTimeName.setText(bean.getInvestTimeName());

        //计算红色进度条的显示值start
        String finishStr = bean.getFinishRateStr().replace("%", "");
        String intStr;
        if(Double.valueOf(finishStr) > 1) {
            intStr = finishStr.substring(0, finishStr.indexOf("."));
        }else {
            intStr = finishStr.substring(finishStr.indexOf(".") + 1, finishStr.length());
        }
        int finishInt = Integer.parseInt(intStr);
        //计算红色进度条的显示值end
        TextView percentageLeftTv = (TextView) this.findViewById(R.id.percentage_left);
        TextView percentageRightTv = (TextView) this.findViewById(R.id.percentage_right);
        //LayoutParams参数依次为，宽度，高度，权重占比
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, 12, (float) finishInt);
        percentageLeftTv.setLayoutParams(p);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0, 12, (float) (100 - finishInt));
        percentageRightTv.setLayoutParams(p2);

        //迭代加载显示贷款详情的内容
        MyListView financingListView = (MyListView)this.findViewById(R.id.financing_content);
        ContentListAdapter contentListAdapter = new ContentListAdapter(bean.getRichTextContent(), mContext);
        financingListView.setAdapter(contentListAdapter);
//        infoContentList.setFocusable(false);
        ScrollView myScrollView = (ScrollView) this.findViewById(R.id.scrollView_financing);
        myScrollView.smoothScrollTo(0, 0);
//        ListViewUtil.setListViewHeightBasedOnChildren(infoContentList, null);
    }

    private void httpPost(String financingId) {
        ri.financingDetail(financingId, new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
                // AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
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
                    FinancingDetailResponse bean = (FinancingDetailResponse) AbJsonUtil.fromJson(content, FinancingDetailResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        //加载贷款列表数据
                        if(bean.getData() != null){
                            initView(bean.getData());
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
