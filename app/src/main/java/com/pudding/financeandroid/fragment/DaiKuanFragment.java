package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.LoanDetailActivity;
import com.pudding.financeandroid.activity.UserLoginActivity;
import com.pudding.financeandroid.adapter.LoanListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.LoanBean;
import com.pudding.financeandroid.response.LoanListResponse;
import com.pudding.financeandroid.util.SPUtils;
import com.shizhefei.fragment.LazyFragment;

import java.util.List;

/**
 * 贷款的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class DaiKuanFragment extends LazyFragment{
    private static final String TAG = DaiKuanFragment.class.getName();
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private Context mContext;
    private LoanListAdapter newAdapter;
    /** 连接对象 */
    private RequestImpl ri = null;
    private int pageNum = 1;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_daikuan);

        mContext = this.getApplicationContext();
        ri = new RequestImpl(mContext);
        httpPost(Boolean.FALSE, null);
    }

    private void initListView(List<LoanBean> loanBeanList) {
        PullToRefreshListView loanListView = (PullToRefreshListView)this.findViewById(R.id.loan_list_view);
        newAdapter = new LoanListAdapter(loanBeanList, mContext);
        loanListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        loanListView.setOnRefreshListener(new MyOnRefreshListener2(loanListView));
        loanListView.setAdapter(newAdapter);
        //设置点击item监听事件
        loanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                LoanBean bean = (LoanBean) listView.getItemAtPosition(position);
                Intent itemIntent = new Intent();
                //判断一下，是否处于登录状态，详情需要登陆了才可以才看
                String phone = (String) SPUtils.get(mContext, "phone", "");
                if (AbStrUtil.isEmpty(phone)) {
                    itemIntent.setClass(mContext, UserLoginActivity.class);
                }else {
                    itemIntent.setClass(mContext, LoanDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", bean);
                    itemIntent.putExtras(bundle);
                }
                startActivity(itemIntent);
            }
        });
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
        ri.loanList(pageNum, new AbStringHttpResponseListener() {
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
                    LoanListResponse bean = (LoanListResponse) AbJsonUtil.fromJson(content, LoanListResponse.class);
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
                            initListView(bean.getData());
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
