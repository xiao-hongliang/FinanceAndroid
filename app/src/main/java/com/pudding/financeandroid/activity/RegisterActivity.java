package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
    private int codeTime = 5;// 发送短信默认时间
    private int time = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.register);
        setStatusBar(R.color.title_bg);
        mContext = RegisterActivity.this;

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
        phoneTv = (TextView) this.findViewById(R.id.phone_text_view);
        randCodeTv = (TextView) this.findViewById(R.id.code_text_view);
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
                    AbToastUtil.showToast(mContext, R.string.register_phone_send_hint);
                    return;
                }
                //做点验证验证码的事情吧
                AbToastUtil.showToast(mContext, "还没来得及写呢");
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
