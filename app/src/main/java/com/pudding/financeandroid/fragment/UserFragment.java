package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.MainActivity;
import com.pudding.financeandroid.activity.MyOrderActivity;
import com.pudding.financeandroid.activity.UpdateUserPwdActivity;
import com.pudding.financeandroid.util.SPUtils;
import com.shizhefei.fragment.LazyFragment;

/**
 * 我的的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class UserFragment extends LazyFragment implements View.OnClickListener{

    private static final String TAG = UserFragment.class.getName();

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private Context mContext;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_user);
        mContext = getApplicationContext();

        this.findViewById(R.id.user_logout_btn).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_1).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_2).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_3).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_4).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_5).setOnClickListener(this);
        this.findViewById(R.id.user_center_layout_6).setOnClickListener(this);
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        Log.v(TAG, "onResumeLazy is call");
    }

    //检查登陆状态
    private void checkLogin() {
        String phone = (String) SPUtils.get(mContext, "phone", "");
        if (AbStrUtil.isEmpty(phone)) {
//            MainActivity mainActivity = (MainActivity) getActivity();
//            mainActivity.indicatorViewPager.setCurrentItem(0, true);

            //没有登录
//            Intent intent = new Intent();
//            intent.setClass(mContext, UserLoginActivity.class);
//            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()) {
            case R.id.user_logout_btn:
                AbToastUtil.showToast(mContext, "退出登陆哦");
                break;
            case R.id.user_center_layout_1:
                intent.setClass(mContext, MyOrderActivity.class);
                break;
            case R.id.user_center_layout_2:
                AbToastUtil.showToast(mContext, R.string.user_center_2);
                break;
            case R.id.user_center_layout_3:
                AbToastUtil.showToast(mContext, R.string.user_center_3);
                break;
            case R.id.user_center_layout_4:
                AbToastUtil.showToast(mContext, R.string.user_center_4);
                break;
            case R.id.user_center_layout_5:
                intent.setClass(mContext, UpdateUserPwdActivity.class);
                break;
            case R.id.user_center_layout_6:
                AbToastUtil.showToast(mContext, R.string.user_center_6);
                break;
        }
        startActivity(intent);
    }
}
