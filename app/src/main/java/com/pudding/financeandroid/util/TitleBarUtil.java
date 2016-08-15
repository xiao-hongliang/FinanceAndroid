package com.pudding.financeandroid.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.ab.view.titlebar.AbTitleBar;
import com.pudding.financeandroid.R;

/**  
 * @Project: HNCloud
 * @Title: TitleBarUtil.java
 * @Package com.linzi.hncloud.util
 * 
 * @author zuolangguo
 * @date 2015年10月10日 上午10:33:08
 * @version V1.0  
 */
public class TitleBarUtil {
	private Context context;
	
	public TitleBarUtil(Context context){
		this.context = context;
	}
	
	/**
	 * 设置Fragment titleBar
	 * @Title: setFragmentTitleBar 
	 * 
	 * @param  layout    标题布局文件
	 * @return void    返回类型
	 */
	public void setFragmentTitleBar(LinearLayout layout,int resId)
	{
		//获取标题布局
		AbTitleBar mAbTitleBar = new AbTitleBar(context);
        //mAbTitleBar.setTitleBarHeight(96);
        mAbTitleBar.setTitleText(resId);
        mAbTitleBar.setLogo2(R.drawable.app_n);
        mAbTitleBar.setTitleBarBackground(R.color.title_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        //mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER,Gravity.CENTER);//设置文字对齐方式
         
        //设置标题布局 
        layout.removeAllViews();
        layout.addView(mAbTitleBar); 
	}
	
	
	public void setHomeTitleBar(LinearLayout layout,int resId)
	{
		//获取标题布局
		AbTitleBar mAbTitleBar = new AbTitleBar(context);
        //mAbTitleBar.setTitleBarHeight(96);
        mAbTitleBar.setTitleText(resId);
        mAbTitleBar.setLogo2(R.drawable.app_n);
        mAbTitleBar.setTitleBarBackground(R.color.title_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        //mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER,Gravity.CENTER);//设置文字对齐方式
         
        //设置标题布局 
        layout.removeAllViews();
        layout.addView(mAbTitleBar); 
	}
	
	/**
	 * 设置Activity标题头
	 * @Title: setActivityTitleBar 
	 * 
	 * @param  mAbTitleBar  继承AbActivity的AbTitleBar对象
	 * @return void    返回类型
	 */
	public void setActivityTitleBar(AbTitleBar mAbTitleBar,int resId)
	{ 
        mAbTitleBar.setTitleText(resId);
        mAbTitleBar.setTitleBarHeight(96);
//        mAbTitleBar.setLogo2(R.drawable.app_n);
        mAbTitleBar.setTitleBarBackground(R.color.title_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        //mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER,Gravity.CENTER);//设置文字对齐方式
		// mAbTitleBar.setVisibility(View.GONE);
		// 设置AbTitleBar在最上 
	}
	
	public void setActivityTitleBarBack(AbTitleBar mAbTitleBar,int resId)
	{ 
        mAbTitleBar.setTitleText(resId);
        mAbTitleBar.setTitleBarHeight(96);
//        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setLogo(R.drawable.icon_left);
        mAbTitleBar.setTitleBarBackground(R.color.title_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        //mAbTitleBar.setLogoLine(R.drawable.line);
        mAbTitleBar.setTitleBarGravity(Gravity.CENTER,Gravity.CENTER);//设置文字对齐方式
		// mAbTitleBar.setVisibility(View.GONE);
		// 设置AbTitleBar在最上 
	}

}

