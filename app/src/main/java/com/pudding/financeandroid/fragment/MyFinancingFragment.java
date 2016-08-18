package com.pudding.financeandroid.fragment;

import android.os.Bundle;

import com.pudding.financeandroid.R;
import com.shizhefei.fragment.LazyFragment;

/**
 * 我的理财fragment页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class MyFinancingFragment extends LazyFragment{

    private static MyFinancingFragment instance;

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
    }
}
