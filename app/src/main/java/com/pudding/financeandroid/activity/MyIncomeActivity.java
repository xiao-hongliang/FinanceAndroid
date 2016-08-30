package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.MyIncomeDetailAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.MyIncomeDetailBean;
import com.pudding.financeandroid.bean.MyIncomeSummaryBean;
import com.pudding.financeandroid.response.MyIncomeDetailResponse;
import com.pudding.financeandroid.response.MyIncomeSummaryResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

import java.util.List;

/**
 * 我的收益页面
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MyIncomeActivity extends AbActivity{
    private static final String TAG = MyIncomeActivity.class.getName();
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private int pageNum = 1;
    private PullToRefreshListView myIncomeDetailList = null;
    private MyIncomeDetailAdapter newAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.my_income);
        setStatusBar(R.color.title_bg);
        mContext = MyIncomeActivity.this;
        ri = new RequestImpl(mContext);

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.user_center_2);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myIncomeDetailList = (PullToRefreshListView)this.findViewById(R.id.myIncome_detail_list);
        httpPostForList(Boolean.FALSE, null);
        httpPostForSummary();
    }

    private void initViewForSummary(MyIncomeSummaryBean myIncomeSummaryBean) {
        TextView tvMyIncomeAmount = (TextView) this.findViewById(R.id.myIncome_amount);
        tvMyIncomeAmount.setText("￥" + myIncomeSummaryBean.getIncomeAmount());
        TextView tvMyIncomeInvestAmount = (TextView) this.findViewById(R.id.myIncome_investAmount);
        tvMyIncomeInvestAmount.setText(myIncomeSummaryBean.getInvestAmount());
        TextView tvMyIncomeTotalAmount = (TextView) this.findViewById(R.id.myIncome_totalAmount);
        tvMyIncomeTotalAmount.setText("￥" + myIncomeSummaryBean.getIncomeAmount());
    }

    private void initView(List<MyIncomeDetailBean> myIncomeDetailBeen){
        newAdapter = new MyIncomeDetailAdapter(mContext, myIncomeDetailBeen, R.layout.my_income_list_item);
        myIncomeDetailList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        myIncomeDetailList.setOnRefreshListener(new MyOnRefreshListener2(myIncomeDetailList));
        myIncomeDetailList.setAdapter(newAdapter);
    }

    class MyOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2<ListView> {
        private PullToRefreshListView mPtflv;
        public MyOnRefreshListener2(PullToRefreshListView ptflv) {
            this.mPtflv = ptflv;
        }
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {}
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            // 上拉加载
            String label = DateUtils.formatDateTime(mContext,
                    System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_ABBREV_ALL);
            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            refreshView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
            refreshView.getLoadingLayoutProxy().setRefreshingLabel("加载中……");
            refreshView.getLoadingLayoutProxy().setReleaseLabel("释放加载");

            httpPostForList(Boolean.TRUE, mPtflv);
        }
    }

    private void httpPostForList(final Boolean isMorePage, final PullToRefreshListView perv) {
        if(isMorePage) {
            pageNum++;
        }
        ri.myIncomeDetailList(pageNum, new AbStringHttpResponseListener() {
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
                    MyIncomeDetailResponse bean = (MyIncomeDetailResponse) AbJsonUtil.fromJson(content, MyIncomeDetailResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        if(isMorePage) {
                            if(bean.getData().size() > 0){
                                newAdapter.addNews(bean.getData());
                                newAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(mContext, R.string.noData, Toast.LENGTH_SHORT).show();
                            }
                            perv.onRefreshComplete();
                        }else {
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

    private void httpPostForSummary() {
        ri.myIncomeSummary(new AbStringHttpResponseListener() {
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
                    MyIncomeSummaryResponse bean = (MyIncomeSummaryResponse) AbJsonUtil.fromJson(content, MyIncomeSummaryResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        initViewForSummary(bean.getData());
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