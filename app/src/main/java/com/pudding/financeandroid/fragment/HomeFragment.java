package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbJsonUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.activity.AdvertisementDetailActivity;
import com.pudding.financeandroid.activity.ApplyInvestActivity;
import com.pudding.financeandroid.activity.ApplyLoanActivity;
import com.pudding.financeandroid.activity.CompanyCultureActivity;
import com.pudding.financeandroid.activity.FinancingDetailActivity;
import com.pudding.financeandroid.activity.LoanDetailActivity;
import com.pudding.financeandroid.activity.MainActivity;
import com.pudding.financeandroid.activity.NewFinancingActivity;
import com.pudding.financeandroid.activity.UserLoginActivity;
import com.pudding.financeandroid.adapter.FinancingListAdapter;
import com.pudding.financeandroid.adapter.HomeGridAdapter;
import com.pudding.financeandroid.adapter.LoanListAdapter;
import com.pudding.financeandroid.api.RequestImpl;
import com.pudding.financeandroid.bean.AdvsListBean;
import com.pudding.financeandroid.bean.ApplicationBean;
import com.pudding.financeandroid.bean.FinancingBean;
import com.pudding.financeandroid.bean.LoanBean;
import com.pudding.financeandroid.bean.MainBean;
import com.pudding.financeandroid.response.MainResponse;
import com.pudding.financeandroid.util.SPUtils;
import com.pudding.financeandroid.view.ADInfo;
import com.pudding.financeandroid.view.ImageCycleView;
import com.pudding.financeandroid.view.MyGridView;
import com.pudding.financeandroid.view.MyListView;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class HomeFragment extends LazyFragment{
    private static final String TAG = HomeFragment.class.getName();
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private ProgressBar progressBar;
    private ArrayList<ADInfo> infos = new ArrayList<>();
    private MyGridView gridview;
    private HomeGridAdapter homeAdapter;
    private Context mContext;
    /** 连接对象 */
    private RequestImpl ri = null;
    private FinancingListAdapter financingListAdapter;
    private LoanListAdapter loanListAdapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_home);
        mContext = getApplicationContext();
        ri = new RequestImpl(mContext);

        gridview = (MyGridView) findViewById(R.id.gridview);
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        httpPost();
        initGuidView();
    }

    private void initView(MainBean mainBean) {
        initImg(mainBean.getAdvsList());
        initFinancingList(mainBean.getFinanceList());
        initLoanList(mainBean.getLoanList());
    }

    private void initFinancingList(List<FinancingBean> financingBeen) {
//        ListView mainFinancingListView = (ListView)this.findViewById(R.id.main_financing_list_view);
        MyListView mainFinancingListView = (MyListView)this.findViewById(R.id.main_financing_list_view);
        financingListAdapter = new FinancingListAdapter(financingBeen, mContext);
        mainFinancingListView.setAdapter(financingListAdapter);
        //设置点击item监听事件
        mainFinancingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FinancingBean bean = (FinancingBean) financingListAdapter.getItem(position);
                Intent itemIntent = new Intent();
                itemIntent.setClass(mContext, FinancingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                itemIntent.putExtras(bundle);
                startActivity(itemIntent);
            }
        });
//        ListViewUtil.setListViewHeightBasedOnChildren(mainFinancingListView, null);
    }

    private void initLoanList(List<LoanBean> loanBeen) {
//        ListView mainLoanListView = (ListView)this.findViewById(R.id.main_loan_list_view);
        MyListView mainLoanListView = (MyListView)this.findViewById(R.id.main_loan_list_view);
        loanListAdapter = new LoanListAdapter(loanBeen, mContext);
        mainLoanListView.setAdapter(loanListAdapter);
        //设置点击item监听事件
        mainLoanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanBean bean = (LoanBean) loanListAdapter.getItem(position);
                Intent itemIntent = new Intent();
                itemIntent.setClass(mContext, LoanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                itemIntent.putExtras(bundle);
                startActivity(itemIntent);
            }
        });
