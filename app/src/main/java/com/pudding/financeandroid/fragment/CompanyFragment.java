package com.pudding.financeandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.pudding.financeandroid.R;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * 公司的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class CompanyFragment extends LazyFragment{

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private TextView tvTabAll, tvTabNews, tvTabDongTai, tvTabMaoYi;
    private final int tabAll = 0;
    private final int tabNews = 1;
    private final int tabDongTai = 2;
    private final int tabMaoYi = 3;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_company);
    }

    private void InitTextView() {
        tvTabAll = (TextView)this.findViewById(R.id.company_all);
        tvTabNews = (TextView)this.findViewById(R.id.company_news);
        tvTabDongTai = (TextView)this.findViewById(R.id.company_dongTai);
        tvTabMaoYi = (TextView)this.findViewById(R.id.company_maoYi);
        tvTabAll.setOnClickListener(new MyOnClickListener(0));
        tvTabNews.setOnClickListener(new MyOnClickListener(1));
        tvTabDongTai.setOnClickListener(new MyOnClickListener(2));
        tvTabMaoYi.setOnClickListener(new MyOnClickListener(3));
    }

    private void InitViewPager() {
        mPager = (ViewPager) this.findViewById(R.id.company_pager);
        fragmentsList = new ArrayList<>();

        Fragment taballfragment = InfoListFragment.newInstance(taball);
        Fragment tabNoticefragment = InfoListFragment.newInstance(tabnotice);
        Fragment tabYcnewsFragment = InfoListFragment.newInstance(tabycnews);
        Fragment tabFaguiFragment = InfoListFragment.newInstance(tabfagui);
        fragmentsList.add(taballfragment);
        fragmentsList.add(tabNoticefragment);
        fragmentsList.add(tabYcnewsFragment);
        fragmentsList.add(tabFaguiFragment);
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
    };

}
