package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbToastUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 注册页面的activity
 *
 * Created by xiaohongliang on 2016/8/15.
 */
public class RegisterActivity extends AbActivity{

    private Context mContext;
    private TextView phoneTv;
    private TextView randCodeTv;
    private Button sendRandCodeBtn;
    private Button submitBtn;
    private ImageView xieyiImageV;
    private int codeTime = 5;// 发送短信默认时间
    private int time = 1000;
    //是否选中了协议
    private Boolean isChecked = true;
    //是否是忘记密码的操作页面
    private Boolean isForgetPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.register);
        setStatusBar(R.color.title_bg);
        mContext = RegisterActivity.this;

        submitBtn = (Button) this.findViewById(R.id.submit_register_btn);
        AbTitleBar mAbTitleBar = this.getTitleBar();

        String sourceType = getIntent().getStringExtra("sourceType");
        if(sourceType != null && "forgetPwd".equals(sourceType)) {
            isForgetPwd = true;
            new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.forgetPwd_title);
            submitBtn.setText(R.string.forgetPwd_submit_btn);
            this.findViewById(R.id.xieyi_view).setVisibility(View.GONE);
        }else {
            TextView rightView = new TitleBarUtil(mContext).setActivityTitleBarAndRight(mAbTitleBar, R.string.register,
                    mContext, R.layout.ico_home_right_list);
            // 设置右边菜单的点击事件
            rightView.setText(R.string.login_title);
            rightView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, UserLoginActivity.class);
                    startActivity(intent);
                }
            });
        }
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
        //提交验证的绑定事件
        submitBtn.setOnClickListener(new View.OnClickListener() {
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
                if(!isForgetPwd && !isChecked) {
                    AbToastUtil.showToast(mContext, R.string.register_xieyi);
                    return;
                }
                //做点验证验证码的事情吧
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterSubmitActivity.class);
                intent.putExtra("phone", phone);
                if(isForgetPwd) {
                    intent.putExtra("sourceType", "forgetPwd");
                }
                startActivity(intent);
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
                get2Code();
            }
        });
        //点击清空手机号
        this.findViewById(R.id.clear_phone_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneTv.setText("");
            }
        });

        xieyiImageV = (ImageView) this.findViewById(R.id.xieyi_icon);
        //协议条的checkbox框的选中事件
        this.findViewById(R.id.xieyi_layout_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked) {
                    isChecked = false;
                    xieyiImageV.setImageResource(R.drawable.icon_choice_n);
                }else {
                    isChecked = true;
                    xieyiImageV.setImageResource(R.drawable.icon_choice_h);
                }
            }
        });
        //查看用户协议内容
        this.findViewById(R.id.xieyi_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, UserAgreementActivity.class);
                startActivity(intent);
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
}
