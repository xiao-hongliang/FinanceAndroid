package com.pudding.financeandroid.fragment;

import android.os.Bundle;

import com.pudding.financeandroid.R;
import com.shizhefei.fragment.LazyFragment;

/**
 * 我的贷款fragment页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class MyLoanFragment extends LazyFragment{
    private static MyLoanFragment instance;

    public static MyLoanFragment newInstance() {
        if(instance == null) {
            instance = new MyLoanFragment();
        }
        return instance;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.my_loan);
    }
}
