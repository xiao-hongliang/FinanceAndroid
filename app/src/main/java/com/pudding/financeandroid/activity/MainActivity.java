package com.pudding.financeandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbFragmentActivity;
import com.ab.global.AbActivityManager;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.update.UpdateHelper;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.api.BaseApi;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.form.UserLoginForm;
import com.pudding.financeandroid.fragment.CompanyFragment;
import com.pudding.financeandroid.fragment.DaiKuanFragment;
import com.pudding.financeandroid.fragment.HomeFragment;
import com.pudding.financeandroid.fragment.LiCaiFragment;
import com.pudding.financeandroid.fragment.UserFragment;
import com.pudding.financeandroid.response.CommonResponse;
import com.pudding.financeandroid.util.SPUtils;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends AbFragmentActivity {
    private static final String TAG = MainActivity.class.getName();
    private Context mContext;
    public IndicatorViewPager indicatorViewPager;
    private long exitTime = 0;
    /** 连接对象 */
    private RequestImpl ri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.activity_main, null);
        setContentView(view);
        mContext = MainActivity.this;
        setTitle(R.string.app_name);
        setStatusBar(view, R.color.title_bg);
        ri = new RequestImpl(mContext);

        //启动更新检测
        UpdateHelper updateHelper = new UpdateHelper.Builder(this).checkUrl(BaseApi.BASE_URL + BaseApi.app_upgrade)
                .isAutoInstall(true) // 设置为false需在下载完手动点击安装;默认值为true，下载后自动安装。
                .build();
        updateHelper.check();

        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(true);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(5);

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                if (currentItem == 0) {
                    setTitle(R.string.main_title_name);
                } else if (currentItem == 1) {
                    setTitle(R.string.licai_title_name);
                } else if (currentItem == 2) {
                    setTitle(R.string.daikuan_title_name);
                } else if (currentItem == 3) {
                    setTitle(R.string.company_title_name);
                } else if(currentItem == 4) {
                    String phone = (String) SPUtils.get(mContext, "phone", "");
                    if (AbStrUtil.isEmpty(phone)) {
                        indicatorViewPager.setCurrentItem(preItem, false);

                        Intent intent = new Intent();
                        intent.setClass(mContext, UserLoginActivity.class);
                        startActivity(intent);
                    }else {
                        setTitle(R.string.user_title_name);
                    }
                }
//                // 保存一下首页tabFragment当前显示的页面状态
//                myApplication.setMainFragmentCurrentItem(currentItem);
            }
        });

        String phone = (String) SPUtils.get(mContext, "phone", "");
        String pwd = (String) SPUtils.get(mContext, "pwd", "");
        if (!AbStrUtil.isEmpty(phone) && !AbStrUtil.isEmpty(pwd)) {
            //如果是登录成功过的，则下一次启动时，自动登录一把
            userLoginPost(new UserLoginForm(null, phone, pwd));
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = { "首页", "理财", "贷款", "公司", "我的"};
        private int[] tabIcons = { R.drawable.maintab_1_selector, R.drawable.maintab_2_selector,
                R.drawable.maintab_3_selector, R.drawable.maintab_4_selector, R.drawable.maintab_5_selector};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(tabIcons[position]), null, null);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            // T.showShort(getApplicationContext(), "====:"+position);
            Fragment mainFragment = null;
            if (position == 0) {
                mainFragment = new HomeFragment();
            } else if (position == 1) {
                mainFragment = new LiCaiFragment();
            } else if (position == 2) {
                mainFragment = new DaiKuanFragment();
            } else if (position == 3) {
                mainFragment = new CompanyFragment();
            } else if(position == 4) {
                mainFragment = new UserFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putString(HomeFragment.INTENT_STRING_TABNAME, tabNames[position]);
            bundle.putInt(HomeFragment.INTENT_INT_INDEX, position);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }

    public void setTitle(int resId) {
        // 设置标题头
        LinearLayout titleBarll = (LinearLayout) findViewById(R.id.titlebar);
        // 获取标题布局
        AbTitleBar mAbTitleBar = new AbTitleBar(mContext);
        // mAbTitleBar.setTitleBarHeight(96);
        mAbTitleBar.setTitleText(resId);
        mAbTitleBar.setTitleBarBackground(R.color.title_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        // 添加右边操作按钮
        mAbTitleBar.clearRightView();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        // 添加list
        View rightViewList = mInflater.inflate(R.layout.main_home_right_list, null);
        mAbTitleBar.addRightView(rightViewList);
        //主页面导航栏右上角消息图标的点击事件绑定
        rightViewList.findViewById(R.id.main_home_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) SPUtils.get(mContext, "phone", "");
                Intent messageIntent = new Intent();
                if (AbStrUtil.isEmpty(phone)) {
                    messageIntent.setClass(mContext, UserLoginActivity.class);
                }else {
                    messageIntent.setClass(mContext, UserMessageActivity.class);
                }
                startActivity(messageIntent);
            }
        });
        // 设置标题布局
        titleBarll.removeAllViews();
        titleBarll.addView(mAbTitleBar);
        // 设置文字对齐方式
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER, Gravity.CENTER);
    }

    /**
     * 描述：退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // System.currentTimeMillis()无论何时调用，肯定大于2000
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                try {
                    AbActivityManager.getInstance().clearAllActivity();
                } catch (Exception e) {
                    AbLogUtil.d("main:", "退出=" + e.getMessage());
                } finally {
                    System.exit(0);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void userLoginPost(UserLoginForm form) {
        ri.userLogin(form, new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish");
            }
            // 失败，调用
            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                Log.v(TAG, "onFailure");
            }
            // 获取数据成功会调用这里
            public void onSuccess(int statusCode, String content) {
                try{
                    CommonResponse bean = (CommonResponse) AbJsonUtil.fromJson(content, CommonResponse.class);
                    if (bean.getSuccess()) {
                        Log.v(TAG, "自动登录成功");
                    }
                }catch(Exception e) {
                    Log.v(TAG, "Home加载数据异常！" + e.getMessage());
                }
            }
        });
    }
}
