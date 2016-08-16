package com.pudding.financeandroid.fragment;

import android.os.Bundle;

import com.pudding.financeandroid.R;
import com.shizhefei.fragment.LazyFragment;

/**
 * 公司的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class CompanyFragment extends LazyFragment{

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_company);
    }

}
