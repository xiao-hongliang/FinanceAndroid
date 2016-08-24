package com.pudding.financeandroid.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.MyFragmentPagerAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * 公司的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class CompanyFragment extends LazyFragment {

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private TextView tvTabAll, tvTabNews, tvTabDongTai, tvTabMaoYi;
    private ViewPager mPager;
    private int currIndex = 0;
    private Resources resources;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_company);
        resources = getResources();

        InitTextView();
        InitViewPager();
    }

    private void InitTextView() {
        tvTabAll = (TextView) this.findViewById(R.id.company_all);
        tvTabNews = (TextView) this.findViewById(R.id.company_news);
        tvTabDongTai = (TextView) this.findViewById(R.id.company_dongTai);
        tvTabMaoYi = (TextView) this.findViewById(R.id.company_maoYi);
        tvTabAll.setOnClickListener(new MyOnClickListener(0));
        tvTabNews.setOnClickListener(new MyOnClickListener(1));
        tvTabDongTai.setOnClickListener(new MyOnClickListener(2));
        tvTabMaoYi.setOnClickListener(new MyOnClickListener(3));
    }

    private void InitViewPager() {
        mPager = (ViewPager) this.findViewById(R.id.company_pager);
        ArrayList<Fragment> fragmentsList = new ArrayList<>();

        Fragment tabAllFragment = InfoListFragment.newInstance(0);
        Fragment tabNewsFragment = InfoListFragment.newInstance(1);
        Fragment tabDongTaiFragment = InfoListFragment.newInstance(2);
        Fragment tabMaoYiiFragment = InfoListFragment.newInstance(3);
        fragmentsList.add(tabAllFragment);
        fragmentsList.add(tabNewsFragment);
        fragmentsList.add(tabDongTaiFragment);
        fragmentsList.add(tabMaoYiiFragment);
        mPager.setAdapter(new MyFragmentPagerAdapter(this.getFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(3);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
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
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        tvTabNews.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 2) {
                        tvTabDongTai.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 3) {
                        tvTabMaoYi.setTextColor(resources.getColor(R.color.mainTabOff));
                    }
                    tvTabAll.setTextColor(resources.getColor(R.color.title_bg));
                    break;
                case 1:
                    if (currIndex == 0) {
                        tvTabAll.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 2) {
                        tvTabDongTai.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 3) {
                        tvTabMaoYi.setTextColor(resources.getColor(R.color.mainTabOff));
                    }
                    tvTabNews.setTextColor(resources.getColor(R.color.title_bg));
                    break;
                case 2:
                    if (currIndex == 0) {
                        tvTabAll.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 1) {
                        tvTabNews.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 3) {
                        tvTabMaoYi.setTextColor(resources.getColor(R.color.mainTabOff));
                    }
                    tvTabDongTai.setTextColor(resources.getColor(R.color.title_bg));
                    break;
                case 3:
                    if (currIndex == 0) {
                        tvTabAll.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 1) {
                        tvTabNews.setTextColor(resources.getColor(R.color.mainTabOff));
                    } else if (currIndex == 2) {
                        tvTabDongTai.setTextColor(resources.getColor(R.color.mainTabOff));
                    }
                    tvTabMaoYi.setTextColor(resources.getColor(R.color.title_bg));
                    break;
            }
            currIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }
}