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
import com.pudding.financeandroid.activity.LoanDetailActivity;
import com.pudding.financeandroid.adapter.LoanListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.LoanBean;
import com.pudding.financeandroid.response.LoanListResponse;
import com.shizhefei.fragment.LazyFragment;

import java.util.List;

/**
 * 贷款的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class DaiKuanFragment extends LazyFragment{

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private Context mContext;
    private LoanListAdapter adapter;
    /** 连接对象 */
    private RequestImpl ri = null;
    private static final String TAG = DaiKuanFragment.class.getName();
    private ListView loanListView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_daikuan);

        mContext = this.getApplicationContext();
        ri = new RequestImpl(mContext);
        loanListView = (ListView)this.findViewById(R.id.loan_list_view);
        httpPost();
        //设置点击item监听事件
        loanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanBean bean = (LoanBean) adapter.getItem(position);
                Intent itemIntent = new Intent();
                itemIntent.setClass(mContext, LoanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                itemIntent.putExtras(bundle);
                startActivity(itemIntent);
            }
        });
    }

    private void initListView(List<LoanBean> loanBeanList) {
        adapter = new LoanListAdapter(loanBeanList, mContext);
        loanListView.setAdapter(adapter);
    }

    private void httpPost() {
        ri.loanList(new AbStringHttpResponseListener() {
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
//                        AbToastUtil.showToast(mContext, "数据获取成功!");
                        //加载贷款列表数据
                        if(bean.getData().size()>0){
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
