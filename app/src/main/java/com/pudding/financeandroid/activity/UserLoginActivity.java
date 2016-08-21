package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.form.UserLoginForm;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.util.SPUtils;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 用户登录的界面
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class UserLoginActivity extends AbActivity{
    private static final String TAG = UserLoginActivity.class.getName();
    private Context mContext;
    private TextView phoneTv;
    private TextView pwdTv;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.user_login);
        setStatusBar(R.color.title_bg);
        mContext = UserLoginActivity.this;
        ri = new RequestImpl(mContext);

        AbTitleBar mAbTitleBar = this.getTitleBar();
        TextView rightView = new TitleBarUtil(mContext).setActivityTitleBarAndRight(mAbTitleBar, R.string.login_title,
                mContext, R.layout.ico_home_right_list);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置右边菜单的点击事件
        rightView.setText(R.string.login_register);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //绑定登陆接口
        phoneTv = (TextView) this.findViewById(R.id.login_phone_text);
        pwdTv = (TextView) this.findViewById(R.id.login_pwd_text);
        this.findViewById(R.id.submit_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneTv.getText().toString().trim();
                if("".equals(phone)) {
                    AbToastUtil.showToast(mContext, R.string.register_phone_send_hint);
                    return;
                }
                String pwd = pwdTv.getText().toString().trim();
                if("".equals(pwd)) {
                    AbToastUtil.showToast(mContext, R.string.login_pwd_response);
                    return;
                }
                //可以进行登陆了
                SPUtils.put(mContext, "phone", phone);
                UserLoginForm userLoginForm = new UserLoginForm("", phone, pwd);
                userLoginPost(userLoginForm);
            }
        });

        this.findViewById(R.id.clear_login_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdTv.setText("");
            }
        });
        this.findViewById(R.id.clear_phone_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneTv.setText("");
            }
        });

        //忘记密码的绑定事件
        this.findViewById(R.id.login_forgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterActivity.class);
                intent.putExtra("sourceType", "forgetPwd");
                startActivity(intent);
            }
        });
        //注册新用户
        this.findViewById(R.id.login_to_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLoginPost(UserLoginForm form) {
        ri.userLogin(form, new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
//                 AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(mContext);
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
                    CommonResponse bean = (CommonResponse) AbJsonUtil.fromJson(content, CommonResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        AbToastUtil.showToast(mContext, "登陆成功");
                        finish();
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