//        ListViewUtil.setListViewHeightBasedOnChildren(mainLoanListView, Boolean.TRUE);
    }

    /**
     * 轮播图片
     */
    private void initImg(List<AdvsListBean> advsListBeen) {
        for(AdvsListBean bean : advsListBeen) {
            ADInfo info = new ADInfo();
            info.setUrl(bean.getLogo());
            info.setContent(bean.getTitle());
            info.setId(bean.getId());
            infos.add(info);
        }
        ImageCycleView mAdView = (ImageCycleView) findViewById(R.id.ad_view);
        mAdView.setVisibility(View.VISIBLE);
        mAdView.setImageResources(infos, mAdCycleViewListener);
    }

    /**
     * 加载九宫格
     */
    private void initGuidView() {
        List<ApplicationBean> beanList = new ArrayList<>(6);
        beanList.add(new ApplicationBean(R.string.gridView_name_1, R.drawable.gridview_1, R.color.home_tab_1));
        beanList.add(new ApplicationBean(R.string.gridView_name_2, R.drawable.gridview_2, R.color.home_tab_2));
        beanList.add(new ApplicationBean(R.string.gridView_name_3, R.drawable.gridview_3, R.color.home_tab_3));
        beanList.add(new ApplicationBean(R.string.gridView_name_4, R.drawable.gridview_4, R.color.home_tab_4));
        beanList.add(new ApplicationBean(R.string.gridView_name_5, R.drawable.gridview_5, R.color.home_tab_5));
        beanList.add(new ApplicationBean(R.string.gridView_name_6, R.drawable.gridview_6, R.color.home_tab_6));

        homeAdapter = new HomeGridAdapter(mContext, beanList);
        gridview.setAdapter(homeAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
                MainActivity tabMainActivity = (MainActivity) getActivity();
                //判断一下，是否处于登录状态，详情需要登陆了才可以才看
                String phone = (String) SPUtils.get(mContext, "phone", "");
                switch (index) {
                    case 0:
                        Intent newFinancingApply = new Intent();
                        newFinancingApply.setClass(mContext, NewFinancingActivity.class);
                        startActivity(newFinancingApply);
                        break;
                    case 1:
                        tabMainActivity.indicatorViewPager.setCurrentItem(1, false);
                        break;
                    case 2:
                        Intent financingApply = new Intent();
                        if (AbStrUtil.isEmpty(phone)) {
                            financingApply.setClass(mContext, UserLoginActivity.class);
                        }else {
                            financingApply.setClass(mContext, ApplyInvestActivity.class);
                            financingApply.putExtra("isMainEnter", Boolean.TRUE);
                        }
                        startActivity(financingApply);
                        break;
                    case 3:
                        Intent loanApply = new Intent();
                        if (AbStrUtil.isEmpty(phone)) {
                            loanApply.setClass(mContext, UserLoginActivity.class);
                        }else {
                            loanApply.setClass(mContext, ApplyLoanActivity.class);
                            loanApply.putExtra("isMainEnter", Boolean.TRUE);
                        }
                        startActivity(loanApply);
                        break;
                    case 4:
                        Intent intentRecommend = new Intent();
//                        intentRecommend.putExtra("url", BaseApi.BASE_URL + BaseApi.recommend_xplain);
//                        intentRecommend.putExtra("title", "推荐有礼规则说明");
//                        intentRecommend.setClass(mContext, UserAgreementActivity.class);
                        intentRecommend.setClass(mContext, CompanyCultureActivity.class);
                        startActivity(intentRecommend);
                        break;
                    case 5:
                        tabMainActivity.indicatorViewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    /**
     * 图片监听器
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            //轮播图点击事件
            Intent intent = new Intent();
            intent.setClass(mContext, AdvertisementDetailActivity.class);
            intent.putExtra("title", info.getContent());
            intent.putExtra("id", info.getId());
            startActivity(intent);
        }
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader.getInstance().displayImage(imageURL, imageView);//
            // 使用ImageLoader对图片进行加装！
        }
    };

    private void httpPost() {
        ri.getIndex(new AbStringHttpResponseListener() {
            // 开始执行前
            @Override
            public void onStart() {
                // 显示进度框
                // AbDialogUtil.showProgressDialog(mContext, 0, "正在获取数据...");
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
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
                    MainResponse bean = (MainResponse) AbJsonUtil.fromJson(content, MainResponse.class);
                    // 验证成功
                    if (bean.getSuccess()) {
                        initView(bean.getData());
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
