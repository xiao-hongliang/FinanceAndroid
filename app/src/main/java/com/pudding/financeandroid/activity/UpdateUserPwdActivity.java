package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 注册页面提交密码的页面
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class UpdateUserPwdActivity extends AbActivity{
    private static final String TAG = UpdateUserPwdActivity.class.getName();
    private Context mContext;
    private EditText pwdFirstTv;
    private EditText pwdSecondTv;
    private EditText passwordOldTv;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.update_user_pwd);
        setStatusBar(R.color.title_bg);
        mContext = UpdateUserPwdActivity.this;
        ri = new RequestImpl(mContext);

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.user_center_5);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pwdFirstTv = (EditText) this.findViewById(R.id.password_first);
        pwdSecondTv = (EditText) this.findViewById(R.id.password_second);
        passwordOldTv = (EditText) this.findViewById(R.id.password_old);
        //绑定提交密码按钮
        this.findViewById(R.id.submit_register_pwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordOld = passwordOldTv.getText().toString().trim();
                if("".equals(passwordOld)) {
                    AbToastUtil.showToast(mContext, R.string.update_pwd_old_hint);
                    return;
                }
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
                userUpdatePwdPost(passwordOld, firstPwd);
            }
        });

        this.findViewById(R.id.clear_pwd_old).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordOldTv.setText("");
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

    private void userUpdatePwdPost(String oldPwd, String newPwd) {
        ri.updateUserPwd(oldPwd, newPwd, new AbStringHttpResponseListener() {
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
                        AbToastUtil.showToast(mContext, "密码修改成功");
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
