package com.pudding.financeandroid.cordova;

import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

/**  
 * @Project: HNCloud
 * @Title: LZWebViewClient.java
 * @Package com.linzi.hncloud.cordova
 * 
 * @author zuolangguo
 * @date 2015年12月24日 下午3:43:50
 * @version V1.0  
 */
public class LZWebChromeClient extends SystemWebChromeClient  {
	/** 标题头 */
	private AbTitleBar mAbTitleBar = null;

	private Context mContext;
	
	/** 进度条 */
	private AbLoadDialogFragment pdf = null;

	public LZWebChromeClient(SystemWebViewEngine parentEngine) {
		super(parentEngine);
		
	}
	
	/**
	 * 设置标题头
	 * @Title: setTitleBar 
	 * 
	 * @param     mContext
	 * @return void    返回类型
	 */
	public void init(AbTitleBar mAbTitleBar,Context mContext)
	{
		this.mAbTitleBar = mAbTitleBar;
		this.mContext = mContext;
	}
	
	/**
	 * 获取网页标题头
	 */
	@Override
	public void onReceivedTitle(WebView view, String title) {
		// 
		 
		if (mAbTitleBar != null) {
			String titles = title;
			if (titles.length() > 14) {
				titles = titles.substring(0, 14)+"...";
			}
			mAbTitleBar.setTitleText(titles);
		}

		super.onReceivedTitle(view, title);
	}
	
	/**
	 * 设置加载进度
	 */
	@SuppressLint("NewApi")
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		// 
		if (newProgress == 100) {
           // progressbar.setVisibility(GONE);
			AbDialogUtil.removeDialog(mContext);
        } else {
        	if (pdf == null || !pdf.getShowsDialog()) {
        		
        		pdf = AbDialogUtil.showLoadDialog(mContext, R.drawable.loading, "加载中,请稍候...",AbDialogUtil.ThemeHoloLightDialog);
        		
			} 
        }
		super.onProgressChanged(view, newProgress);
	}
 

}

