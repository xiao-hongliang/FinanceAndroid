package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.MyFinancingListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.MyFinancingBean;
import com.pudding.financeandroid.response.MyFinancingResponse;
import com.shizhefei.fragment.LazyFragment;

import java.util.List;

/**
 * 我的理财fragment页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class MyFinancingFragment extends LazyFragment{
    private static final String TAG = MyFinancingFragment.class.getName();
    private static MyFinancingFragment instance;
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private PullToRefreshListView myFinancingList = null;
    private int pageNum = 1;
    private MyFinancingListAdapter newAdapter = null;

    public static MyFinancingFragment newInstance() {
        if(instance == null) {
            instance = new MyFinancingFragment();
        }
        return instance;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.my_financing);
        mContext = getApplicationContext();
        ri = new RequestImpl(mContext);

        myFinancingList = (PullToRefreshListView)this.findViewById(R.id.myFinancing_list);
        httpPost(Boolean.FALSE, null);
    }

    private void initView(List<MyFinancingBean> financingBeen){
        newAdapter = new MyFinancingListAdapter(mContext, financingBeen, R.layout.my_financing_item);
        myFinancingList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        myFinancingList.setOnRefreshListener(new MyOnRefreshListener2(myFinancingList));
        myFinancingList.setAdapter(newAdapter);
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

            httpPost(Boolean.TRUE, mPtflv);
        }
    }

    private void httpPost(final Boolean isMorePage, final PullToRefreshListView perv) {
        if(isMorePage) {
            pageNum++;
        }
        ri.myFinancingList(pageNum, new AbStringHttpResponseListener() {
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
                    MyFinancingResponse bean = (MyFinancingResponse) AbJsonUtil.fromJson(content, MyFinancingResponse.class);
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
}
