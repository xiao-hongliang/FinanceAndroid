package com.pudding.financeandroid.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ab.global.AbAppConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pudding.financeandroid.R;
import com.pudding.financeandroid.log.CrashHandler;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
	private static final String TAG = MyApplication.class.getName();
	public boolean isFirstStart = true;
	public SharedPreferences mSharedPreferences = null;
	
	private Integer mainFragmentCurrentItem = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//初始化日志
		CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());  
		
		mSharedPreferences = getSharedPreferences(AbAppConfig.SHARED_PATH, Context.MODE_PRIVATE);

		// 初始化ImageLoader
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);

		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public Integer getMainFragmentCurrentItem() {
		return mainFragmentCurrentItem;
	}

	public void setMainFragmentCurrentItem(Integer mainFragmentCurrentItem) {
		this.mainFragmentCurrentItem = mainFragmentCurrentItem;
	}
}
