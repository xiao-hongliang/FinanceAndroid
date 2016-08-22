package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.FinancingDetailActivity;
import com.shizhefei.fragment.LazyFragment;

/**
 * 我的理财fragment页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class MyFinancingFragment extends LazyFragment{

    private static MyFinancingFragment instance;
    private Context mContext;

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

        //临时写一个点击事件跳转至详情页面(正常接口对接时，是一个item点击事件来着)
        this.findViewById(R.id.financing_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, FinancingDetailActivity.class);
//                startActivity(intent);
            }
        });
    }
}
