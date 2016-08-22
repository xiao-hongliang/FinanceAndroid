package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.FinancingDetailActivity;
import com.pudding.financeandroid.adapter.FinancingListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.FinancingBean;
import com.pudding.financeandroid.response.FinancingListResponse;
import com.shizhefei.fragment.LazyFragment;

import java.util.List;

/**
 * 理财的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class LiCaiFragment extends LazyFragment{
    private static final String TAG = LiCaiFragment.class.getName();
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private ListView financingListView;
    private FinancingListAdapter adapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_licai);

        mContext = this.getApplicationContext();
        ri = new RequestImpl(mContext);
        financingListView = (ListView)this.findViewById(R.id.financing_list_view);
        httpPost();
        //设置点击item监听事件
        financingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FinancingBean bean = (FinancingBean) adapter.getItem(position);
                Intent itemIntent = new Intent();
                itemIntent.setClass(mContext, FinancingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                itemIntent.putExtras(bundle);
                startActivity(itemIntent);
            }
        });
    }

    private void initListView(List<FinancingBean> financingBeen) {
        adapter = new FinancingListAdapter(financingBeen, mContext);
        financingListView.setAdapter(adapter);
    }

    private void httpPost() {
        ri.financingList(Boolean.FALSE, new AbStringHttpResponseListener() {
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
                    FinancingListResponse bean = (FinancingListResponse) AbJsonUtil.fromJson(content, FinancingListResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        //加载贷款列表数据
                        if(bean.getData().size() > 0){
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
