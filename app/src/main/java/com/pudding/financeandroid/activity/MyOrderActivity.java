package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.MyFragmentPagerAdapter;
import com.pudding.financeandroid.fragment.MyFinancingFragment;
import com.pudding.financeandroid.fragment.MyLoanFragment;
import com.pudding.financeandroid.util.TitleBarUtil;

import java.util.ArrayList;

/**
 * 我的订单列表页面
 *
 * Created by xiao.hongliang on 2016/8/18.
 */
public class MyOrderActivity extends AbActivity{

    private Context mContext;
    private ViewPager mPager;
    private TextView myFinancingTv;
    private TextView myLoanTv;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.my_order);
        setStatusBar(R.color.title_bg);
        mContext = MyOrderActivity.this;
        resources = getResources();

        AbTitleBar mAbTitleBar = this.getTitleBar();
        new TitleBarUtil(mContext).setActivityTitleBarBack(mAbTitleBar, R.string.user_center_1);
        //设置AbTitleBar在最上
        this.setTitleBarOverlay(false);
        //绑定返回上一页的点击按钮
        mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InitTextView();
        initViewPager();
    }

    private void InitTextView() {
        myLoanTv = (TextView)this.findViewById(R.id.my_loan_order);
        myFinancingTv = (TextView)this.findViewById(R.id.my_loan_financing);
        myFinancingTv.setOnClickListener(new MyOnClickListener(0));
        myLoanTv.setOnClickListener(new MyOnClickListener(1));
    }

    private void initViewPager() {
        mPager = (ViewPager) this.findViewById(R.id.fragmentPager);
        ArrayList<Fragment> fragmentsList = new ArrayList<>();
        fragmentsList.add(MyFinancingFragment.newInstance());
        fragmentsList.add(MyLoanFragment.newInstance());
        mPager.setAdapter(new MyFragmentPagerAdapter(this.getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(1);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    myFinancingTv.setTextColor(resources.getColor(R.color.title_bg));
                    myLoanTv.setTextColor(resources.getColor(R.color.font_3));
                    break;
                case 1:
                    myFinancingTv.setTextColor(resources.getColor(R.color.font_3));
                    myLoanTv.setTextColor(resources.getColor(R.color.title_bg));
                    break;
            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };
}
