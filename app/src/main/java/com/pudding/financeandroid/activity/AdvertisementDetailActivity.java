package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.ContentListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.AdvertisementBean;
import com.pudding.financeandroid.response.AdvertisementResponse;
import com.pudding.financeandroid.util.ListViewUtil;
import com.pudding.financeandroid.util.TitleBarUtil;
import com.pudding.financeandroid.view.MyListView;

/**
 * 首页广告区图片的详情内容
 *
 * Created by xiao.hongliang on 2016/9/4.
 */
public class AdvertisementDetailActivity extends AbActivity{
    private static final String TAG = ApplyInvestActivity.class.getName();
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.advertisement_info);
        setStatusBar(R.color.title_bg);
        mContext = AdvertisementDetailActivity.this;
        ri = new RequestImpl(mContext);
        AbTitleBar mAbTitleBar = this.getTitleBar();

//        String title = getIntent().getStringExtra("title");
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, "广告详情");
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        httpPost(getIntent().getStringExtra("id"));
    }

    private void initView(AdvertisementBean bean) {
        //初始化标题等内容的显示
        TextView titleTv = (TextView) this.findViewById(R.id.adv_info_title);
        titleTv.setText(bean.getTitle());
        TextView createTimeTv = (TextView) this.findViewById(R.id.adv_info_createTime);
        createTimeTv.setText(bean.getCreateTimeStr());
        //初始化内容的列表显示
        MyListView advertisementListView = (MyListView)this.findViewById(R.id.advertisement_list_view);
        ContentListAdapter contentListAdapter = new ContentListAdapter(bean.getRichTextContent(), mContext);
        advertisementListView.setAdapter(contentListAdapter);
//        ListViewUtil.setListViewHeightBasedOnChildren(advertisementListView, null);
    }

    private void httpPost(String id) {
        ri.getAdvertisementInfo(id, new AbStringHttpResponseListener() {
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
                    AdvertisementResponse bean = (AdvertisementResponse) AbJsonUtil.fromJson(content, AdvertisementResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        initView(bean.getData());
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
