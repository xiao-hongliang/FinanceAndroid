package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.util.TitleBarUtil;

public class MainActivity extends AbActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_main);
        setStatusBar(R.color.title_bg);

        mContext = MainActivity.this;

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBar(mAbTitleBar, R.string.app_name);

        this.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
