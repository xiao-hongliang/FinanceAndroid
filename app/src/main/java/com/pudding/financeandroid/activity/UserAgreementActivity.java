package com.pudding.financeandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.register_user_agreement);
        setStatusBar(R.color.title_bg);
        mContext = UserAgreementActivity.this;

        AbTitleBar mAbTitleBar = this.getTitleBar();
        String title = getIntent().getStringExtra("title");
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, title);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
}
