package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 注册页面提交密码的页面
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class RegisterSubmitActivity extends AbActivity{

    private Context mContext;
    private TextView pwdFirstTv;
    private TextView pwdSecondTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.register_submit);
        setStatusBar(R.color.title_bg);
        mContext = RegisterSubmitActivity.this;

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.register);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pwdFirstTv = (TextView) this.findViewById(R.id.password_first);
        pwdSecondTv = (TextView) this.findViewById(R.id.password_second);
        //绑定提交密码按钮
        this.findViewById(R.id.submit_register_pwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstPwd = pwdFirstTv.getText().toString().trim();
                if("".equals(firstPwd)) {
                    AbToastUtil.showToast(mContext, R.string.register_pwd_first_hint);
                    return;
                }
                String secondPwd = pwdSecondTv.getText().toString().trim();
                if("".equals(secondPwd)) {
                    AbToastUtil.showToast(mContext, R.string.register_pwd_second_hint);
                    return;
                }
                if(!firstPwd.equals(secondPwd)) {
                    AbToastUtil.showToast(mContext, R.string.register_pwd_disagree);
                    return;
                }
                //好了，可以提交密码了
                AbToastUtil.showToast(mContext, "可以提交密码了");
            }
        });

        this.findViewById(R.id.clear_pwd_first_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdFirstTv.setText("");
            }
        });
        this.findViewById(R.id.clear_pwd_second_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdSecondTv.setText("");
            }
        });
    }
}
