package com.pudding.financeandroid.util;

import java.util.Properties;

import android.content.Context;

public class PropUtil {
	
	/**
	 * 获取配置文件的值
	 * @Title: getV 
	 * 
	 * @param  mContext
	 * @param  key
	 * @param @return    设定文件 
	 * @return String    返回类型
	 */
	public static String getV(Context mContext, String key) {
	  
		return getV(mContext, key, "");
	}
	
	/**
	 * 获取配置文件的值
	 * @Title: getV 
	 * 
	 * @param  mContext
	 * @param  key
	 * @param @return    设定文件 
	 * @return String    返回类型
	 */
	public static String getV(Context mContext, String key,String defaultValue) {
		String value = null;
		Properties properties = new Properties();
		try {
			properties.load(mContext.getAssets().open("config.properties"));
			value = properties.getProperty(key, defaultValue);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		return value;
	} 

}
