package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.form.UserRegisterForm;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 忘记密码页面的activity
 *
 * Created by xiaohongliang on 2016/8/15.
 */
public class ForgetPwdActivity extends AbActivity{
    private static final String TAG = ForgetPwdActivity.class.getName();
    private Context mContext;
    private TextView phoneTv;
    private TextView randCodeTv;
    private TextView pwdFirstTv;
    private TextView pwdSecondTv;
    private Button sendRandCodeBtn;
    private int codeTime = 5;// 发送短信默认时间
    private int time = 1000;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.forget_pwd);
        setStatusBar(R.color.title_bg);
        mContext = ForgetPwdActivity.this;
        ri = new RequestImpl(mContext);

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.forgetPwd_title);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            //绑定返回上一页的点击按钮
            public void onClick(View v) {
                finish();
            }
        });

        phoneTv = (TextView) this.findViewById(R.id.phone_text_view);
        randCodeTv = (TextView) this.findViewById(R.id.code_text_view);
        pwdFirstTv = (TextView) this.findViewById(R.id.password_first);
        pwdSecondTv = (TextView) this.findViewById(R.id.password_second);
        //提交验证的绑定事件
        this.findViewById(R.id.submit_register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneTv.getText().toString().trim();
                if("".equals(phone)) {
                    AbToastUtil.showToast(mContext, R.string.register_phone_send_hint);
                    return;
                }
                String randCode = randCodeTv.getText().toString().trim();
                if("".equals(randCode)) {
                    AbToastUtil.showToast(mContext, R.string.register_randCode_send_hint);
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
                //重置密码吧
                UserRegisterForm form = new UserRegisterForm("", randCode, phone, firstPwd);
                sendRegisterPost(form);
            }
        });
        sendRandCodeBtn = (Button) this.findViewById(R.id.send_randCode);
        //发送验证的绑定事件
        sendRandCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneTv.getText().toString().trim();
                if("".equals(phone)) {
                    AbToastUtil.showToast(mContext, R.string.register_phone_send_hint);
                    return;
                }
                //发送验证码吧
                sendRandCodePost(phone);
            }
        });
        //点击清空手机号
        this.findViewById(R.id.clear_phone_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneTv.setText("");
            }
        });
        //点击清空第一次密码
        this.findViewById(R.id.clear_pwd_first_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdFirstTv.setText("");
            }
        });
        //点击清空第二次密码
        this.findViewById(R.id.clear_pwd_second_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdSecondTv.setText("");
            }
        });
    }

    // 再次获取验证码方法
    private void get2Code() {
        sendRandCodeBtn.setEnabled(false);
        codeTime = 60;
        handler.removeCallbacks(runnable);// 停止计时器
        handler.postDelayed(runnable, time); // 每隔1s执行
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                sendRandCodeBtn.setText("发送验证码" + "(" + codeTime + "s)");
                if (codeTime == 0) {
                    sendRandCodeBtn.setText("发送验证码");
                    sendRandCodeBtn.setEnabled(true);
                } else {
                    handler.postDelayed(this, time);
                }
                if (codeTime < 0) {
                    handler.removeCallbacks(runnable);// 停止计时器
                }
                codeTime = codeTime - 1;
            } catch (Exception e) {
                System.out.println("exception...");
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 停止计时器
            handler.removeCallbacks(runnable);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void sendRandCodePost(String mobile) {
        ri.sendRandCodeForForgetPwd(mobile, new AbStringHttpResponseListener() {
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
                        AbToastUtil.showToast(mContext, "验证码已发送");
                        get2Code();
                    } else {
                        AbToastUtil.showToast(mContext, bean.getMsg());
                    }
                }catch(Exception e) {
                    Log.v(TAG, "Home加载数据异常！" + e.getMessage());
                }
            }
        });
    }

    private void sendRegisterPost(UserRegisterForm form) {
        ri.userForgetPwd(form, new AbStringHttpResponseListener() {
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
                        AbToastUtil.showToast(mContext, "重置密码成功");
                        Intent intent = new Intent();
                        intent.setClass(mContext, UserLoginActivity.class);
                        startActivity(intent);
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
