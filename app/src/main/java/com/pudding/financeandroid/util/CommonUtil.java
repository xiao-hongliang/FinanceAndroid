package com.pudding.financeandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 公共工具类
 *
 * Created by xiao.hongliang on 2016/8/24.
 */
public class CommonUtil {
    /**
     * 检查当前的网络状态(包括WiFi、手机GPRS)
     * @param context  上下文对象
     * @return  网络状态是否连接
     */
    public static boolean isWifiConnected(Context context) {
        //单独获取WIFI连接状态
        //WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //return manager.isWifiEnabled();
        // 获取手机所以连接管理对象（包括wi-fi，net等连接的管理）
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null) {
            // 网络管理连接对象
            NetworkInfo info = conn.getActiveNetworkInfo();
            if(info != null && info.isConnected()) {
                // 判断当前网络是否连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
