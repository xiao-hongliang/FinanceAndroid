package com.pudding.financeandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ab.util.AbToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.adapter.HomeGridAdapter;
import com.pudding.financeandroid.bean.ApplicationBean;
import com.pudding.financeandroid.view.ADInfo;
import com.pudding.financeandroid.view.ImageCycleView;
import com.pudding.financeandroid.view.MyGridView;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的fragment
 *
 * Created by xiao.hongliang on 2016/8/16.
 */
public class HomeFragment extends LazyFragment{

    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";

    private ProgressBar progressBar;
    private ArrayList<ADInfo> infos = new ArrayList<>();
    private MyGridView gridview;
    private HomeGridAdapter homeAdapter;
    private Context mContext;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.tab_main_home);
        mContext = getApplicationContext();

        gridview = (MyGridView) findViewById(R.id.gridview);
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        initImg();
        initGuidView();
    }

    /**
     * 轮播图片
     */
    private void initImg() {
        List<String> urlList = new ArrayList<>();
        urlList.add("http://test.4009947755.com/appmanager/upload/img/14690717102003g2d.png");
        urlList.add("http://test.4009947755.com/appmanager/upload/img/1458185516565lbnew03.jpg");
        urlList.add("http://test.4009947755.com/appmanager/upload/img/1458185512027lbnew02.jpg");
        for(String url : urlList) {
            ADInfo info = new ADInfo();
            info.setUrl(url);
            info.setContent("广告区图片");
            infos.add(info);
        }
        ImageCycleView mAdView = (ImageCycleView) findViewById(R.id.ad_view);
        mAdView.setVisibility(View.VISIBLE);
        mAdView.setImageResources(infos, mAdCycleViewListener);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
                // 在本例中arg2=arg3
                //AbToastUtil.showToast(mContext, "=arg0:"+arg0+"=arg2:"+index+"=arg3:"+arg3);
                ApplicationBean bean = homeAdapter.getList().get(index);
                AbToastUtil.showToast(mContext, bean.getName());
//                Intent intent = new Intent();
//                String url = BaseApi.getInstance(mContext).getAppUrl(bean.getGateway(), bean.getAppId());
//                intent.putExtra("url", url);
//                intent.putExtra("applicationId", bean.getId());
//                intent.setClass(mContext, BaseWebViewActivity.class);
//                startActivity(intent);
            }
        });
    }

    /**
     * 加载九宫格
     */
    private void initGuidView() {
        List<ApplicationBean> beanList = new ArrayList<>(6);
        beanList.add(new ApplicationBean(R.string.gridView_name_1, R.drawable.gridview_1, R.color.white));
        beanList.add(new ApplicationBean(R.string.gridView_name_2, R.drawable.gridview_2, R.color.title_bg));
        beanList.add(new ApplicationBean(R.string.gridView_name_3, R.drawable.gridview_3, R.color.font_9));
        beanList.add(new ApplicationBean(R.string.gridView_name_4, R.drawable.gridview_4, R.color.lightyellow));
        beanList.add(new ApplicationBean(R.string.gridView_name_5, R.drawable.gridview_5, R.color.bisque));
        beanList.add(new ApplicationBean(R.string.gridView_name_6, R.drawable.gridview_6, R.color.pink));

        homeAdapter = new HomeGridAdapter(mContext, beanList);
        gridview.setAdapter(homeAdapter);
    }

    /**
     * 图片监听器
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            //轮播图点击事件
            //Toast.makeText(mContext, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader.getInstance().displayImage(imageURL, imageView);//
            // 使用ImageLoader对图片进行加装！
        }
    };
}
