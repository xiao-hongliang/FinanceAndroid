package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

/**
 * 用户协议的展现页面(懒得弄当前页面的弹出框了，这样简单)
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class UserAgreementActivity extends AbActivity{

    private Context mContext;
    private TextView agreementTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.register_user_agreement);
        setStatusBar(R.color.title_bg);
        mContext = UserAgreementActivity.this;

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.register_user_agreement);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        agreementTv = (TextView)this.findViewById(R.id.agreement_content);
        agreementTv.setText("这是用户协议，这是用户协议，这是用户协议，这是用户协议，这是用户协议，这是用户协议，这是用户协议，这是用户协议，这是用户协议，");

    }
}
